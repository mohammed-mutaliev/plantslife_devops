package com.example.plantslife;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.plantslife.repository.UserRepository;

@WithMockUser(username="test", roles={"USER"})
@TestMethodOrder(OrderAnnotation.class) 
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlantslifeDevopsApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class FavoriteTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private UserRepository userRepository;
	
	@Order(1)
	@Test
	public void addsNewFavorite() throws Exception{
		String newFollow = "{\"userId\":" + userRepository.findTopByOrderByIdDesc().getId() + ",\"plantId\":" + 1 + "}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/favorite")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newFollow)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
	@Order(2)
	@Test
	public void getsFavoritesByUserId() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/favorite/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(3)
	@Test
	public void isFavorite() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/favorite/"+ userRepository.findTopByOrderByIdDesc().getId() + "/plant/" + 1)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(4)
	@Test
	public void removeNewFavorite() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/favorite/"+ userRepository.findTopByOrderByIdDesc().getId() + "/plant/" + 1)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
}


