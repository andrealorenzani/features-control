package name.lorenzani.andrea.featurescontrol.restcontrollers;

import name.lorenzani.andrea.featurescontrol.exceptions.DataRetrieverException;
import name.lorenzani.andrea.featurescontrol.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class FeatureFlagApi {

    private final String capabilitiesListApi;
    private final String validVersionApi;
    private final String versionCapabilitiesApi;
    private final String userCapabilitiesApi;
    private final String companyCapabilitiesApi;
    @Autowired
    private RestTemplate restTemplate;

    public FeatureFlagApi(@Value("${api.capability.list}") String capabilitiesListApi,
                          @Value("${api.version.validator}") String validVersionApi,
                          @Value("${api.version.capabilities}") String versionCapabilitiesApi,
                          @Value("${api.user.capabilities}") String userCapabilitiesApi,
                          @Value("${api.company.capabilities}") String companyCapabilitiesApi) {
        this.capabilitiesListApi = capabilitiesListApi;
        this.validVersionApi = validVersionApi;
        this.versionCapabilitiesApi = versionCapabilitiesApi;
        this.userCapabilitiesApi = userCapabilitiesApi;
        this.companyCapabilitiesApi = companyCapabilitiesApi;
    }

    @RequestMapping(value = "/custom/valid/{version}", method = RequestMethod.GET)
    public String getValidVersionApi(@PathVariable String version) {
        String reqId = UUID.randomUUID().toString();
        try {
            Version ver = invokeApi(String.format("%s/%s", validVersionApi, version), Version.class).get();
            if (ver.getCapabilities().size() > 0) {
                return "yay";
            } else {
                return "nay";
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new DataRetrieverException(reqId, String.format("Unable to retrieve the version %s from custom: %s", version, ex.getMessage()), ex);
        }
    }

    @RequestMapping(value = "/custom/check/{version}/{user}", method = RequestMethod.GET)
    public CheckReply checkVersionAndUserApi(@PathVariable String version, @PathVariable String user) {
        return checkVersionAndUserAndCompanyApi(version, user, null);
    }

    @RequestMapping(value = "/custom/check/{version}/{user}/{company}", method = RequestMethod.GET)
    public CheckReply checkVersionAndUserAndCompanyApi(@PathVariable String version,
                                                       @PathVariable String user,
                                                       @PathVariable String company) {
        String reqId = UUID.randomUUID().toString();
        try {
            return checkVersionAndUserAndCompany(version, user, company);
        } catch (Throwable ex) {
            throw new DataRetrieverException(reqId, "Unable to check for dynamic features:" + ex.getMessage(), ex);
        }
    }

    public CheckReply checkVersionAndUserAndCompany(String version,
                                                    String user,
                                                    String company) {
        CompletableFuture<List> futcap = invokeApi(capabilitiesListApi, List.class);
        CompletableFuture<Version> futversion = invokeApi(String.format("%s/%s", versionCapabilitiesApi, version), Version.class);
        CompletableFuture<User> futuser = invokeApi(String.format("%s/%s", userCapabilitiesApi, user), User.class);
        CompletableFuture<Company> futcompany = new CompletableFuture<>();
        if (company != null && !company.isEmpty()) {
            futcompany = invokeApi(String.format("%s/%s", companyCapabilitiesApi, company), Company.class);
            Stream.of(futcap, futversion, futuser, futcompany).forEach(CompletableFuture::join);
        } else {
            Stream.of(futcap, futversion, futuser).forEach(CompletableFuture::join);
        }
        List<Capability> caps = (List<Capability>) extractObject(futcap, List.class);
        Version ver = extractObject(futversion, Version.class);
        User userData = extractObject(futuser, User.class);
        if (Optional.ofNullable(company).isPresent()) {
            return computeCheckReply(caps.size(), Arrays.asList(ver.getCapabilities(),
                    userData.getCapabilities(), extractObject(futcompany, Company.class).getCapabilities()));
        } else {
            return computeCheckReply(caps.size(), Arrays.asList(ver.getCapabilities(),
                    userData.getCapabilities()));
        }

    }

    private CheckReply computeCheckReply(int size, List<List<Capability>> allCaps) {
        BitSet res = new BitSet(size);
        res.set(0, size); // This sets the Bitset to all 1
        allCaps.forEach(caps -> res.and(convertToBitset(size, caps)));
        allCaps.forEach(cap -> System.out.println("One list: " + cap.stream().map(Capability::getName).collect(Collectors.joining(","))));
        allCaps.get(0).stream().filter(cap -> res.get(cap.getPos())).forEach(cap -> System.out.println("Capability found: " + cap.getName()));
        return new CheckReply(res.toLongArray());
    }

    private BitSet convertToBitset(int size, List<Capability> capabilities) {
        BitSet res = new BitSet(size);
        capabilities.forEach(cap -> res.set(cap.getPos()));
        return res;
    }

    private <T> CompletableFuture<T> invokeApi(String path, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(path, clazz)
        );
    }

    private <T> T extractObject(CompletableFuture<T> future, Class<T> type) {
        try {
            return future.get();
        } catch (Throwable t) {
            throw new IllegalArgumentException("Unable to retrieve capabilities", t);
        }
    }

}
