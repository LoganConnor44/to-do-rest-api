package com.loganconnor44.dao;

import com.loganconnor44.entity.Goal;

public interface IGoalDAO {
    void addGoal(Goal goal);

    boolean goalExists(String name, String owner);

    Goal getGoalById(int goalId);

    void updateGoal(Goal goal);

    void deleteGoal(int goalId);

    void markGoalAsComplete(Integer GoalId);
}