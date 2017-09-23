package name.lorenzani.andrea.featurescontrol.restcontrollers.datapopulator;

import name.lorenzani.andrea.featurescontrol.datasource.CapabilitySource;
import name.lorenzani.andrea.featurescontrol.datasource.CompanySource;
import name.lorenzani.andrea.featurescontrol.exceptions.DataFillerException;
import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/company")
public class CompanyDataFiller extends DataFiller<Company> {

    private final CompanySource ds;
    private final CapabilitySource cs;

    @Autowired
    public CompanyDataFiller(CompanySource ds, CapabilitySource cs) {
        super(Company.class);
        this.ds = ds;
        this.cs = cs;
    }

    @Override
    public Company addElementApi(@PathVariable String id) {
        String requestId = UUID.randomUUID().toString();
        throw new DataFillerException(requestId, "Unable to create Company without name", null);
    }

    @Override
    protected Company addElement(String... arguments) throws Exception {
        String requestId = UUID.randomUUID().toString();
        if (arguments.length == 0)
            throw new DataFillerException(requestId, "Unable to create Company without id and name", null);
        if (arguments.length == 1)
            throw new DataFillerException(requestId, "Unable to create Company without name", null);
        return ds.addCompany(arguments[0], arguments[1]);
    }

    @Override
    protected Company addCapabilities(String id, List<String> capabilities) throws Exception {
        List<Capability> caps =
                capabilities.stream()
                        .map(cap -> cs.getCapability(cap)
                                .orElseThrow(() -> new IllegalArgumentException(String.format("Capability with id %s does not exist", cap)))
                        ).collect(Collectors.toList());
        return ds.setCapabilities(id, caps);
    }

    @Override
    protected Company removeCapabilities(String id, List<String> capabilities) throws Exception {
        List<Capability> caps =
                capabilities.stream()
                        .map(cap -> cs.getCapability(cap)
                                .orElseThrow(() -> new IllegalArgumentException(String.format("Capability with id %s does not exist", cap)))
                        ).collect(Collectors.toList());
        return ds.removeCapabilities(id, caps);
    }

    @Override
    protected List<Company> listAllElements() {
        return ds.getAllCompanies();
    }
}
