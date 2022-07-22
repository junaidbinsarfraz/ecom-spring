package com.ecomhunt.dtos;

import com.ecomhunt.entities.Address;
import com.ecomhunt.entities.Customer;
import com.ecomhunt.entities.Order;
import com.ecomhunt.entities.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
