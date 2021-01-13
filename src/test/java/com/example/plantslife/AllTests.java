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
import com.example.plantslife.repository.MessageRepository;
import com.example.plantslife.repository.UserRepository;

@SpringBootTest(classes = PlantslifeDevopsApplication.class)
@WithMockUser(username="test", roles={"USER"})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class) 
class AllTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private MessageRepository messageRepository;
	
	@Autowired
    private CommentRepository commentRepository;
	
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
		
	}
	
	@Order(3)
	@Test
	public void getsSingleUser() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(4)
	@Test
	public void returnsNotFoundForInvalidSingleUser() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + (userRepository.findTopByOrderByIdDesc().getId()+1))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
	}
	
	@Order(5)
	@Test
	public void searchUsersForUsername() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/search?username=testing")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(6)
	@Test
	public void getsAllUsersOverview() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/overview")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(7)
	@Test
	public void getsAllUserFollowers() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/"+ userRepository.findTopByOrderByIdDesc().getId() + "/followers")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(8)
	@Test
	public void getsAllUserFollows() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/"+ userRepository.findTopByOrderByIdDesc().getId() + "/follows")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(9)
	@Test
	public void addsLocationToUser() throws Exception{
		String newLocation = "{\"latitude\":22,\"longitude\":22}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/"+ userRepository.findTopByOrderByIdDesc().getId() + "/update_location")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newLocation)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(10)
	@Test
	public void addsNewMessage() throws Exception{
		long id = userRepository.findTopByOrderByIdDesc().getId();
		String newMessage = "{\"text\":\"testing\",\"senderId\":" + id + ",\"receiverId\":" + id + "}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/messages")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newMessage)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
	@Order(11)
	@Test
	public void getsAllMessages() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(12)
	@Test
	public void getsSingleMessage() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/" + messageRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(13)
	@Test
	public void returnsNotFoundForInvalidSingleMessage() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/" + (messageRepository.findTopByOrderByIdDesc().getId()+1))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
	}
	
	@Order(15)
	@Test
	public void getsUsersCountUnreadMessages() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/"+ userRepository.findTopByOrderByIdDesc().getId() + "/messages/unread")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(16)
	@Test
	public void getsMessagesBetweenUsers() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/"+ userRepository.findTopByOrderByIdDesc().getId() + "/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(17)
	@Test
	public void setsMessagesBetweenUsersRead() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/read/"+ userRepository.findTopByOrderByIdDesc().getId() + "/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(18)
	@Test
	public void setsMessageRead() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/" + messageRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Order(19)
	@Test
	public void removeNewMessage() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/messages/" + messageRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(20)
	@Test
	public void addsNewFollow() throws Exception{
		String newFollow = "{\"followerId\":" + userRepository.findTopByOrderByIdDesc().getId() + ",\"followsId\":" + userRepository.findTopByOrderByIdDesc().getId() + "}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/follow")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newFollow)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
	@Order(21)
	@Test
	public void isFollowing() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/"+ userRepository.findTopByOrderByIdDesc().getId() + "/follow/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(22)
	@Test
	public void removeNewFollow() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/"+ userRepository.findTopByOrderByIdDesc().getId() + "/follow/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(23)
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
	
	@Order(24)
	@Test
	public void getsFavoritesByUserId() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/favorite/" + userRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(25)
	@Test
	public void isFavorite() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/favorite/"+ userRepository.findTopByOrderByIdDesc().getId() + "/plant/" + 1)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(26)
	@Test
	public void removeNewFavorite() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/favorite/"+ userRepository.findTopByOrderByIdDesc().getId() + "/plant/" + 1)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(27)
	@Test
	public void addsNewComment() throws Exception{
		String newComment = "{\"userId\":" + userRepository.findTopByOrderByIdDesc().getId() + ",\"plantId\":1,\"text\":\"testing\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newComment)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
	@Order(28)
	@Test
	public void getsCommentsByUserId() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/" + userRepository.findTopByOrderByIdDesc().getId() + "/comments")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(29)
	@Test
	public void getsCommentsByPlantId() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/plant/" + 1 + "/comments")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Order(30)
	@Test
	public void removeNewComment() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/comment/"+ commentRepository.findTopByOrderByIdDesc().getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}
	
}

