package com.example.plantslife.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "favorite_plants")
@IdClass(Favorite.class)
public class Favorite implements Serializable {

	@Id
	private Long userId;
	@Id
	private Long plantId;
	
	public Favorite() {
	}
	
	public Favorite(Long userId, Long plantId) {
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
