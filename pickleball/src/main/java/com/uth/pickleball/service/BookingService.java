package com.uth.pickleball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uth.pickleball.model.Booking;
import com.uth.pickleball.model.Coach;
import com.uth.pickleball.repositories.IBookingRepository;
import java.util.List;


@Service
public class BookingService {
 @Autowired
    private IBookingRepository bookingRepository;

    public List<Booking> getBookingsByCoach(Coach coach) {
        return bookingRepository.findByCoach(coach);
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }
    public Booking findById(Long id) {
    return bookingRepository.findById(id).orElse(null);
}

}
