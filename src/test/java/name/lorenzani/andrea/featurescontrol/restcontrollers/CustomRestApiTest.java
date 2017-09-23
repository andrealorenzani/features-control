package name.lorenzani.andrea.featurescontrol.restcontrollers;

import name.lorenzani.andrea.featurescontrol.datasource.ClientSource;
import name.lorenzani.andrea.featurescontrol.datasource.CompanySource;
import name.lorenzani.andrea.featurescontrol.datasource.VersionSource;
import name.lorenzani.andrea.featurescontrol.model.Company;
import name.lorenzani.andrea.featurescontrol.model.User;
import name.lorenzani.andrea.featurescontrol.model.Version;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomRestApi.class)
public class CustomRestApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestTemplate template;

    @MockBean
    private ClientSource usrSrc;

    @MockBean
    private CompanySource companySrc;

    @MockBean
    private VersionSource verSrc;

    @Before
    public void setup() {
        when(usrSrc.getClient("id")).thenReturn(Optional.of(new User("id", "name")));
        when(companySrc.getCompany("id")).thenReturn(Optional.of(new Company("id", "name")));
        when(verSrc.getVersion("id")).thenReturn(Optional.of(new Version("id")));
    }

    @Test
    public void itShouldSuccessRetrievingElements() throws Exception {
        this.mvc.perform(get("/custom/get/usr/id").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        this.mvc.perform(get("/custom/get/company/id").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        this.mvc.perform(get("/custom/get/version/id").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldFailOnMissingElement() throws Exception {

        this.mvc.perform(get("/custom/get/company/noid").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        this.mvc.perform(get("/custom/get/version/noid").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        this.mvc.perform(get("/custom/get/usr/noid").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}