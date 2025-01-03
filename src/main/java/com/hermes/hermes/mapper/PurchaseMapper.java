package com.hermes.hermes.mapper;

import com.hermes.hermes.dto.Purchase;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseMapper {
    void insertPurchase(Purchase purchase);
}