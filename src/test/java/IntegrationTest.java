import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/test-settings.xml"})
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class IntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

//    @Test
//    public void _01_createGamer() throws Exception {
//        mockMvc.perform(get("/welcome"))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//    }

//    @Test
//    public void _02_getClients() throws Exception {
//
//        final Client[] clients = parseResponse(mockMvc.perform(get("/clients"))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful())
//                .andReturn(), Client[].class);
//
//        Assert.assertEquals(1, clients.length);
//        Assert.assertNotNull(clients[0].getId());
//    }

    private <T> T parseResponse(MvcResult mvcResult, Class<T> clazz) throws IOException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsByteArray(), clazz);
    }

}
