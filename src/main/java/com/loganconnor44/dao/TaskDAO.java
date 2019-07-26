package com.loganconnor44.dao;

import com.loganconnor44.entity.Task;
import com.loganconnor44.helpers.Status;
import org.springframework.stereotype.Repository;
import com.loganconnor44.helpers.Convenience;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Instant;
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
        task.setLastModified(Instant.now());
    }

    /**
     * Deletes a task with the id of the passed in value.
     * If there are any children, do not orphan them.
     *
     * @param taskId The id of a task to delete.
     */
    @Override
    public void deleteTask(int taskId) {
        String jpql = "SELECT SUB.PARENT_TASK_FK " +
                "FROM TASKS TA " +
                "LEFT OUTER JOIN TASKS_SUBTASKS SUB " +
                "ON TA.TASK_ID = SUB.CHILD_SUBTASK_FK " +
                "WHERE TA.TASK_ID = :taskId";

        @SuppressWarnings("unchecked")
        List<Integer> parentIds = (List<Integer>) entityManager.createNativeQuery(jpql)
                .setParameter("taskId", taskId)
                .getResultList();
        if (!Convenience.isListOfNulls(parentIds)) {
            for (Integer id : parentIds) {
                entityManager.remove(getTaskById(id));
            }
        }
        entityManager.remove(getTaskById(taskId));
    }
}