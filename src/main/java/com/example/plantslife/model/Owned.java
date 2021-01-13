package com.example.plantslife.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "owned_plants")
@IdClass(Owned.class)
public class Owned implements Serializable {

	@Id
	private Long userId;
	@Id
	private Long plantId;
	
	public Owned() {
	}
	
	public Owned(Long userId, Long plantId) {
		super();
		this.userId = userId;
		this.plantId = plantId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPlantId() {
		return plantId;
	}

	public void setPlantId(Long plantId) {
		this.plantId = plantId;
	}
	
	

}
