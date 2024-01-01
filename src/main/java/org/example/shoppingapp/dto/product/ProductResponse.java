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
    private double retailPrice;
    private double wholesalePrice;
}
