package com.uth.pickleball.repositories;

import org.springframework.stereotype.Repository;

import com.uth.pickleball.model.Booking;
import com.uth.pickleball.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface IExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByBooking(Booking booking);

}
