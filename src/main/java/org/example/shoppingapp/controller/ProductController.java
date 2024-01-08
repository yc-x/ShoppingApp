package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.product.ProductRequest;
import org.example.shoppingapp.dto.product.ProductResponse;
import org.example.shoppingapp.service.OrderService;
import org.example.shoppingapp.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final OrderService orderService;

    private Set<String> getAuthUserAuthorities() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    @PostMapping("")
    @ResponseBody
    @PreAuthorize("hasAuthority('Admin')")
    public DataResponse createProduct(@Valid @RequestBody ProductRequest productRequest,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            return buildErrorContent(errors);
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
    public DataResponse getAllProducts() {
        if (getAuthUserAuthorities().contains("Admin")) {
            return getAllProductsForAdmin();
        }
        return getAllProductsForBuyer();
    }

    @GetMapping("/{productId}")
    @ResponseBody
    public DataResponse getProductById(@PathVariable long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            if (product.getQuantity() == 0 &&
                    !getAuthUserAuthorities().contains("Admin")) {
                return DataResponse.builder()
                        .message("The product you are searching is out of stock!")
                        .success(true)
                        .build();
            }
            ProductResponse productResponse = ProductResponse.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .retailPrice(product.getRetailPrice())
                    .wholesalePrice(product.getWholesalePrice())
                    .build();
            if (getAuthUserAuthorities().contains("Admin")) {
                productResponse.setQuantity(product.getQuantity());
            }
            return DataResponse.builder()
                    .data(productResponse)
                    .message("Successfully get product")
                    .success(true)
                    .build();
        }
        return DataResponse.builder()
                .message("The product id does not exist!")
                .success(false)
                .build();
    }

    @PatchMapping("/{productId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('Admin')")
    public DataResponse modifyProductById(@Valid @RequestBody ProductRequest productRequest,
                                          @PathVariable Long productId,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            return buildErrorContent(errors);
        }
        Product product = productService.getProductById(productId);
        if (product == null) {
            return DataResponse.builder()
                    .success(false)
                    .message("The product id doesn't exist!")
                    .build();
        }
        productService.updateProductById(productRequest, product.getId());
        return DataResponse.builder()
                .success(true)
                .message("Product updated!")
                .data(productService.getProductById(productId))
                .build();
    }

    @GetMapping("/popular/{count}")
    @PreAuthorize("hasAuthority('Admin')")
    public DataResponse getMostPopularProducts(@PathVariable Integer count){
        List<Product> result = productService.getTopKFrequentProductsByUserId(null, count);
        return DataResponse.builder()
                .data(result)
                .success(true)
                .message("Got " + count + " most popular products")
                .build();
    }

    @GetMapping("/profit/{count}")
    @PreAuthorize("hasAuthority('Admin')")
    public DataResponse getMostProfitableProducts(@PathVariable Integer count){
        List<Product> result = productService.getTopKProfitableProducts(count);
        return DataResponse.builder()
                .data(result)
                .success(true)
                .message("Got " + count + " most popular products")
                .build();
    }

    @GetMapping("/frequent/{count}")
    @PreAuthorize("hasAuthority('Buyer')")
    public DataResponse getMostFrequentProducts(@PathVariable Integer count) {
        String userIdStr = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);
        List<ProductResponse> result = productService.getTopKFrequentProductsByUserId(userId, count)
                .stream().map(
                    p -> ProductResponse.builder()
                            .name(p.getName())
                            .description(p.getDescription())
                            .retailPrice(p.getRetailPrice())
                            .wholesalePrice(p.getWholesalePrice())
                            .build()
                )
                .collect(Collectors.toList());
        return DataResponse.builder()
                .data(result)
                .success(true)
                .message("Got " + count + " most frequent products")
                .build();
    }


    @GetMapping("/recent/{count}")
    @PreAuthorize("hasAuthority('Buyer')")
    public DataResponse getMostRecentProducts(@PathVariable Integer count){
        String userIdStr = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);
        List<ProductResponse> result = productService.getTopKRecentProductsByUserId(userId, count)
                .stream().map(p -> ProductResponse.builder()
                        .name(p.getName())
                        .description(p.getDescription())
                        .retailPrice(p.getRetailPrice())
                        .wholesalePrice(p.getWholesalePrice())
                        .build()
                )
                .collect(Collectors.toList());
        return DataResponse.builder()
                .data(result)
                .success(true)
                .message("Got " + count + " most recent products")
                .build();
    }


    private DataResponse getAllProductsForBuyer(){
        List<ProductResponse> responseList = productService.getAllProducts().stream()
                .filter(p -> p.getQuantity() > 0)
                .map(p -> ProductResponse.builder()
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

    private DataResponse getAllProductsForAdmin(){
        List<ProductResponse> responseList = productService.getAllProducts().stream()
                .map(p -> ProductResponse.builder()
                        .name(p.getName())
                        .description(p.getDescription())
                        .quantity(p.getQuantity())
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

    private DataResponse buildErrorContent(List<FieldError> errors){
        StringBuilder errorMessage = new StringBuilder();
        errors.forEach(error -> errorMessage.append(error.getObjectName())
                .append(": ")
                .append(error.getDefaultMessage())
                .append("\n"));
        return DataResponse.getGeneralInvalidResponse(errorMessage.toString(),
                "Product request not in correct format, please check your request.");
    }
}
