package com.uth.pickleball.service;

import com.uth.pickleball.model.Booking;
import com.uth.pickleball.repositories.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private IBookingRepository bookingRepo;

    public Booking createBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    public void updateStatus(Long bookingId, String status) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow();
        booking.setStatus(status);
        bookingRepo.save(booking);
    }

    public Optional<Booking> findByStripeSessionId(String sessionId) {
        return bookingRepo.findByStripeSessionId(sessionId);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepo.findByUserId(userId);
    }
} 