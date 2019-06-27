package com.keysoft.bucktrackerjpa.service;

import com.keysoft.bucktrackerjpa.entity.Goal;

public interface IGoalService {
    boolean addGoal(Goal goal);

    Goal getGoalById(int goalId);

    void updateGoal(Goal goal);

    void deleteGoal(int goalId);

    void markGoalAsComplete(Integer goalId);
}
