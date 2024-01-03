package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.service.ProductService;
import org.example.shoppingapp.service.UserService;
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

    @GetMapping("/products/all")
    public DataResponse getAllWatchList(){
        // TODO: fetch from real user id;
        Long userId = 2L;
        Set<Product> productWatchlist = userService.getWatchlistProductsByUserId(userId);
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
        //TODO: change user id to current user.
        Long userId = 2L;

        userService.addProductToWatchlistByProductIdAndUserId(productId, userId);
        return DataResponse.builder()
                .success(true)
                .message("Product added to watchlist!")
                .build();
    }

    @DeleteMapping("/product/{productId}")
    public DataResponse removeFromWatchlist(@PathVariable Long productId){
        // TODO: replace dummy user to current user
        Long userId = 2L;
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
