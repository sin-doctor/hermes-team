package com.hermes.hermes.service;

import java.util.List;
import java.util.Map;

public interface MyPageService {
    //세션에서 유저 id 받아와서 user_id 가 일치하는 Purchase 리스트 가져오기
    List<Map<String, Object>> myPageService_getUserPurchase(String user_id);

    // 세션에서 User 받아와서 user_id 가 일치하는 User 정보 수정하기
    void myPage_infoCorrection(String user_id, String user_verification_answer, String user_pw);
}
