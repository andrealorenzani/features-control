package name.lorenzani.andrea.featurescontrol.datasource;

import name.lorenzani.andrea.featurescontrol.datasource.impl.CapabilitySourceImpl;
import name.lorenzani.andrea.featurescontrol.datasource.impl.ClientSourceImpl;
import name.lorenzani.andrea.featurescontrol.datasource.impl.CompanySourceImpl;
import name.lorenzani.andrea.featurescontrol.datasource.impl.VersionSourceImpl;
import name.lorenzani.andrea.featurescontrol.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {


    private CapabilitySource capSource;
    private ClientSource usrSource;
    private CompanySource companySource;
    private VersionSource verSource;

    @Before
    public void setup() {
        capSource = new CapabilitySourceImpl();
        usrSource = new ClientSourceImpl();
        companySource = new CompanySourceImpl();
        verSource = new VersionSourceImpl();
    }

    @Test
    public void itShouldStartEmpty() {
        Assert.assertTrue(capSource.getAllCapabilities().isEmpty());
        Assert.assertTrue(usrSource.getAllUsers().isEmpty());
        Assert.assertTrue(companySource.getAllCompanies().isEmpty());
        Assert.assertTrue(verSource.getAllVersions().isEmpty());
    }

    @Test
    public void itShouldWorkNominalCases() throws Exception {
        capSource.addCapability("id", 0);
        capSource.addCapability("id2", 1);
        Assert.assertEquals("id", capSource.getCapability("id").get().getName());
        Assert.assertEquals(0, capSource.getCapability("id").get().getPos());
        Assert.assertEquals(2, capSource.getAllCapabilities().size());
        usrSource.addClient("id", "1");
        usrSource.addClient("id2", "2");
        usrSource.setCapabilities("id", capSource.getAllCapabilities());
        usrSource.setCapabilities("id2", capSource.getAllCapabilities());
        usrSource.removeCapabilities("id", capSource.getAllCapabilities());
        Assert.assertEquals("1", usrSource.getClient("id").get().getName());
        Assert.assertEquals("2", usrSource.getClient("id2").get().getName());
        Assert.assertEquals(0, usrSource.getClient("id").get().getCapabilities().size());
        Assert.assertEquals(2, usrSource.getClient("id2").get().getCapabilities().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldFailWhenSameIdCreated() {
        usrSource.addClient("id", "1");
        usrSource.addClient("id", "2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldFailWhenUpdatingNonEsistingElement() throws Exception {
        usrSource.setCapabilities("blabla", Collections.emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldFailWhenRemovingCapNonEsistingElement() throws Exception {
        usrSource.removeCapabilities("blabla", Collections.emptyList());
    }

    @Test
    public void itShouldReturnEmptyOptionWhenIdNotPresent() {
        Optional<User> result = usrSource.getClient("notpresent");
        Assert.assertFalse(result.isPresent());
    }

}
