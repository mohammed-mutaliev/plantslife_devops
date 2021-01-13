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

import com.example.plantslife.repository.CommentRepository;
import com.example.plantslife.repository.UserRepository;

@WithMockUser(username="test", roles={"USER"})
@TestMethodOrder(OrderAnnotation.class) 
@SpringBootTest(classes = PlantslifeDevopsApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CommentTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private UserRepository userRepository;
	
	@MockBean
    private CommentRepository commentRepository;
	
//	@Order(1)
//	@Test
//	public void addsNewComment() throws Exception{
//		String newComment = "{\"userId\":" + userRepository.findTopByOrderByIdDesc().getId() + ",\"plantId\":1,\"text\":\"testing\"}";
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comment")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(newComment)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isCreated())
//				.andReturn();
//	}
	
//	@Order(2)
//	@Test
//	public void getsCommentsByUserId() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/" + userRepository.findTopByOrderByIdDesc().getId() + "/comments")
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
	
	@Order(3)
	@Test
	public void getsCommentsByPlantId() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/plant/" + 1 + "/comments")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
//	@Order(4)
//	@Test
//	public void removeNewComment() throws Exception{
//		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/comment/"+ commentRepository.findTopByOrderByIdDesc().getId())
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
}


