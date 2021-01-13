package com.example.plantslife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.plantslife.model.Message;
import com.example.plantslife.repository.MessageRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private MessageRepository chatMessageService;

	@MessageMapping("/chat")
	public void processMessage(@Payload Message chatMessage) {
		chatMessageService.save(chatMessage);
		messagingTemplate.convertAndSendToUser(String.valueOf(chatMessage.getReceiverId()) + String.valueOf(chatMessage.getSenderId()), "/queue/messages", chatMessage);
		messagingTemplate.convertAndSendToUser(String.valueOf(chatMessage.getReceiverId()), "/queue/messages", chatMessage);
	}
}
