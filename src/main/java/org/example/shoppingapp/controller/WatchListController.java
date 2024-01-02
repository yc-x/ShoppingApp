package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/watchlist")
@RequiredArgsConstructor
public class WatchListController {
    private final UserService userService;

    @GetMapping("/products/all")
    public DataResponse getAllWatchList(){
        // TODO: fetch from real user id;
        Long userId = 2L;
        List<Product> productWatchlist = userService.getWatchlistProductsByUserId(userId);
        return DataResponse.builder()
                .data(productWatchlist)
                .success(true)
                .message("Get user's watchlist")
                .build();
    }


}
