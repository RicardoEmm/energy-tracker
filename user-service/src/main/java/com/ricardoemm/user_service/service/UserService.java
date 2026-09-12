package com.ricardoemm.user_service.service;

import com.ricardoemm.user_service.dto.UserRequest;
import com.ricardoemm.user_service.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse findById(UUID id);
    List<UserResponse> findAll();
    UserResponse create(UserRequest request);
    void update(UUID id, UserRequest request);
    void deleteUser(UUID id);
}
