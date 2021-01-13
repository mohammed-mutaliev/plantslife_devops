package com.example.plantslife.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "messages")
public class Message {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 

	@Column(columnDefinition = "LONGTEXT")
	private String text;
	
	private MessageState state;
	
	@Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime timeStamp;
	
	private long senderId;
	
	private long receiverId; 
	
	@Transient
	private String username;
	
	public Message() {
		
	}  

	public Message(long senderId, long receiverId) {
		this.senderId = senderId;
		this.receiverId = receiverId;
	}
	
	public Message(String text, MessageState state, long senderId, long receiverId) {
		this.text = text;
		this.state = state;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}
	
	public Message(long id, String text, LocalDateTime timeStamp, long senderId, long receiverId, MessageState state) {
		this.id = id;
		this.text = text;
		this.timeStamp = timeStamp;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MessageState getState() {
		return state;
	}

	public void setState(MessageState state) {
		this.state = state;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
