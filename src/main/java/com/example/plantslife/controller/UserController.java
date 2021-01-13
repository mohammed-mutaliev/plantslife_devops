package com.example.plantslife.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.plantslife.exception.ResourceNotFoundException;
import com.example.plantslife.model.Follow;
import com.example.plantslife.model.User;
import com.example.plantslife.model.User.UserOverview;
import com.example.plantslife.model.User.getUser;
import com.example.plantslife.repository.CommentRepository;
import com.example.plantslife.repository.FollowRepository;
import com.example.plantslife.repository.UserRepository;

import org.json.simple.JSONObject;    

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	// get all messages
//	@GetMapping("/users")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public List<User> getAllEmployees() {
//		return userRepository.findAll();
//	}
	
	@GetMapping("/users/access")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> tryAccess() {
		return ResponseEntity.ok("Granted");
	}
	
	@PostMapping("/users/{id}/update_location")
	public ResponseEntity<String> updateLocation(@PathVariable long id, @RequestBody User userLocationData){
		User user = userRepository.findById(id).get();
		user.setLatitude(userLocationData.getLatitude());
		user.setLongitude(userLocationData.getLongitude());
		userRepository.save(user);
		return ResponseEntity.ok("Location updated");
		
	}

	// create message
	@PostMapping("/users")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		userRepository.save(user);
		return ResponseEntity.created(null).body(user);
	}

	// get all user overviews 
		@SuppressWarnings("unchecked")
		@GetMapping("/users/overview")
		public ResponseEntity<List<JSONObject>> getAllUserOverviews() {
			List<User> users = userRepository.getAllOverview();
			
			List<JSONObject> res = new ArrayList<>();
			for (User tempUser : users) {
				long countFollows = followRepository.countFollowsByUserId(tempUser.getId());
				long countComments = commentRepository.countCommentByUserId(tempUser.getId());
				long countFollowers = followRepository.countFollowersByUserId(tempUser.getId());
				
				JSONObject tempObj=new JSONObject();			
				tempObj.put("id", tempUser.getId());
				tempObj.put("username", tempUser.getUsername());
				tempObj.put("latitude", tempUser.getLatitude());
				tempObj.put("longitude", tempUser.getLongitude());
				tempObj.put("nrOfFollows", countFollows);
				tempObj.put("nrOfComments", countComments);
				tempObj.put("nrOfFollowers", countFollowers);
				tempObj.put("profileImage", tempUser.getProfileImage());
				res.add(tempObj);
			}
			return ResponseEntity.ok(res);
		}
		
	// get all user overviews 
	@SuppressWarnings("unchecked")
	@GetMapping("/users/{id}/followers")
	public ResponseEntity<List<JSONObject>> getAllFollowers(@PathVariable long id) {
		List<Long> followers = followRepository.getFollowersByUserId(id);		
		List<JSONObject> res = new ArrayList<>();
		
		for (Long tempFollowerId : followers) {
			UserOverview tempUser = userRepository.getUserOverview(tempFollowerId);
			long countFollows = followRepository.countFollowsByUserId(tempUser.getId());
			long countComments = commentRepository.countCommentByUserId(tempUser.getId());
			long countFollowers = followRepository.countFollowersByUserId(tempUser.getId());
						
			JSONObject tempObj=new JSONObject();			
			tempObj.put("id", tempUser.getId());
			tempObj.put("username", tempUser.getUsername());
			tempObj.put("latitude", tempUser.getLatitude());
			tempObj.put("longitude", tempUser.getLongitude());
			tempObj.put("nrOfFollows", countFollows);
			tempObj.put("nrOfComments", countComments);
			tempObj.put("nrOfFollowers", countFollowers);
			tempObj.put("profileImage", tempUser.getProfileImage());
			res.add(tempObj);
		}
		return ResponseEntity.ok(res);
	}
	
	// get all user overviews 
	@SuppressWarnings("unchecked")
	@GetMapping("/users/{id}/follows")
	public ResponseEntity<List<JSONObject>> getAllFollows(@PathVariable long id) {
		List<Long> followers = followRepository.getFollowsByUserId(id);		
		List<JSONObject> res = new ArrayList<>();
			
		for (Long tempFollowerId : followers) {
			UserOverview tempUser = userRepository.getUserOverview(tempFollowerId);
			long countFollows = followRepository.countFollowsByUserId(tempUser.getId());
			long countComments = commentRepository.countCommentByUserId(tempUser.getId());
			long countFollowers = followRepository.countFollowersByUserId(tempUser.getId());
							
			JSONObject tempObj=new JSONObject();			
			tempObj.put("id", tempUser.getId());
			tempObj.put("username", tempUser.getUsername());
			tempObj.put("latitude", tempUser.getLatitude());
			tempObj.put("longitude", tempUser.getLongitude());
			tempObj.put("nrOfFollows", countFollows);
			tempObj.put("nrOfComments", countComments);
			tempObj.put("nrOfFollowers", countFollowers);
			tempObj.put("profileImage", tempUser.getProfileImage());
			res.add(tempObj);
		}
		return ResponseEntity.ok(res);
	}
			
	// get user by id
	@SuppressWarnings("unchecked")
	@GetMapping("/users/{id}")
	//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<JSONObject> getUserById(@PathVariable long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id :" + id));
		long countFollows = followRepository.countFollowsByUserId(id);
		long countComments = commentRepository.countCommentByUserId(id);
		long countFollowers = followRepository.countFollowersByUserId(id);
		JSONObject tempObj=new JSONObject();			
		tempObj.put("id", user.getId());
		tempObj.put("username", user.getUsername());
		tempObj.put("nrOfFollows", countFollows);
		tempObj.put("nrOfComments", countComments);
		tempObj.put("nrOfFollowers", countFollowers);
		tempObj.put("profileImage", user.getProfileImage());
		return ResponseEntity.ok(tempObj);
	}
	
	 //get userid by username
	 @GetMapping("/users/{username}/get-id")
	 //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	 public ResponseEntity<Object> getUserIdByUsername(@PathVariable String username)
	 {
		 long result = userRepository.getUserIdByUsername(username);
		 return ResponseEntity.ok(result);
	 }
	 
	 //get username by userid
	 @GetMapping("/users/{id}/get-username")
	 //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	 public ResponseEntity<Object> getUsernameById(@PathVariable long id)
	 {
		 String result = userRepository.getUsernameById(id);
		 return ResponseEntity.ok(result);
	 }
	
	/*@SuppressWarnings("unchecked")
	@GetMapping("/users/{id}/friends/overview")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<JSONObject> getAllFriendsUserOverviews(@PathVariable long id) {
		List<Friends> friends = Arrays.asList(new RestTemplate().getForEntity(
		        "http://localhost:8082/api/v1/users/{id}/friends", Friends[].class, id).getBody());
		List<Long> friendsUserIds = new ArrayList<>();
		List<UserOverview> result = new ArrayList<>();
		
		for (Friends tempFriend : friends) 
		{ 
		    if(tempFriend.getFriendId1() == id) friendsUserIds.add(tempFriend.getFriendId2());
		    else {
		    	friendsUserIds.add(tempFriend.getFriendId1());
		    }
		    
		}
		
		for (Long tempUserId : friendsUserIds) {
			result.add(userRepository.getUserOverview(tempUserId));
		}
		
		List<JSONObject> res = new ArrayList<>();
		//for (UserOverview tempUser : result) 
		for (int i = 0; i < result.size(); i++){
			JSONObject tempObj=new JSONObject();
			tempObj.put("username", result.get(i).getUsername());
			tempObj.put("firstname", result.get(i).getFirstname());
			tempObj.put("lastname", result.get(i).getLastname());
			tempObj.put("friendsId", friends.get(i).getId());
			res.add(tempObj);
		}
		return res;
	}
	*/
	 
	@SuppressWarnings("unchecked")
	@GetMapping("/users/search")
	public List<JSONObject> searchUserByUsername(@RequestParam(name = "username") String username){
		List<User> result = userRepository.searchUserByUsername(username);
		List<JSONObject> res = new ArrayList<>();
		for (User tempUser : result) {
			long countFollows = followRepository.countFollowsByUserId(tempUser.getId());
			long countComments = commentRepository.countCommentByUserId(tempUser.getId());
			long countFollowers = followRepository.countFollowersByUserId(tempUser.getId());
			
			JSONObject tempObj=new JSONObject();
			tempObj.put("id", tempUser.getId());
			tempObj.put("username", tempUser.getUsername());
			tempObj.put("latitude", tempUser.getLatitude());
			tempObj.put("longitude", tempUser.getLongitude());
			tempObj.put("nrOfFollows", countFollows);
			tempObj.put("nrOfComments", countComments);
			tempObj.put("nrOfFollowers", countFollowers);
			tempObj.put("profileImage", tempUser.getProfileImage());
			res.add(tempObj);
		}
		return res;
	}
}
