package com.hermes.hermes.service;

import com.hermes.hermes.dto.User;

public interface UserService {
    void insertUser(User user);
    String getImage(int productId);
    boolean checkDuplicatedUserId(String user_id);
}