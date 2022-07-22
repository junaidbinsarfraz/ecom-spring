package com.ecomhunt.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    public void add(Order order) {
        if(order != null) {
            if(orders == null) {
                this.orders = new HashSet<>();
            }
            this.orders.add(order);
            order.setCustomer(this);
        }
    }
}
