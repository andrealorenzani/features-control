package name.lorenzani.andrea.featurescontrol.restcontrollers;

import name.lorenzani.andrea.featurescontrol.exceptions.DataRetrieverException;
import name.lorenzani.andrea.featurescontrol.model.Company;
import name.lorenzani.andrea.featurescontrol.model.User;
import name.lorenzani.andrea.featurescontrol.model.Version;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

public abstract class GenericDataRestApi {

    protected abstract User getUser(String id);

    protected abstract Company getCompany(String id);

    protected abstract Version getVersion(String id);

    @RequestMapping(value = "/get/usr/{id}", method = RequestMethod.GET)
    public User getUserApi(@PathVariable String id) {
        String reqId = UUID.randomUUID().toString();
        try {
            return getUser(id);
        } catch (Throwable t) {
            throw new DataRetrieverException(reqId, String.format("Unable to retrieve user: %s", t.getMessage()), t);
        }
    }

    @RequestMapping(value = "/get/company/{id}", method = RequestMethod.GET)
    public Company getCompanyApi(@PathVariable String id) {
        String reqId = UUID.randomUUID().toString();
        try {
            return getCompany(id);
        } catch (Throwable t) {
            throw new DataRetrieverException(reqId, String.format("Unable to retrieve company: %s", t.getMessage()), t);
        }
    }

    @RequestMapping(value = "/get/version/{id}", method = RequestMethod.GET)
    public Version getVersionApi(@PathVariable String id) {
        String reqId = UUID.randomUUID().toString();
        try {
            return getVersion(id);
        } catch (Throwable t) {
            throw new DataRetrieverException(reqId, String.format("Unable to retrieve version: %s", t.getMessage()), t);
        }
    }
}
