package com.keysoft.bucktrackerjpa.dao;

import com.keysoft.bucktrackerjpa.entity.Task;

public interface ITaskDAO {
    void addTask(Task task);

    Task getTaskById(int taskId);

    void updateTask(Task task);

    void deleteTask(int taskId);

    void addSubTask(Integer parentTaskId, Task childSubTask);

    void markTaskAsComplete(Integer taskId);
}