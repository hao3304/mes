package com.yizit.mes.service.impl;

import com.yizit.mes.domain.User;
import com.yizit.mes.repository.UserRepository;
import com.yizit.mes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByUserName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public Page<User> listUser(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

}
