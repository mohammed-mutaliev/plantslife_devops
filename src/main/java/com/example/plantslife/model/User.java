package com.example.plantslife.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })

public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String firstname;
	
	@NotBlank
	@Size(max = 20)
	private String lastname;

	@NotBlank
	@Size(max = 50)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;
	
	@Column(name="profile_image")
	private String profileImage = "https://schooloflanguages.sa.edu.au/wp-content/uploads/2017/11/placeholder-profile-sq.jpg";
	
	private double latitude, longitude;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(targetEntity = Favorite.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private List<Favorite> favoritePlants;
	
	@OneToMany(targetEntity = Owned.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private List<Owned> ownedPlants; 

	@OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JoinColumn(name = "username", referencedColumnName = "username")
	private List<Comment> comments;
	
	@OneToMany(targetEntity = Follow.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "followerId", referencedColumnName = "id")
	private List<Follow> following;
	
	@OneToMany(targetEntity = Follow.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "followsId", referencedColumnName = "id")
	private List<Follow> follows;
	
	@OneToMany(targetEntity = Message.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "senderId", referencedColumnName = "id")
	private List<Message> sentMessages;
	
	public interface UserOverview { 
		Long getId();
		String getUsername();
		String getFirstname();
		String getLastname();
		double getLatitude();
		double getLongitude();
		String getProfileImage();
	}

	public interface getUser {
		Long getId();
		String getUsername();
		String getFirstname();
		String getLastname();
		String getEmail();
	}
	
	public User() {
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public User(Long id, String username, String firstname, String lastname) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public User(Long id, String username, String firstname, String lastname, double latitude, double longitude, String profileImage) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.latitude = latitude;
		this.longitude = longitude;
		this.profileImage = profileImage;
	}
	
	public User(Long id, String username, String firstname, String lastname, String email) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	
	public User(String username, String email, String password, String firstname, String lastname) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public List<Favorite> getFavoritePlants() {
		return favoritePlants;
	}

	public void setFavoritePlants(List<Favorite> favoritePlants) {
		this.favoritePlants = favoritePlants;
	}

	public List<Owned> getOwnedPlants() {
		return ownedPlants;
	}

	public void setOwnedPlants(List<Owned> ownedPlants) {
		this.ownedPlants = ownedPlants;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Follow> getFollowing() {
		return following;
	}

	public void setFollowing(List<Follow> following) {
		this.following = following;
	}

	public List<Follow> getFollows() {
		return follows;
	}

	public void setFollows(List<Follow> follows) {
		this.follows = follows;
	}

	public List<Message> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
}
