package com.example.plantslife.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Follows")
public class Follow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	
	private long followerId;
	private long followsId;
	
	public Follow() {
		
	}
	
	public Follow(long followerId, long followsId) {
		super();
		this.followerId = followerId;
		this.followsId = followsId;
	}

	public Follow(long id, long followerId, long followsId) {
		super();
		this.id = id;
		this.followerId = followerId;
		this.followsId = followsId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(long followerId) {
		this.followerId = followerId;
	}

	public long getFollowsId() {
		return followsId;
	}

	public void setFollowsId(long followsId) {
		this.followsId = followsId;
	}

}
