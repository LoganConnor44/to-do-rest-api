package com.loganconnor44.dao;

import com.loganconnor44.dto.TaskDto;
import com.loganconnor44.entity.Task;

import java.util.List;
import java.util.UUID;

public interface ITaskDAO {
    void addTask(Task task);

    Task getTaskById(int taskId);

    List<Task> getTasksByOwner(String owner);

    Long getCountOfTaskByOwner(String owner);

    void updateTask(Task task);

    void deleteTask(int taskId);

    void addSubTask(Integer parentTaskId, Task childSubTask);

    void markTaskAsComplete(Integer taskId);
}