package com.loganconnor44.service;

import com.loganconnor44.entity.Task;

public interface ITaskService {
    boolean addTask(Task task);

    Task getTaskById(int taskId);

    boolean updateTask(Task task);

    void deleteTask(int taskId);

    void addSubTask(Integer parentTaskId, Task childSubTask);

    void markTaskAsComplete(Integer taskId);
}
