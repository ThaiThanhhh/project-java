package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uth.pickleball.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    // Custom query methods can be defined here if needed
    // For example, to find a user by username:
    // User findByUsername(String username);
    
    // Or to check if a user exists by email:
    // boolean existsByEmail(String email); 
}



