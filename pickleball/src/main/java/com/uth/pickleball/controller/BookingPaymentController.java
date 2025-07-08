package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingPaymentController {

    @GetMapping("/bookingpayment")
    public String showBookingPaymentPage() {
        return "private/features/booking_payment";
    }
    @GetMapping("/bookingpayment/course1")
    public String showBookingPaymentSuccessPage() {
        return "private/features/course1";
    }

}
