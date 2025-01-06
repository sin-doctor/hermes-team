package com.hermes.hermes.service;

import com.hermes.hermes.dto.PurchaseProductJoin;
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
    public List<Map<String, Object>> myPageService_getUserPurchase2(String user_id) {
        List<PurchaseProductJoin> purchaseList = myPageMapper.myPageService_getUserPurchase2(user_id);
        return purchaseList.stream().map(purchaseProductJoin -> {
            Map<String, Object> purchaseProductJoinMap = new HashMap<>();
            purchaseProductJoinMap.put("product_image_path", purchaseProductJoin.getProduct_image_path());
            purchaseProductJoinMap.put("product_name", purchaseProductJoin.getProduct_name());
            purchaseProductJoinMap.put("product_price", purchaseProductJoin.getProduct_price());
            purchaseProductJoinMap.put("product_size", purchaseProductJoin.getProduct_size());
            purchaseProductJoinMap.put("purchase_date", purchaseProductJoin.getPurchase_date());
            purchaseProductJoinMap.put("purchase_order_id", purchaseProductJoin.getPurchase_order_id());
            purchaseProductJoinMap.put("purchase_product_size", purchaseProductJoin.getPurchase_product_size());
            purchaseProductJoinMap.put("purchase_status", purchaseProductJoin.getPurchase_status());
            return purchaseProductJoinMap;
        }).collect(Collectors.toList());
    }

    @Override
    public void myPage_infoCorrection(String user_id, String user_verification_answer, String user_pw) {
        myPageMapper.myPage_infoCorrection(user_id, user_verification_answer, user_pw);
    }


}
