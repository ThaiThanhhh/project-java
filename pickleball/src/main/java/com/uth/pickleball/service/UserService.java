package com.uth.pickleball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uth.pickleball.repositories.IUserRepository;
import com.uth.pickleball.model.User;

import java.util.List;

@Service
public class UserService {
    public final IUserRepository userRepository;
    @Autowired
    public UserService(IUserRepository _userRepository) {
        this.userRepository = _userRepository;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User addUser(User user) {
        return userRepository.save(user);
    }
    

    




}
