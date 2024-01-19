package org.example.shoppingapp.controller;

import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.domain.User;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.product.ProductResponse;
import org.example.shoppingapp.service.ProductService;
import org.example.shoppingapp.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WatchListControllerTest {
    @InjectMocks
    private WatchListController watchListController;
    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;
    @Mock
    private SecurityContext securityContext;

    Set<Product> mockUserWatchlist;
    Product p1;
    Product p2;
    Product p3;


    @BeforeEach
    void setUp() {
        this.mockUserWatchlist = new HashSet<>();
        HashSet<User> mockUserSet = new HashSet<>();
        p1 = Product.builder()
                .id(1L)
                .description("Mock one")
                .name("Mock1")
                .quantity(1)
                .retailPrice(1.99)
                .wholesalePrice(0.99)
                .users(mockUserSet)
                .build();

        p2 = Product.builder()
                .id(2L)
                .description("Mock two")
                .name("Mock2")
                .quantity(2)
                .retailPrice(2.99)
                .wholesalePrice(0.99)
                .users(mockUserSet)
                .build();
        p3 = Product.builder()
                .id(3L)
                .description("Mock Three")
                .name("Mock3")
                .quantity(1)
                .retailPrice(3.99)
                .wholesalePrice(0.99)
                .users(mockUserSet)
                .build();
        mockUserWatchlist.add(p1);
        mockUserWatchlist.add(p2);
    }

    @Nested
    class RequiredAuthTests{
        @BeforeEach
        void setUpAuth(){
            // Mock an authenticated user
            Authentication authentication = new UsernamePasswordAuthenticationToken("1", "password");
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

            // Set the mocked SecurityContext to the SecurityContextHolder
            SecurityContextHolder.setContext(securityContext);
        }

        @Test
        @DisplayName("Get Watchlist from user")
        void getAllWatchList() {

            Mockito.when(userService.getWatchlistProductsByUserId(1L)).thenReturn(mockUserWatchlist);
            List<ProductResponse> mockResponse = mockUserWatchlist.stream().map(p -> ProductResponse.builder()
                            .id(p.getId())
                            .name(p.getName())
                            .description(p.getDescription())
                            .retailPrice(p.getRetailPrice())
                            .build()
                    )
                    .collect(Collectors.toList());
            DataResponse mockData = DataResponse.builder()
                    .data(mockResponse)
                    .success(true)
                    .message("Get user's watchlist")
                    .build();
            assertEquals(mockData, watchListController.getAllWatchList());
        }

        @Test
        @DisplayName("Successfully Add to Watchlist")
        void addToWatchList() {
            Mockito.when(userService.getWatchlistProductsByUserId(1L)).thenReturn(mockUserWatchlist);
            Mockito.when(productService.getProductById(3L)).thenReturn(p3);
            DataResponse mockResponse = DataResponse.builder()
                    .success(true)
                    .message("Product added to watchlist!")
                    .build();
            DataResponse testResponse = watchListController.addToWatchList(3L);
            Mockito.verify(userService, Mockito.times(1)).getWatchlistProductsByUserId(1L);
            Mockito.verify(userService, Mockito.times(1)).addProductToWatchlistByProductIdAndUserId(3L, 1L);
            assertEquals(mockResponse, testResponse);
        }

        @Test
        @DisplayName("Add Already in Watchlist Product to Watchlist")
        void addToWatchListWithExistingProduct() {
            Mockito.when(userService.getWatchlistProductsByUserId(1L)).thenReturn(mockUserWatchlist);
            Mockito.when(productService.getProductById(2L)).thenReturn(p2);
            DataResponse mockResponse = DataResponse.builder()
                    .success(false)
                    .message("Product you want to add is already in list!")
                    .build();;
            DataResponse testResponse = watchListController.addToWatchList(2L);
            Mockito.verify(userService, Mockito.times(1))
                    .getWatchlistProductsByUserId(1L);
            assertEquals(mockResponse, testResponse);
        }

        @Test
        @DisplayName("Successfully remove from watchlist")
        void removeFromWatchlist() {
            Mockito.when(userService.getWatchlistProductsByUserId(1L)).thenReturn(mockUserWatchlist);
            DataResponse mockResponse = DataResponse.builder()
                    .success(true)
                    .message("Product successfully removed from your watchlist")
                    .build();
            DataResponse testResponse = watchListController.removeFromWatchlist(2L);
            Mockito.verify(userService, Mockito.times(1)).getWatchlistProductsByUserId(1L);
            Mockito.verify(userService, Mockito.times(1)).removeProductFromWatchlistByProductIdAndUserId(2L, 1L);
            assertEquals(mockResponse, testResponse);
        }

        @Test
        @DisplayName("Remove product that are not in watchlist")
        void removeNotInWatchlistProductFromWatchlist() {
            Mockito.when(userService.getWatchlistProductsByUserId(1L)).thenReturn(mockUserWatchlist);
            DataResponse mockResponse = DataResponse.builder()
                    .success(false)
                    .message("product is not in your watchlist!")
                    .build();
            DataResponse testResponse = watchListController.removeFromWatchlist(4L);
            Mockito.verify(userService, Mockito.times(1)).getWatchlistProductsByUserId(1L);
            assertEquals(mockResponse, testResponse);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Add Null Product to Watchlist")
    void addToWatchListWithNullProduct() {
        Mockito.when(productService.getProductById(4L)).thenReturn(null);
        DataResponse mockResponse = DataResponse.builder()
                .success(false)
                .message("Product you want to add doesn't exist!")
                .build();
        DataResponse testResponse = watchListController.addToWatchList(4L);
        assertEquals(mockResponse, testResponse);
    }
}