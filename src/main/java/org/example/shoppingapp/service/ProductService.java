package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.ProductDao;
import org.example.shoppingapp.domain.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    @Transactional
    public List<Product> getAllProducts(){
        return productDao.getAllProducts();
    }

    @Transactional
    public Product getProductById(long productId){
        return productDao.getProductById(productId);
    }
}
