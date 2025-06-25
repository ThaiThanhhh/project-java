package com.uth.pickleball.service;

import com.uth.pickleball.model.Payment;
import com.uth.pickleball.repositories.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private IPaymentRepository paymentRepo;

    public Payment savePayment(Payment payment) {
        return paymentRepo.save(payment);
    }

    public Optional<Payment> findByBookingId(Long bookingId) {
        return paymentRepo.findByBookingId(bookingId);
    }
} 