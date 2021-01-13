package com.example.plantslife;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.plantslife.repository.UserRepository;

@WithMockUser(username="test", roles={"USER"})
@TestMethodOrder(OrderAnnotation.class) 
@SpringBootTest(classes = PlantslifeDevopsApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private UserRepository userRepository;
	
	@Order(1)
	@Test
	public void addsNewUser() throws Exception{
		String newUser = "{\"firstname\":\"test\",\"lastname\":\"testsson\",\"username\":\"testing\",\"email\":\"testing@example.com\",\"password\":\"lollol\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newUser)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
		
		newUser = "{\"firstname\":\"test\",\"lastname\":\"testsson\",\"username\":\"testing1\",\"email\":\"testing1@example.com\",\"password\":\"lollol\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newUser)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
//	@Order(2)
//	@Test
//	public void getsAllUsers() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
	
//	@Order(3)
//	@Test
//	public void getsSingleUser() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + userRepository.findTopByOrderByIdDesc().getId())
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
	
//	@Order(4)
//	@Test
//	public void returnsNotFoundForInvalidSingleUser() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + (userRepository.findTopByOrderByIdDesc().getId()+1))
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNotFound())
//				.andReturn();
//	}
	
	@Order(5)
	@Test
	public void searchUsersForUsername() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/search?username=testing")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(5)
	@Test
	public void getsAllUsersOverview() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/overview")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
//	@Order(6)
//	@Test
//	public void getsAllUserFollowers() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/"+ userRepository.findTopByOrderByIdDesc().getId() + "/followers")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
	
//	@Order(7)
//	@Test
//	public void getsAllUserFollows() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/"+ userRepository.findTopByOrderByIdDesc().getId() + "/follows")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
	
//	@Order(8)
//	@Test
//	public void addsLocationToUser() throws Exception{
//		String newLocation = "{\"latitude\":22,\"longitude\":22}";
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/"+ userRepository.findTopByOrderByIdDesc().getId() + "/update_location")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(newLocation)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
	
//	@Order(9)
//	@Test
//	public void removeNewUser() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/" + userRepository.findTopByOrderByIdDesc().getId())
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
}
