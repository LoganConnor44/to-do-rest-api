package com.loganconnor44.service;

import com.loganconnor44.dto.TaskDto;
import com.loganconnor44.entity.Task;

import java.util.List;

public interface ITaskService {
    boolean addTask(Task task);

    Task getTaskById(int taskId);

    List<Task> getTasksByOwner(String owner);

    boolean updateTask(Task task);

    void deleteTask(int taskId);

    void addSubTask(Integer parentTaskId, Task childSubTask);

    void markTaskAsComplete(Integer taskId);

    Long getCountOfTasksByOwner(String owner);
}

