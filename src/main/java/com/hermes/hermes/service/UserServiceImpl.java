package com.hermes.hermes.service;
import com.hermes.hermes.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hermes.hermes.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    private boolean isPasswordValid(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }

    @Override
    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    @Override
    public String getImage(int productId) {
        return userMapper.getImage(productId);
    }

    /**
     *
     * @param user_id
     * @return
     */
    @Override
    public boolean checkDuplicatedUserId(String user_id) {
        boolean duplicated = userMapper.checkDuplicatedUserId(user_id) > 0;
        System.out.println("중복 확인 : "+duplicated);
        return duplicated;
    }

    @Override
    public User login(String user_id, String user_pw) {
        return userMapper.login(user_id,user_pw);
    }

    @Override
    public User login_search_completed(String user_name,String user_verification_answer) {
        return userMapper.login_search_completed(user_name , user_verification_answer);
    }
}