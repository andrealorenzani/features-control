package name.lorenzani.andrea.featurescontrol.datasource.impl;

import name.lorenzani.andrea.featurescontrol.datasource.CompanySource;
import name.lorenzani.andrea.featurescontrol.datasource.GenericDatasource;
import name.lorenzani.andrea.featurescontrol.model.Capability;
import name.lorenzani.andrea.featurescontrol.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CompanySourceImpl extends GenericDatasource<Company> implements CompanySource {

    public CompanySourceImpl() {
        super(new ConcurrentHashMap<>(), Company.class);
    }

    @Override
    public Optional<Company> getCompany(String id) {
        return get(id);
    }

    @Override
    public Company addCompany(String id, String name) {
        return add(id, new Company(id, name));
    }

    @Override
    public Company setCapabilities(String id, List<Capability> capabilities) throws Exception {
        Company company = get(id).orElse(new Company(id, ""));
        company.getCapabilities().addAll(capabilities);
        return update(id, company);
    }

    @Override
    public Company removeCapabilities(String id, List<Capability> capabilities) throws Exception {
        Company company = get(id).orElse(new Company(id, ""));
        company.getCapabilities().removeAll(capabilities);
        return update(id, company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return getAllElements();
    }
}
