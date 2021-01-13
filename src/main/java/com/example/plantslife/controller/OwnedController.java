package com.example.plantslife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantslife.exception.ResourceNotFoundException;
import com.example.plantslife.model.Owned;
import com.example.plantslife.repository.OwnedRepository;  

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class OwnedController {

	@Autowired
	private OwnedRepository ownedRepository;

	// create owned
	@PostMapping("/owned")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Owned> createOwned(@RequestBody Owned owned) {
		ownedRepository.save(owned);
		return ResponseEntity.created(null).body(owned);
	}

	// get user by id
	@GetMapping("/owned/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Owned>> getOwnedByUserId(@PathVariable long id) {
		List<Owned> owned = ownedRepository.getOwnedByUserId(id);
		return ResponseEntity.ok(owned);
	}
}
