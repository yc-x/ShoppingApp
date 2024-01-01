package org.example.shoppingapp.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_table")
public class Order {
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name="date_placed")
    private Timestamp datePlaced;
    @Column(name="order_status")
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
