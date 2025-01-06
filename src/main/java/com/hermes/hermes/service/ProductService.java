package com.hermes.hermes.service;

import com.hermes.hermes.dto.Product;

import java.util.List;

public interface ProductService {
    // 단일 상품 조회
    Product getProduct(int productId);
    // 모든 상품 목록 조회
    List<Product> getAllIndexProducts();

    // category 페이지 카테고리별 상품 조회
    List<Product> getCategoryList(String product_category);
}