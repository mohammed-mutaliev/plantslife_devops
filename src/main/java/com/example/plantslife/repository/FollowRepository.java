package com.example.plantslife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.plantslife.model.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
	
	@Query("SELECT COUNT(f) FROM Follow f WHERE f.followsId = ?1")
    long countFollowersByUserId(Long id);
	
	@Query("SELECT COUNT(f) FROM Follow f WHERE f.followerId = ?1")
    long countFollowsByUserId(Long id);
	
	@Query("SELECT new Follow(f.id, followerId, followsId) FROM Follow f WHERE f.followerId = ?1 AND f.followsId = ?2")
	Follow isFollowing(long followerId, long followsId);
	
	@Query("SELECT f.id FROM Follow f WHERE f.followerId = ?1 AND f.followsId = ?2")
	long getFollowIdByUsers(long followerId, long followsId);
	
	@Query("SELECT followerId FROM Follow f WHERE f.followsId = ?1")
	List<Long> getFollowersByUserId(long followsId);
	
	@Query("SELECT followsId FROM Follow f WHERE f.followerId = ?1")
	List<Long> getFollowsByUserId(long followerId);
}
