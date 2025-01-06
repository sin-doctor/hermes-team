package com.hermes.hermes.mapper;

import com.hermes.hermes.dto.Purchase;
import com.hermes.hermes.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    String getImage(int productId);
    int checkDuplicatedUserId(String user_id);

    List<User> getAllUsers();


     User login(String user_id,String user_pw);
    // 문답으로 유저 찾기
    // User login_search(String answer);

    // 결과 페이지에서 아이디 비밀번호 보여주기
    User login_search_completed(String user_name,String user_verification_answer);

    // 세션에서 유저 id 받아와서 user_id 가 일치하는 Purchase 리스트 가져오기
    List<Purchase> myPageService_getUserPurchase(String user_id);

    // 세션에서 유저 User 객체 받아와서 user_id 가 일치하는 User 정보 수정하기
    void myPage_infoCorrection(String user_id, String user_verification_answer, String user_pw);

}
