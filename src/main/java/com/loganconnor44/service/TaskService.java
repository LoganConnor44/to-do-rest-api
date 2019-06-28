package com.loganconnor44.service;

import com.loganconnor44.dao.ITaskDAO;
import com.loganconnor44.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskDAO taskDAO;

    /**
     * Adds the current task to the database.
     *
     * @param task A child object of a goal.
     * @return boolean
     */
    @Override
    public synchronized boolean addTask(Task task) {
        taskDAO.addTask(task);
        return true;
    }

    /**
     * Retrieves the task object by its id.
     *
     * @param taskId The id of the task that will be marked as completed.
     * @return Task
     */
    @Override
    public Task getTaskById(int taskId) {
        return taskDAO.getTaskById(taskId);
    }

    /**
     * Updates an existing task.
     *
     * @param task The task to be updated.
     */
    @Override
    public void updateTask(Task task) {
        taskDAO.updateTask(task);
    }

    /**
     * Updates the task's status to be completed.
     *
     * @param taskId The id of the task that will be marked as completed.
     */
    @Override
    public void markTaskAsComplete(Integer taskId) {
        taskDAO.markTaskAsComplete(taskId);
    }

    /**
     * Deletes a task by its id.
     *
     * @param taskId The id of the task that will be marked as completed.
     */
    @Override
    public void deleteTask(int taskId) {
        taskDAO.deleteTask(taskId);
    }

    /**
     * Creates and associates a new task with a pre-existing task.
     * @param parentTaskId The pre-existing task.
     * @param childSubTask The newly created sub-task
     */
    @Override
    public void addSubTask(Integer parentTaskId, Task childSubTask) {
        taskDAO.addSubTask(parentTaskId, childSubTask);
    }
}
