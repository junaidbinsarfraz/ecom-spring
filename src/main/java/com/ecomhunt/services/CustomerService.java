package com.ecomhunt.services;

import com.ecomhunt.dtos.Purchase;
import com.ecomhunt.dtos.PurchaseResponse;
import com.ecomhunt.entities.Customer;
import com.ecomhunt.entities.Order;
import com.ecomhunt.entities.OrderItem;
import com.ecomhunt.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

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
}
