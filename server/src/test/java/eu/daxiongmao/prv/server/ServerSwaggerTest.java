package eu.daxiongmao.prv.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import eu.daxiongmao.prv.server.util.ApiDocsResultHandler;


/** Dummy test that will generate SWAGGER documentation.<br>
 * You should always run that test!
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServerApplication.class})
@WebAppConfiguration
public class ServerSwaggerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void saveApiDocs() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(ApiDocsResultHandler.outputDirectory("target").build())
        .andExpect(status().isOk());
    }
}
