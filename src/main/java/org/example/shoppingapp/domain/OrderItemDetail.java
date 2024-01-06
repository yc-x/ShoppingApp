package org.example.shoppingapp.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDetail {
    private Double purchasedPrice;
    private Integer quantity;
    private Double wholesalePrice;
    private String productName;

    public static OrderItemDetail buildOrderItemDetailFromOrderItem(OrderItem orderItem){
        return OrderItemDetail.builder()
                .productName(orderItem.getProduct().getName())
                .wholesalePrice(orderItem.getWholesalePrice())
                .purchasedPrice(orderItem.getPurchasedPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
