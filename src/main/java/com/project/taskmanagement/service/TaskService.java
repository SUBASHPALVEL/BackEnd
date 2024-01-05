package com.project.taskmanagement.service;

import com.project.taskmanagement.dto.TaskDTO;


public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO getTask(Long taskId);

    TaskDTO updateTask(Long taskId, TaskDTO taskDTO);

    void deleteTask(Long taskId);
}
