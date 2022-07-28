package com.ecomhunt.services;

import com.ecomhunt.dtos.PaymentInfo;
import com.ecomhunt.dtos.Purchase;
import com.ecomhunt.dtos.PurchaseResponse;
import com.ecomhunt.entities.Customer;
import com.ecomhunt.entities.Order;
import com.ecomhunt.entities.OrderItem;
import com.ecomhunt.repositories.CustomerRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(@Value("${stripe.key.secret}") String stripeSecretKey) {
        Stripe.apiKey = stripeSecretKey;
    }

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Order order = purchase.getOrder();

        String orderTrackingNumber = UUID.randomUUID().toString();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(orderItem -> {
            order.add(orderItem);
        });

        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());

        Customer customer = purchase.getCustomer();

        Customer customerDb = this.customerRepository.findByEmail(customer.getEmail());

        if(customerDb != null) {
            customer = customerDb;
        }

        customer.add(order);

        this.customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethods);
        params.put("description", "Ecom Purchase");
        params.put("receipt_email", paymentInfo.getEmail());

        return PaymentIntent.create(params);
    }
}
