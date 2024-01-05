package com.project.taskmanagement.service.impl;

import com.project.taskmanagement.converter.TaskConverter;
import com.project.taskmanagement.converter.UserConverter;
import com.project.taskmanagement.dto.TaskDTO;
import com.project.taskmanagement.entity.TaskEntity;
import com.project.taskmanagement.exception.ResourceNotFoundException;
import com.project.taskmanagement.repository.TaskRepository;
import com.project.taskmanagement.repository.UserRepository;
import com.project.taskmanagement.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskConverter taskConverter;

    @Autowired
    private UserConverter userConverter;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        TaskEntity taskEntity = taskConverter.convertToEntity(taskDTO);
        TaskEntity savedTask = taskRepository.save(taskEntity);
        return taskConverter.convertToDTO(savedTask);
    }

    @Override
    public TaskDTO getTask(Long taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        return taskConverter.convertToDTO(taskEntity);
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        TaskEntity existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        // Update task fields
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.getStatus());
        existingTask.setPriority(taskDTO.getPriority());
        existingTask.setDueDate(taskDTO.getDueDate());

        TaskEntity updatedTask = taskRepository.save(existingTask);
        return taskConverter.convertToDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }
}
