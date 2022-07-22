package com.ecomhunt.entities;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="country")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String code;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<State> states;
}
