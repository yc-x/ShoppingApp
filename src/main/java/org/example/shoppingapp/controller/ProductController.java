package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.product.ProductResponse;
import org.example.shoppingapp.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    @ResponseBody
    public DataResponse getAllProducts(){
        List<ProductResponse> responseList = productService.getAllProducts().stream().
                map(p -> ProductResponse.builder()
                        .name(p.getName())
                        .description(p.getDescription())
                        .retailPrice(p.getRetailPrice())
                        .wholesalePrice(p.getWholesalePrice())
                        .build()
                ).collect(Collectors.toList());

        return DataResponse.builder()
                .data(responseList)
                .success(true)
                .message("Get All products")
                .build();
    }

    @GetMapping("/{productId}")
    public DataResponse getProductById(@PathVariable long productId){
        Product product = productService.getProductById(productId);
        if(product != null){
            ProductResponse productResponse = ProductResponse.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .retailPrice(product.getRetailPrice())
                    .wholesalePrice(product.getWholesalePrice())
                    .build();
            return DataResponse.builder()
                    .data(productResponse)
                    .message("Succesfully get product")
                    .success(true)
                    .build();
        }
        return DataResponse.builder()
                .message("The product id does not exist!")
                .success(false)
                .build();
    }

}
