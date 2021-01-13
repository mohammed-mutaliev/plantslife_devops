package com.example.plantslife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.example.plantslife.model.Favorite;
import com.example.plantslife.model.Follow;
import com.example.plantslife.repository.FavoriteRepository;
import com.example.plantslife.repository.FollowRepository;  

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class FollowController {

	@Autowired
	private FollowRepository followRepository;

	// create owned
	@PostMapping("/follow")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> createFollow(@RequestBody Follow follow) {
		followRepository.save(follow);
		return ResponseEntity.created(null).body("create successful");
	}
	// get user by id
	@GetMapping("/{id1}/follow/{id2}")
	public ResponseEntity<Boolean> isFollowing(@PathVariable long id1, @PathVariable long id2) {
		Follow follow = followRepository.isFollowing(id1, id2);
		if (follow == null) {
			return ResponseEntity.ok(false);
		}
		return ResponseEntity.ok(true);
	}
		
	// get user by id
	@DeleteMapping("/{id1}/follow/{id2}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteFollow(@PathVariable long id1, @PathVariable long id2) {
		followRepository.deleteById(followRepository.getFollowIdByUsers(id1, id2));
		return ResponseEntity.ok(true);
	}
}
