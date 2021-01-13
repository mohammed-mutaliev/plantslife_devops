package com.example.plantslife.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.plantslife.model.Owned;

@Repository
public interface OwnedRepository extends JpaRepository<Owned, Long> {
	@Query("SELECT new Owned(userId, plantId) FROM Owned o WHERE o.userId = ?1")
	List<Owned> getOwnedByUserId(long id);
}