package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Product;
import org.example.shoppingapp.dto.product.ProductRequest;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends AbstractHibernateDao<Product>{
    public ProductDao() {
        this.setClassType(Product.class);
    }

    public List<Product> getAllProducts() {
        return this.getAll();
    }

    public Product getProductById(Long id) {
        return this.findById(id);
    }

    public void addProduct(Product product) {
        this.add(product);
    }

    public void updateProductById(ProductRequest productInfo, Long productId){
        Session currentSession = this.getCurrentSession();
        Product product = this.getProductById(productId);
        if(productInfo.getDescription() != null && !productInfo.getDescription().isEmpty()){
            product.setDescription(productInfo.getDescription());
        }
        if(productInfo.getName() != null && !productInfo.getName().isEmpty()){
            product.setName(productInfo.getName());
        }
        if(productInfo.getRetailPrice() != null){
            product.setRetailPrice(productInfo.getRetailPrice());
        }
        if(productInfo.getWholesalePrice() != null){
            product.setWholesalePrice(productInfo.getWholesalePrice());
        }
        if(productInfo.getQuantity() != null){
            product.setQuantity(productInfo.getQuantity());
        }

        currentSession.update(product);
    }

    public void updateProductQuantityById(Long productId, int change){
        Session currentSession = this.getCurrentSession();
        Product product = this.getProductById(productId);
        product.setQuantity(product.getQuantity() + change);
        currentSession.update(product);
    }
}
