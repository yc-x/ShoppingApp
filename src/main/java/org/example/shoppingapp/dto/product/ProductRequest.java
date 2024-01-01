package org.example.shoppingapp.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class ProductRequest {
    @NotNull(message = "product name cannot be null")
    @NotBlank(message = "product name cannot be blank")
    private String name;

    private String description;

    @NotNull(message = "quantity cannot be null")
    private Integer quantity;

    @NotNull(message = "wholesale price cannot be null")
    private Double wholesalePrice;

    @NotNull(message = "wholesale price cannot be null")
    private Double retailPrice;
}
