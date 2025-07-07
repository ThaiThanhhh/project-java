package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uth.pickleball.model.Booking;
import com.uth.pickleball.model.Coach;
import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {

    // Additional query methods can be defined here if needed
    // For example, to find bookings by user or date, etc.
    List<Booking> findByCoach(Coach coach);

}
