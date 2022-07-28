package com.ecomhunt.resources;

import com.ecomhunt.dtos.PaymentInfo;
import com.ecomhunt.dtos.Purchase;
import com.ecomhunt.dtos.PurchaseResponse;
import com.ecomhunt.services.CustomerService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/checkout")
public class CheckoutResource {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse purchaseResponse = this.customerService.placeOrder(purchase);

        return ResponseEntity.ok().body(purchaseResponse);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = this.customerService.createPaymentIntent(paymentInfo);

        return new ResponseEntity<>(paymentIntent.toJson(), HttpStatus.OK);
    }
}
