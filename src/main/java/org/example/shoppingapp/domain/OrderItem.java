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
    private Long id;

    @Column(name="purchased_price")
    private Double purchasedPrice;
    @Column
    private Integer quantity;
    @Column(name="wholesale_price")
    private Double wholesalePrice;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

}
