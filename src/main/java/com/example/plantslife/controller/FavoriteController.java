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
import com.example.plantslife.repository.FavoriteRepository;  

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class FavoriteController {

	@Autowired
	private FavoriteRepository favoriteRepository;

	// create owned
	@PostMapping("/favorite")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> createFavorite(@RequestBody Favorite favorite) {
		favoriteRepository.save(favorite);
		return ResponseEntity.created(null).body("create successful");
	}

	// get user by id
	@GetMapping("/favorite/{id}")
	public ResponseEntity<List<Long>> getFavoriteByUserId(@PathVariable long id) {
		List<Long> favorite = favoriteRepository.getFavoriteByUserId(id);
		return ResponseEntity.ok(favorite);
	}
	
	// get user by id
	@GetMapping("/favorite/{id1}/plant/{id2}")
	public ResponseEntity<Boolean> isFavorite(@PathVariable long id1, @PathVariable long id2) {
		Favorite favorite = favoriteRepository.isFavorite(id1, id2);
		if (favorite == null) {
			return ResponseEntity.ok(false);
		}
		return ResponseEntity.ok(true);
	}
	
	// get user by id
	@DeleteMapping("/favorite/{id1}/plant/{id2}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteFavorite(@PathVariable long id1, @PathVariable long id2) {
		favoriteRepository.delete(new Favorite(id1, id2));
		return ResponseEntity.ok(true);
	}
}
