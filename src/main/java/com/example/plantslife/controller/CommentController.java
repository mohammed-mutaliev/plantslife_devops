package com.example.plantslife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantslife.exception.ResourceNotFoundException;
import com.example.plantslife.model.Comment;
import com.example.plantslife.repository.CommentRepository;  

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class CommentController {

	@Autowired
	private CommentRepository commentRepository;

	// create comment
	@PostMapping("/comment")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> createComment(@RequestBody Comment comment) {
		commentRepository.save(comment);
		return ResponseEntity.created(null).body("create successful");
	}
	
	// get user by id
	@GetMapping("/user/{id}/comments")
	public ResponseEntity<List<Comment>> getCommentByUserId(@PathVariable long id) {
		List<Comment> comments = commentRepository.getCommentsByUserId(id);
		return ResponseEntity.ok(comments);
	}

	// get user by id
	@GetMapping("/plant/{id}/comments")
	public ResponseEntity<List<Comment>> getCommentByPlantId(@PathVariable long id) {
		List<Comment> comments = commentRepository.getCommentsByPlantsId(id);
		return ResponseEntity.ok(comments);
	}
	
	// get user by id
	@DeleteMapping("/comment/{id1}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
		public ResponseEntity<Boolean> deleteComment(@PathVariable long id1) {
			commentRepository.deleteById(id1);
			return ResponseEntity.ok(true);
		}
}
