package com.hermes.hermes.mapper;

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
}
