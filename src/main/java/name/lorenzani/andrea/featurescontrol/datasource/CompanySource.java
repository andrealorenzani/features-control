package name.lorenzani.andrea.featurescontrol.datasource;

import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanySource {
    Optional<Company> getCompany(String id);

    Company addCompany(String id, String name);

    Company setCapabilities(String id, List<Capability> capabilities) throws Exception;

    Company removeCapabilities(String id, List<Capability> capabilities) throws Exception;

    List<Company> getAllCompanies();
}
