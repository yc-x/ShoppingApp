package org.example.shoppingapp.service;

import org.example.shoppingapp.dao.ProductDao;
import org.example.shoppingapp.dao.UserDao;
import org.example.shoppingapp.domain.Order;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserDao userDao;
    @Mock
    ProductDao productDao;
    User mockUser;
    Set<Product> mockUserWatchlist;
    Product p1;
    Product p2;
    Product p3;

    @BeforeEach
    void setUp() {
        this.mockUserWatchlist = new HashSet<>();

        HashSet<User> mockUserSet = new HashSet<>();
        mockUserSet.add(mockUser);
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

        this.mockUser = User.builder()
                .id(1L)
                .email("abc@gmail.com")
                .password("!#123")
                .role(null)
                .username("test User")
                .watchlistProducts(this.mockUserWatchlist)
                .build();
        // mockUserWatchlist.add(p3);
    }

    @AfterEach
    void tearDown() {
        // this.mockUser = null;
        this.mockUserWatchlist.clear();
    }

    @Test
    @DisplayName("Add User Test")
    void createUser() {
//        Mockito.when(userDao.add(mockUser)).then()
        userService.createUser(mockUser);
        Mockito.verify(userDao, Mockito.times(1)).addUser(mockUser);
    }

    @Test
    @DisplayName("Get User by Id Test")
    void getUserById() {
        Mockito.when(userDao.getUserById(1L)).thenReturn(mockUser);
        assertEquals(mockUser, userService.getUserById(1L));
    }

    @Test
    @DisplayName("Get User Watchlist by Id Test")
    void getWatchlistProductsByUserId() {
        Mockito.when(userDao.getWatchlistProductsByUserId(1L)).thenReturn(mockUserWatchlist);
        assertEquals(mockUserWatchlist, userService.getWatchlistProductsByUserId(1L));
    }

    @Test
    @DisplayName("Successfully add to watchlist")
    void addProductToWatchlistByProductIdAndUserId() {
        Mockito.when(userDao.getUserById(1L)).thenReturn(this.mockUser);
        Mockito.when(productDao.getProductById(3L)).thenReturn(p3);
        userService.addProductToWatchlistByProductIdAndUserId(3L, 1L);
        Mockito.verify(userDao, Mockito.times(1)).getUserById(1L);
        Mockito.verify(productDao, Mockito.times(1)).getProductById(3L);
    }

    @Test
    @DisplayName("Successfully remove from watchlist")
    void removeProductFromWatchlistByProductIdAndUserId() {
        Mockito.when(userDao.getUserById(1L)).thenReturn(this.mockUser);
        Mockito.when(productDao.getProductById(2L)).thenReturn(p2);
        userService.removeProductFromWatchlistByProductIdAndUserId(2L, 1L);
        Mockito.verify(userDao, Mockito.times(1)).getUserById(1L);
        Mockito.verify(productDao, Mockito.times(1)).getProductById(2L);
    }
}