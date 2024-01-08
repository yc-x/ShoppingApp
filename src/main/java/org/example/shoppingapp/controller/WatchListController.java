package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.product.ProductResponse;
import org.example.shoppingapp.service.ProductService;
import org.example.shoppingapp.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/watchlist")
@RequiredArgsConstructor
public class WatchListController {
    private final UserService userService;
    private final ProductService productService;

    private Set<String> getAuthUserAuthorities(){
        return  SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
    @GetMapping("/products/all")
    public DataResponse getAllWatchList(){
        // TODO: fetch from real user id;
        Long userId = Long.valueOf(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        List<ProductResponse> productWatchlist = userService.getWatchlistProductsByUserId(userId)
                .stream().map(p -> ProductResponse.builder()
                        .name(p.getName())
                        .description(p.getDescription())
                        .retailPrice(p.getRetailPrice())
                        .wholesalePrice(p.getWholesalePrice())
                        .build()
                )
                .collect(Collectors.toList());
        return DataResponse.builder()
                .data(productWatchlist)
                .success(true)
                .message("Get user's watchlist")
                .build();
    }

    @PostMapping("/product/{productId}")
    public DataResponse addToWatchList(@PathVariable Long productId){
        Product p = productService.getProductById(productId);
        if(p == null){
            return DataResponse.builder()
                    .success(false)
                    .message("Product you want to add doesn't exist!")
                    .build();
        }
        Long userId = Long.valueOf(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        userService.addProductToWatchlistByProductIdAndUserId(productId, userId);
        return DataResponse.builder()
                .success(true)
                .message("Product added to watchlist!")
                .build();
    }

    @DeleteMapping("/product/{productId}")
    public DataResponse removeFromWatchlist(@PathVariable Long productId){
        Long userId = Long.valueOf(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        Set<Long> watchlistProductIds = userService.getWatchlistProductsByUserId(userId)
                .stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
        if(!watchlistProductIds.contains(productId)){
            return DataResponse.builder()
                    .success(false)
                    .message("product is not in your watchlist!")
                    .build();
        }
        userService.removeProductFromWatchlistByProductIdAndUserId(productId, userId);
        return DataResponse.builder()
                .success(true)
                .message("Product successfully removed from your watchlist")
                .build();
    }



}
