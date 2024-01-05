package com.project.taskmanagement.service.impl;

import com.project.taskmanagement.converter.TaskConverter;
import com.project.taskmanagement.converter.UserConverter;
import com.project.taskmanagement.dto.TaskDTO;
import com.project.taskmanagement.dto.UserDTO;
import com.project.taskmanagement.entity.TaskEntity;
import com.project.taskmanagement.entity.UserEntity;
import com.project.taskmanagement.exception.ResourceNotFoundException;
import com.project.taskmanagement.repository.TaskRepository;
import com.project.taskmanagement.repository.UserRepository;
import com.project.taskmanagement.service.UserService;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private TaskConverter taskConverter;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = userConverter.convertToEntity(userDTO);
        UserEntity savedUser = userRepository.save(userEntity);
        return userConverter.convertToDTO(savedUser);
    }

    @Override
    public UserDTO getUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userConverter.convertToDTO(userEntity);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Update user fields
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setUsermail(userDTO.getUsermail());
        existingUser.setPassword(userDTO.getPassword());

        UserEntity updatedUser = userRepository.save(existingUser);
        return userConverter.convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<TaskDTO> getAllTasksForUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return userEntity.getAssignedTasks().stream()
                .map(taskConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    private TaskDTO convertToDTO(TaskEntity taskentity1, TaskDTO taskdto1) {
        return null;
    }
}
