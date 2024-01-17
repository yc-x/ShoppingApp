package org.example.shoppingapp.dto.product;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String name;
    private String description;
    private Double retailPrice;
    private Double wholesalePrice;
    private Integer quantity;
    private Long id;

    private Integer soldQuantity;
    //TODO: implement a static method to build this response and replace duplicate code,
    // or just remove this object.
}
