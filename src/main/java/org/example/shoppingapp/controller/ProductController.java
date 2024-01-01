package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.product.ProductRequest;
import org.example.shoppingapp.dto.product.ProductResponse;
import org.example.shoppingapp.service.ProductService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    @ResponseBody
    public DataResponse createProduct(@Valid @RequestBody ProductRequest productRequest,
                                      BindingResult bindingResult){
        // TODO: Add admin user role check before this operation.
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            errors.forEach(error -> errorMessage.append(error.getObjectName())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("\n"));
            return DataResponse.builder()
                    .data(errorMessage.toString())
                    .success(false)
                    .message("Not in Correct format, please check your request.")
                    .build();
        }
        Product newProduct = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .wholesalePrice(productRequest.getWholesalePrice())
                .retailPrice(productRequest.getRetailPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productService.createProduct(newProduct);
        return DataResponse.builder()
                .success(true)
                .message("Product created")
                .build();
    }


    @GetMapping("/all")
    @ResponseBody
    public DataResponse getAllProducts(){
        //TODO: return different products information decided by user's role.
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
    @ResponseBody
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

//    @PatchMapping("/{productId}")
//    @ResponseBody
//    public DataResponse modifyProductById(@Valid @RequestBody ProductRequest productRequest){
//        // TODO: apply change product information.
//    }

}
