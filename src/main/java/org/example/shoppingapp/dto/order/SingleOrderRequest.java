package org.example.shoppingapp.dto.order;

import lombok.*;
import org.example.shoppingapp.domain.OrderItem;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleOrderRequest {
    @NotNull(message = "product id cannot be null")
    private Long productId;
    @NotNull(message = "quantity cannot be null")
    private Integer quantity;

}
