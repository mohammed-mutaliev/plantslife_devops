package com.example.plantslife.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.plantslife.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
	Message findTopByOrderByIdDesc();
	
	@Query("SELECT new Message(m.id, m.text, m.timeStamp, m.senderId, m.receiverId, m.state) FROM Message m WHERE (m.state != com.example.plantslife.model.MessageState.DELETED AND m.senderId= ?1 AND m.receiverId= ?2) OR (m.state != com.example.plantslife.model.MessageState.DELETED AND m.senderId= ?2 AND m.receiverId= ?1) ORDER BY timeStamp ASC")
	List<Message> getChatByUsersId(long id1, long id2); 
	
	@Query("SELECT COUNT(m) FROM Message m WHERE m.receiverId = ?1 AND m.state = com.example.plantslife.model.MessageState.UNREAD")
    long CountUnreadMessages(Long id);
	
	@Query("SELECT DISTINCT new Message(m.senderId, m.receiverId) FROM Message m WHERE (m.state != com.example.plantslife.model.MessageState.DELETED AND m.senderId= ?1) OR (m.state != com.example.plantslife.model.MessageState.DELETED AND m.receiverId= ?1) ORDER BY timeStamp ASC")
    List<Message> getUseridsThatChat(long id);
	
	@Query("SELECT COUNT(m) FROM Message m WHERE m.senderId = ?1 AND m.receiverId = ?2 AND m.state = com.example.plantslife.model.MessageState.UNREAD")
    long CountUnreadMessagesBetweenUsers(long id1, long id2);
	
	@Query("SELECT new Message(m.id, m.text, m.timeStamp, m.senderId, m.receiverId, m.state) FROM Message m WHERE (m.state != com.example.plantslife.model.MessageState.DELETED AND m.senderId= ?1 AND m.receiverId= ?2) OR (m.state != com.example.plantslife.model.MessageState.DELETED AND m.senderId= ?2 AND m.receiverId= ?1) ORDER BY timeStamp DESC")
	Page<Message> getLastMessage(long id1, long id2, Pageable pageable); 
	
	@Query("SELECT new Message(m.id, m.text, m.timeStamp, m.senderId, m.receiverId, m.state) FROM Message m WHERE m.senderId = ?1 AND m.receiverId = ?2 AND m.state = com.example.plantslife.model.MessageState.UNREAD")
	List<Message> getUnreadMessages(long id1, long id2); 
}
