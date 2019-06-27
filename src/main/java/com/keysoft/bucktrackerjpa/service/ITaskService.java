package com.keysoft.bucktrackerjpa.service;

import com.keysoft.bucktrackerjpa.entity.Task;

public interface ITaskService {
    boolean addTask(Task task);

    Task getTaskById(int taskId);

    void updateTask(Task task);

    void deleteTask(int taskId);

    void addSubTask(Integer parentTaskId, Task childSubTask);

    void markTaskAsComplete(Integer taskId);
}
