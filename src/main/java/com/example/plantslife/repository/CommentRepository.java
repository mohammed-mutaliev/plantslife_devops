package com.example.plantslife.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.plantslife.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	@Query("SELECT new Comment(id, userId, username, plantId, text, timeStamp) FROM Comment c WHERE c.plantId = ?1")
	List<Comment> getCommentsByPlantsId(Long id);
	
	@Query("SELECT new Comment(id, userId, username, plantId, text, timeStamp) FROM Comment c WHERE c.userId = ?1")
	List<Comment> getCommentsByUserId(Long id);
	
	@Query("SELECT COUNT(c) FROM Comment c WHERE c.userId = ?1")
    long countCommentByUserId(Long id);
}
