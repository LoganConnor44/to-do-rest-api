package com.keysoft.bucktrackerjpa.dao;

import com.keysoft.bucktrackerjpa.entity.Goal;
import com.keysoft.bucktrackerjpa.helpers.Status;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Instant;

@Transactional
@Repository
public class GoalDAO implements IGoalDAO {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Instructs the entity manager to persist the data of the given goal.
     *
     * @param goal The goal to persist.
     */
    @Override
    public void addGoal(Goal goal) {
        entityManager.persist(goal);
    }

    /**
     * Identify if a goal has already been defined.
     *
     * @param name  The name of the goal to check.
     * @param owner The owner of the goal.
     * @return boolean
     */
    @Override
    public boolean goalExists(String name, String owner) {
        String jpql = "FROM Goal AS GO WHERE GO.name = ? AND GO.owner = ?";
        int count = entityManager
                .createQuery(jpql)
                .setParameter(0, name)
                .setParameter(1, owner)
                .getResultList()
                .size();
        return count > 0;
    }

    /**
     * Retrieves the goal from a given id.
     *
     * @param goalId The id of a goal.
     * @return Goal
     */
    @Override
    public Goal getGoalById(int goalId) {
        return entityManager.find(Goal.class, goalId);
    }

    /**
     * Updates an existing goal by replacing its current value with the passed in goal.
     *
     * @param newGoal The new goal data that will be saved.
     */
    @Override
    public void updateGoal(Goal newGoal) {
        Goal goal = getGoalById(newGoal.getId());
        goal.setName(newGoal.getName());
        goal.setDescription(newGoal.getDescription());
        goal.setOwner(newGoal.getOwner());
        entityManager.flush();
    }

    /**
     * Deletes a goal with the id of the passed in value.
     *
     * @param goalId The id of a goal to delete.
     */
    @Override
    public void deleteGoal(int goalId) {
        entityManager.remove(getGoalById(goalId));
    }

    /**
     * Updates a goal's status with Completed.
     *
     * @param goalId The goal to mark as complete.
     */
    @Override
    public void markGoalAsComplete(Integer goalId) {
        Goal goal = getGoalById(goalId);
        goal.setStatus(Status.COMPLETED);
        goal.setLastModified(Instant.now());
    }
}