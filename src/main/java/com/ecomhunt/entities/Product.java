package com.ecomhunt.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String imageUrl;
    private Boolean active;
    private Integer unitsInStock;
    @CreationTimestamp
    private Date dateCreated;
    @UpdateTimestamp
    private Date lastUpdated;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

}
