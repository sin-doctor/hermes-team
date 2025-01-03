package com.hermes.hermes.mapper;

import com.hermes.hermes.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    String getImage(int productId);
    int checkDuplicatedUserId(String user_id);
}
