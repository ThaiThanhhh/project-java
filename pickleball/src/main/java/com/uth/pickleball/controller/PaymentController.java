package com.uth.pickleball.controller;

import com.uth.pickleball.model.Booking;
import com.uth.pickleball.model.Payment;
import com.uth.pickleball.service.BookingService;
import com.uth.pickleball.service.PaymentService;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private PaymentService paymentService;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) throws Exception {
        String payload = new BufferedReader(request.getReader()).lines().collect(Collectors.joining());
        String sigHeader = request.getHeader("Stripe-Signature");

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();
            String sessionId = session.getId();
            Optional<Booking> bookingOpt = bookingService.findByStripeSessionId(sessionId);
            if (bookingOpt.isPresent()) {
                Booking booking = bookingOpt.get();
                booking.setStatus("PAID");
                bookingService.createBooking(booking);

                Payment payment = new Payment();
                payment.setBookingId(booking.getId());
                payment.setStripePaymentIntentId(session.getPaymentIntent());
                payment.setStatus("SUCCESS");
                paymentService.savePayment(payment);
            }
        }
        return ResponseEntity.ok("");
    }
} 