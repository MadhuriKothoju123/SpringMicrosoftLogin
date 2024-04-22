package com.example.demo.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserEntityRepository;
import com.example.demo.service.UserService;

import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

	@Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public void save(UserEntity user) {
        userEntityRepository.save(user);
    }
}
