package technikal.task.fishmarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FishmarketApplicationTests {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	
	@WithMockUser
	@Test
	public void shouldSend403WhenCreateFishRequestWithUserRole() throws Exception {
		mvc.perform(post("/fish/create").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
	
	@WithMockUser
	@Test
	public void shouldSend403WhenDeleteFishRequestWithUserRole() throws Exception {
		mvc.perform(get("/fish/delete?id=1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
	
	@WithMockUser(roles="ADMIN")
	@Test
	public void shouldSendRedirectWhenDeleteFishRequestWithAdminRole() throws Exception {
		mvc.perform(get("/fish/delete?id=1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is3xxRedirection());
	}

}
