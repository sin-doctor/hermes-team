package com.hermes.hermes.service;

import com.hermes.hermes.dto.Purchase;
import com.hermes.hermes.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MyPageServiceImpl implements MyPageService {

    @Autowired
    private UserMapper myPageMapper;

    @Override
    public List<Map<String, Object>> myPageService_getUserPurchase(String user_id) {
        List<Purchase> purchaseList = myPageMapper.myPageService_getUserPurchase(user_id);
        return purchaseList.stream().map(purchase -> {
            Map<String, Object> purchaseMap = new HashMap<>();
            purchaseMap.put("purchase_reg_num", purchase.getPurchase_reg_num());
            purchaseMap.put("purchase_product_reg_num", purchase.getPurchase_product_reg_num());
            purchaseMap.put("purchase_order_id", purchase.getPurchase_order_id());
            purchaseMap.put("purchase_product_size", purchase.getPurchase_product_size());
            purchaseMap.put("purchase_user_id", purchase.getPurchase_user_id());
            purchaseMap.put("purchase_date", purchase.getPurchase_date());
            purchaseMap.put("purchase_status", purchase.getPurchase_status());
            return purchaseMap;
        }).collect(Collectors.toList());
    }

    @Override
    public void myPage_infoCorrection(String user_id, String user_verification_answer, String user_pw) {
        myPageMapper.myPage_infoCorrection(user_id, user_verification_answer, user_pw);
    }


}
