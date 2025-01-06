package com.hermes.hermes.service;

import com.hermes.hermes.dto.Purchase;
import com.hermes.hermes.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private  PurchaseMapper purchaseMapper;

    @Override
    public void savePurchase(Purchase purchase) {
        purchaseMapper.insertPurchase(purchase);
    }
}