package com.example.plantslife.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantslife.exception.ResourceNotFoundException;
import com.example.plantslife.model.Message;
import com.example.plantslife.model.MessageState;
import com.example.plantslife.model.User;
import com.example.plantslife.repository.MessageRepository;
import com.example.plantslife.repository.UserRepository;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class MessageController {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserRepository userRepository;
	
	// get all messages
	@GetMapping("/messages")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	// create logEntry
	@PostMapping("/messages")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Message> createMessage(@RequestBody Message message) {
		message.setState(MessageState.UNREAD);
		messageRepository.save(message);
		return ResponseEntity.created(null).body(message);
	}

	// get message by id
	@GetMapping("/messages/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
		Message message = messageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Message does not exist with id :" + id));
		return ResponseEntity.ok(message);
	}
	
	// get message by id
	@SuppressWarnings("unchecked")
	@GetMapping("/messages/overview/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<JSONObject>> getMessageOverview(@PathVariable Long id) {
		List<Message> temp = messageRepository.getUseridsThatChat(id);
		List<Long> userIds = new ArrayList<>();
		
		for (Message mesg : temp) {
			if(mesg.getSenderId() == id) {
				userIds.add(mesg.getReceiverId());
			} else {
				userIds.add(mesg.getSenderId());
			}
		}
		
		final Set<Long> setToReturn = new HashSet<>(); 
		final Set<Long> set1 = new HashSet<>();

		for (Long tempId : userIds)
		{
			if (!set1.add(tempId))
		    {
				setToReturn.add(tempId);
		    }
		}
		  
		List<JSONObject> res = new ArrayList<>();
		
		for (Long userId : set1) {
			
			JSONObject tempObj=new JSONObject();
			tempObj.put("userId", userId);
			tempObj.put("username", userRepository.getUsernameById(userId));
			tempObj.put("countUnreadMessages", messageRepository.CountUnreadMessagesBetweenUsers(userId, id));
			tempObj.put("lastMessage", messageRepository.getLastMessage(userId, id, PageRequest.of(0, 1)).getContent().get(0));
			tempObj.put("profileImage", userRepository.getProfileImageById((userId)));
			res.add(tempObj);
		}
		
		return ResponseEntity.ok(res);
	}
	
	// get message by id
	@GetMapping("/{id}/messages/unread")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Long> getCountUnreadMessages(@PathVariable Long id) {
		long count = messageRepository.CountUnreadMessages(id);
		return ResponseEntity.ok(count);
	}
	
	// get chat by friends id
	@GetMapping("/messages/{id1}/{id2}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Message>> getChatByFriendsId(@PathVariable long id1, @PathVariable long id2) {
		List<Message> messages =  messageRepository.getChatByUsersId(id1, id2);
		return ResponseEntity.ok(messages);
	}
	
	// get chat by friends id
	@GetMapping("/messages/read/{id1}/{id2}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> getUnreadMessagesRead(@PathVariable long id1, @PathVariable long id2) {
		List<Message> messages =  messageRepository.getUnreadMessages(id1, id2);
		for (Message mesg : messages) {
			mesg.setState(MessageState.READ);
			messageRepository.save(mesg);;
		}
		return ResponseEntity.ok("read");
	}
	
	// read message
	@GetMapping("/messages/read/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> readMessage(@PathVariable Long id) {
		Message message = messageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Message does not exist with id :" + id));
		message.setState(MessageState.READ);
		messageRepository.save(message);
		//response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok("read");
	}

	// delete message
	@DeleteMapping("/messages/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
		Message message = messageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Message does not exist with id :" + id));
		message.setState(MessageState.DELETED);
		messageRepository.save(message);
		//response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok("deleted");
	}
}
