package com.yhh.xuanke.service.impl;

import com.google.common.base.Preconditions;
import com.yhh.xuanke.entiy.UserEntity;
import com.yhh.xuanke.repository.UserRepository;
import com.yhh.xuanke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findUserById(Integer sno) {

        Optional<UserEntity> optional = userRepository.findById(sno);
        Preconditions.checkArgument(optional.isPresent());
        return optional.get();
    }
}
