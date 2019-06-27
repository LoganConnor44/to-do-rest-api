package com.keysoft.bucktrackerjpa.dao;

import com.keysoft.bucktrackerjpa.entity.Task;
import com.keysoft.bucktrackerjpa.helpers.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class TaskDAO implements ITaskDAO {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Instructs the entity manager to persist the data of the given task.
     *
     * @param task The task to persist.
     */
    @Override
    public void addTask(Task task) {
        entityManager.persist(task);
    }

    /**
     * Adds a subtask to the current task for tasks that require multiple steps.
     *
     * @param parentTaskId The parent task that requires multiple steps.
     * @param childSubTask The child task of a parent task.
     */
    @Override
    public void addSubTask(Integer parentTaskId, Task childSubTask) {
        Task parentTask = getTaskById(parentTaskId);
        childSubTask.setParentTask(parentTask);
        entityManager.flush();
    }

    /**
     * Retrieves the task from a give id.
     *
     * @param taskId the id of a task.
     * @return Task
     */
    @Override
    public Task getTaskById(int taskId) {
        return entityManager.find(Task.class, taskId);
    }

    /**
     * Updates an existing task by replacing its current value with the passed in task.
     *
     * @param newTask The new task data that will be saved.
     */
    @Override
    public void updateTask(Task newTask) {
        Task task = getTaskById(newTask.getId());
        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());
        task.setParentTask(newTask.getParentTask());
        entityManager.flush();
    }

    /**
     * Updates a task's status with Completed.
     *
     * @param taskId The task to mark as complete.
     */
    @Override
    public void markTaskAsComplete(Integer taskId) {
        Task task = getTaskById(taskId);
        task.setStatus(Status.COMPLETED);
    }

    /**
     * Deletes a task with the id of the passed in value.
     *
     * @param taskId The id of a task to delete.
     */
    @Override
    public void deleteTask(int taskId) {
        // you are checking if a parent task exists for any other tasks
        String jpql = "FROM Task AS TA WHERE TA.name = ? AND GO.owner = ?";
        int count = entityManager
                .createQuery(jpql)
                .setParameter(0, name)
                .setParameter(1, owner)
                .getResultList()
                .size();
        return count > 0;
        entityManager.remove(getTaskById(taskId));
        entityManager.find()
    }
}