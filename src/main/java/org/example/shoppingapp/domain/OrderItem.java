package org.example.shoppingapp.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_item")
public class OrderItem {
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name="purchased_price")
    private double purchasedPrice;
    @Column
    private int quantity;
    @Column(name="wholesale_price")
    private double wholesalePrice;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

}
