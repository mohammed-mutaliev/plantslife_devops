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

import com.example.plantslife.repository.MessageRepository;
import com.example.plantslife.repository.UserRepository;

@WithMockUser(username="test", roles={"USER"})
@TestMethodOrder(OrderAnnotation.class) 
@SpringBootTest(classes = PlantslifeDevopsApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class MessageTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private UserRepository userRepository;
	
	@MockBean
    private MessageRepository messageRepository;
	
	@Order(1)
	@Test
	public void addsNewUser() throws Exception{
		long id = userRepository.findTopByOrderByIdDesc().getId();
		String newMessage = "{\"text\":\"testing\",\"senderId\":" + id + ",\"receiverId\":" + id + "}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/messages")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newMessage)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
	@Order(2)
	@Test
	public void getsAllMessages() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(3)
	@Test
	public void getsSingleMessage() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/" + messageRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(4)
	@Test
	public void returnsNotFoundForInvalidSingleMessage() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/" + (messageRepository.findTopByOrderByIdDesc().getId()+1))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
	}
	
	@Order(5)
	@Test
	public void getsSingleMessageOverview() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/overview/" + messageRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(6)
	@Test
	public void getsUsersCountUnreadMessages() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/"+ userRepository.findTopByOrderByIdDesc().getId() + "/messages/unread")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(7)
	@Test
	public void getsMessagesBetweenUsers() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/"+ userRepository.findTopByOrderByIdDesc().getId() + "/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(8)
	@Test
	public void setsMessagesBetweenUsersRead() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/read/"+ userRepository.findTopByOrderByIdDesc().getId() + "/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(9)
	@Test
	public void setsMessageRead() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/" + messageRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Order(10)
	@Test
	public void removeNewMessage() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/messages/" + messageRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
}

