package com.loganconnor44.dao;

import com.loganconnor44.entity.Task;

public interface ITaskDAO {
    void addTask(Task task);

    Task getTaskById(int taskId);

    void updateTask(Task task);

    void deleteTask(int taskId);

    void addSubTask(Integer parentTaskId, Task childSubTask);

    void markTaskAsComplete(Integer taskId);
}