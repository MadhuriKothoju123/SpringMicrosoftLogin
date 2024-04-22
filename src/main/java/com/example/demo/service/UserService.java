package com.example.demo.service;



import java.util.Optional;

import com.example.demo.entity.UserEntity;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity user);
}
