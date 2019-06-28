package com.keysoft.bucktrackerjpa.service;

import com.keysoft.bucktrackerjpa.dao.IGoalDAO;
import com.keysoft.bucktrackerjpa.entity.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService implements IGoalService {

    @Autowired
    private IGoalDAO goalDAO;

    /**
     * Adds the current goal to the database.
     *
     * @param goal The top most object that contains child tasks.
     * @return boolean
     */
    @Override
    public synchronized boolean addGoal(Goal goal) {
        if (goalDAO.goalExists(goal.getName(), goal.getOwner())) {
            return false;
        } else {
            goalDAO.addGoal(goal);
            return true;
        }
    }

    /**
     * Retrieves the goal object by its id.
     *
     * @param goalId The id of the goal that will be marked as completed.
     * @return Goal
     */
    @Override
    public Goal getGoalById(int goalId) {
        return goalDAO.getGoalById(goalId);
    }

    /**
     * Updates an existing goal.
     *
     * @param goal The goal to be updated.
     */
    @Override
    public void updateGoal(Goal goal) {
        goalDAO.updateGoal(goal);
    }

    /**
     * Deletes a goal by its id.
     *
     * @param goalId The id of the goal that will be marked as completed.
     */
    @Override
    public void deleteGoal(int goalId) {
        goalDAO.deleteGoal(goalId);
    }

    /**
     * Updates the goal's status to be completed.
     *
     * @param goalId The id of the goal that will be marked as completed.
     */
    @Override
    public void markGoalAsComplete(Integer goalId) {
        goalDAO.markGoalAsComplete(goalId);
    }
}
