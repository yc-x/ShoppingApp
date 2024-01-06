package org.example.shoppingapp.dto.order;

import lombok.*;
import org.example.shoppingapp.domain.OrderItemDetail;
import org.example.shoppingapp.dto.product.ProductResponse;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Timestamp datePlaced;
    private List<OrderItemDetail> orderItemDetails;
    private String status;
}
