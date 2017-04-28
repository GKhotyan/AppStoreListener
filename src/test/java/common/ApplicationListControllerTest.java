package common;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import controllers.rest.ApplicationListController;

@RunWith(SpringJUnit4ClassRunner.class)

//@ContextConfiguration(locations={"classpath:config/context.groovy"}, classes = Application.class)
@WebMvcTest(ApplicationListController.class)
//@EnableMongoRepositories("db.repositories")
public class ApplicationListControllerTest {
//
//  @Autowired
//  private WebApplicationContext wac;
//
//  private MockMvc mockMvc;
//
//  @Before
//  public void setup() {
//    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//  }
//
//  @Test
//  public void testExample() throws Exception {
//
//    this.mockMvc.perform(get("/aaa").accept(MediaType.TEXT_PLAIN))
//            .andExpect(status().isOk());
//  }
}
