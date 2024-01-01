package org.example.shoppingapp.dao;

import org.example.shoppingapp.domain.Product;
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

    public Product getProductById(long id) {
        return this.findById(id);
    }

    public void addProduct(Product product) {
        this.add(product);
    }
}
