package org.example.shoppingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dao.OrderDao;
import org.example.shoppingapp.dao.OrderItemDao;
import org.example.shoppingapp.dao.ProductDao;
import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.dto.product.ProductRequest;
import org.example.shoppingapp.dto.product.ProductResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    @Transactional
    public List<Product> getAllProducts(){
        return productDao.getAllProducts();
    }

    @Transactional
    public Product getProductById(Long productId){
        return productDao.getProductById(productId);
    }

    @Transactional
    public void createProduct(Product product){
        productDao.addProduct(product);
    }

    @Transactional
    public void updateProductById(ProductRequest productInfo, Long productId){
        productDao.updateProductById(productInfo, productId);
    }

    @Transactional
    public List<Product> getTopKFrequentProductsByUserId(Long userId, int topK){
        return productDao.getTopKFrequentProductByUserId(userId, topK);
    }

    @Transactional
    public List<Product> getTopKRecentProductsByUserId(Long userId, int topK){
        return productDao.getTopKRecentProductByUserId(userId, topK);
    }



    @Transactional
    public List<Product> getTopKProfitableProducts(int topK){
        return productDao.getTopKProfitableProducts(topK);
    }

    @Transactional
    public List<ProductResponse> getTotalProductSold(){
        return productDao.getTotalProductSold().stream()
                .map(objects -> {
                    Product p = (Product) objects[0];
                    // TODO: fix this force type-casting issue.
                    String soldQuantityStr = String.valueOf(objects[1]);
                    return ProductResponse.builder()
                            .id(p.getId())
                            .description(p.getDescription())
                            .name(p.getName())
                            .retailPrice(p.getRetailPrice())
                            .wholesalePrice(p.getWholesalePrice())
                            .quantity(p.getQuantity())
                            .soldQuantity(Integer.valueOf(soldQuantityStr))
                            .build();
                })
                .collect(Collectors.toList());
    }
}
