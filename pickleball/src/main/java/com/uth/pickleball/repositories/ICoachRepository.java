package com.uth.pickleball.repositories;
import org.springframework.stereotype.Repository;
import com.uth.pickleball.model.Coach;
import com.uth.pickleball.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ICoachRepository extends JpaRepository<Coach, String> {
    // Custom query methods can be defined here if needed
    // For example, to find a coach by user ID:
    
    Coach findByUser(User user);


}
