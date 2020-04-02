package com.loganconnor44.dao;

import com.loganconnor44.dto.TaskDto;
import com.loganconnor44.entity.DatabaseUpdate;
import com.loganconnor44.entity.Task;
import com.loganconnor44.helpers.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Data;

import com.loganconnor44.helpers.Convenience;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Transactional
@Repository
@Data
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
        //Task parentTask = getTaskById(parentTaskId);
        //childSubTask.setParentTask(parentTask);
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

    public List<Task> getTasksByOwner(String owner) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        query.select(root).where(cb.equal(root.get("owner"), owner));
        TypedQuery<Task> qry = entityManager.createQuery(query);

        return qry.getResultList();
    }

    public Long getCountOfTaskByOwner(String owner) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Task.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }

    /**
     * Updates an existing task by replacing its current value with the passed in task.
     *
     * @param newTask The new task data that will be saved.
     */
    @Override
    public void updateTask(Task newTask) {
        Task task = getTaskById(newTask.getId());

        if (newTask.getBrowserId() != null) {
            task.setBrowserId(newTask.getBrowserId());
        }
        if (newTask.getName() != null) {
            task.setName(newTask.getName());
        }
        if (newTask.getCreated() != null) {
            task.setCreated(newTask.getCreated());
        }
        if (newTask.getImportance() != null) {
            task.setImportance(newTask.getImportance());
        }
        if (newTask.getStatus() != null) {
            task.setStatus(newTask.getStatus());
        }
        if (newTask.getImportance() != null) {
            task.setImportance(newTask.getImportance());
        }
        if (newTask.getLastModified() != null) {
            task.setLastModified(newTask.getLastModified());
        }

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
        entityManager.remove(getTaskById(taskId));
    }
}