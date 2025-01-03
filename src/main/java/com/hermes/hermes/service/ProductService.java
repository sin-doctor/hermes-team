package com.hermes.hermes.service;

import com.hermes.hermes.dto.Product;

public interface ProductService {
    // 단일 상품 조회
    Product getProduct(int productId);

}