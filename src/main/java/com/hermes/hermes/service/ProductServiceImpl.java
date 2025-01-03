package com.hermes.hermes.service;

import com.hermes.hermes.dto.Product;
import com.hermes.hermes.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Product getProduct(int product_reg_num) {
        Product product = productMapper.getProduct(product_reg_num);

        if (product == null) {
            System.out.println("(MY_DEBUG) Product not found for ID: " + product_reg_num);
        }

        return product;
    }

}