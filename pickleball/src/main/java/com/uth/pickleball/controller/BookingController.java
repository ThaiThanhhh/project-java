package com.uth.pickleball.controller;

import com.uth.pickleball.model.Booking;
import com.uth.pickleball.service.BookingService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostMapping("/checkout")
    public ResponseEntity<?> createBookingAndStripeSession(@RequestBody Booking booking) throws Exception {
        booking.setStatus("PENDING");
        Booking savedBooking = bookingService.createBooking(booking);

        Stripe.apiKey = stripeApiKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://yourdomain.com/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("https://yourdomain.com/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("vnd")
                                                .setUnitAmount(booking.getPrice() * 100L)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Booking Coach #" + booking.getCoachId())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .putMetadata("bookingId", savedBooking.getId().toString())
                .build();

        Session session = Session.create(params);
        savedBooking.setStripeSessionId(session.getId());
        bookingService.createBooking(savedBooking);

        Map<String, String> response = new HashMap<>();
        response.put("checkoutUrl", session.getUrl());
        return ResponseEntity.ok(response);
    }
} 