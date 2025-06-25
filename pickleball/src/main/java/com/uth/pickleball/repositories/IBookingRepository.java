package com.uth.pickleball.repositories;

import com.uth.pickleball.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IBookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    Optional<Booking> findByStripeSessionId(String stripeSessionId);
} 