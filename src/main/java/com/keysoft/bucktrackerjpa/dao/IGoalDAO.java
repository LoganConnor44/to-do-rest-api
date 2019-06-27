package com.keysoft.bucktrackerjpa.dao;

import com.keysoft.bucktrackerjpa.entity.Goal;
import java.util.List;

public interface IGoalDAO {
    void addGoal(Goal goal);

    boolean goalExists(String name, String owner);

    Goal getGoalById(int goalId);

    void updateGoal(Goal goal);

    void deleteGoal(int goalId);

    void markGoalAsComplete(Integer GoalId);
}