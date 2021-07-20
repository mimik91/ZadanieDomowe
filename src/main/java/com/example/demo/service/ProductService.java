package com.example.demo.service;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> readAll() {
        return productRepository.findAll();
    }

    public Product create(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public Product read(Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    public Product update(Product updatedProduct, Long id) {
        return productRepository.findById(id).
                map(entity -> {
                    entity.setName(updatedProduct.getName());
                    entity.setPrice(updatedProduct.getPrice());
                    return productRepository.save(entity);
                }).
                orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    public void delete(Long id){
        try {
            productRepository.deleteById(id);
        } catch (Exception exception) {
            throw new EntityNotFoundException(Product.class, id);
        }
    }
}
