package io.gusraccoon.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gusraccoon.entity.Product;
import io.gusraccoon.entity.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    @Transactional
    public List<Product> getProductsMaster() {
        return productRepository.findAll();
    }
}