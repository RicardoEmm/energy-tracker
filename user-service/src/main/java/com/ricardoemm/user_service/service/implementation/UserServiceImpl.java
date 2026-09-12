package com.ricardoemm.user_service.service.implementation;

import com.ricardoemm.user_service.model.User;
import com.ricardoemm.user_service.dto.UserRequest;
import com.ricardoemm.user_service.dto.UserResponse;
import com.ricardoemm.user_service.repository.UserRepository;
import com.ricardoemm.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserResponse findById(UUID id) {
        return toResponse( userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("user not found with ID: " + id)
        ));
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public UserResponse create(UserRequest request) {
        User createdUser = toDomain(request);
        return toResponse(userRepository.save(createdUser));
    }

    @Override
    public void update(UUID id, UserRequest request) {
        User userToUpdate = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("user not found with ID: " + id)
        );

        userToUpdate = userToUpdate.toBuilder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .address(request.address())
                .alerting(request.alerting())
                .energyAlertingThreshold(request.energyAlertingThreshold())
                .build();

        userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(UUID id) {
        User userToDelete = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("user not found with ID: " + id)
        );
        userRepository.deleteById(userToDelete.getId());
    }

    public User toDomain(UserRequest request) {
        return User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .alerting(request.alerting())
                .energyAlertingThreshold(request.energyAlertingThreshold())
                .build();
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getName())
                .email(user.getEmail())
                .alerting(user.isAlerting())
                .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                .build();
    }
}
