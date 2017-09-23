package name.lorenzani.andrea.featurescontrol.restcontrollers;

import name.lorenzani.andrea.featurescontrol.datasource.ClientSource;
import name.lorenzani.andrea.featurescontrol.datasource.CompanySource;
import name.lorenzani.andrea.featurescontrol.datasource.VersionSource;
import name.lorenzani.andrea.featurescontrol.model.Company;
import name.lorenzani.andrea.featurescontrol.model.User;
import name.lorenzani.andrea.featurescontrol.model.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/custom")
public class CustomRestApi extends GenericDataRestApi {

    private final ClientSource clientDao;
    private final CompanySource companyDao;
    private final VersionSource versionDao;

    @Autowired
    public CustomRestApi(ClientSource clientDao,
                         CompanySource companyDao,
                         VersionSource versionDao) {
        this.clientDao = clientDao;
        this.companyDao = companyDao;
        this.versionDao = versionDao;
    }

    @Override
    protected User getUser(String id) {
        return clientDao.getClient(id).get();
    }

    @Override
    protected Company getCompany(String id) {
        return companyDao.getCompany(id).get();
    }

    @Override
    protected Version getVersion(String id) {
        return versionDao.getVersion(id).get();
    }
}
