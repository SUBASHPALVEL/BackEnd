package com.project.taskmanagement.service;

import com.project.taskmanagement.dto.TaskDTO;
import com.project.taskmanagement.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUser(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);

    List<TaskDTO> getAllTasksForUser(Long userId);

    List<UserDTO> getAllUsers();
}
