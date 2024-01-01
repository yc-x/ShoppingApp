package org.example.shoppingapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product")
public class Product {
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name="p_description")
    private String description;
    @Column(name="p_name")
    private String name;
    @Column
    private int quantity;
    @Column(name="retail_price")
    private double retailPrice;
    @Column(name="wholesale_price")
    private double wholesalePrice;

    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<User> users; //= new HashSet<User>();
}
