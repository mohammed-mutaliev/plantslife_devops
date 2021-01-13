package com.example.plantslife.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.plantslife.model.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>{
	@Query("SELECT plantId FROM Favorite f WHERE f.userId = ?1")
	List<Long> getFavoriteByUserId(long id);
	
	@Query("SELECT new Favorite(userId, plantId) FROM Favorite f WHERE f.userId = ?1 AND f.plantId = ?2")
	Favorite isFavorite(long userId, long plantId);
}
