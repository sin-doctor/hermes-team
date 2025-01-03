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

}