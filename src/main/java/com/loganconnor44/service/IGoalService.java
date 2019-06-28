package com.loganconnor44.service;

import com.loganconnor44.entity.Goal;

public interface IGoalService {
    boolean addGoal(Goal goal);

    Goal getGoalById(int goalId);

    void updateGoal(Goal goal);

    void deleteGoal(int goalId);

    void markGoalAsComplete(Integer goalId);
}
