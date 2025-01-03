package com.hermes.hermes.mapper;

import com.hermes.hermes.dto.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ProductMapper {
    // reg_num 으로 상품 조회
    Product getProduct(@Param("product_reg_num") int product_reg_num);

}