package com.example.plantslife.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.plantslife.model.User;
import com.example.plantslife.model.User.UserOverview;
import com.example.plantslife.model.User.getUser;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Query("SELECT new User(u.id, u.username, u.firstname, u.lastname) FROM User u WHERE u.username LIKE ?1%")
	List<User> searchUserByUsername(String username);

	@Query("SELECT new User(u.id, u.username, u.firstname, u.lastname, u.latitude, u.longitude, u.profileImage) FROM User u")
	List<User> getAllOverview();
	
	@Query("SELECT new User(u.id, u.username, u.firstname, u.lastname, u.latitude, u.longitude, u.profileImage) FROM User u WHERE u.id = ?1")
	UserOverview getUserOverview(long id);

	@Query("SELECT new User(u.id, u.username, u.firstname, u.lastname, u.email)  FROM User u WHERE u.username = ?1")
	Optional<getUser> getUserByUsername(String username);

	@Query("SELECT u.username FROM User u WHERE u.id = ?1")
	String getUsernameById(long id);
	
	@Query("SELECT u.profileImage FROM User u WHERE u.id = ?1")
	String getProfileImageById(long id);

	@Query("SELECT u.id FROM User u WHERE u.username = ?1")
	long getUserIdByUsername(String username);
	
}
