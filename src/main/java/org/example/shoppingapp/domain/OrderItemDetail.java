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
    private Long productId;

    public static OrderItemDetail buildOrderItemDetailFromOrderItem(OrderItem orderItem){
        return OrderItemDetail.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .wholesalePrice(orderItem.getWholesalePrice())
                .purchasedPrice(orderItem.getPurchasedPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
