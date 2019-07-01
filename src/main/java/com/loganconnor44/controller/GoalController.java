package com.loganconnor44.controller;

import com.loganconnor44.entity.Goal;
import com.loganconnor44.service.IGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/to-do/goal")
public class GoalController {

    @Autowired
    private IGoalService goalService;

    /**
     * Creates and saves a goal via http post.
     * <p>
     * Checks if goal name has already been created by user.
     * If it has, do not recreate goal.
     * After goal is created, return back the generated id in the header.
     * Return an http 201 created status to the user.
     *
     * @param goal    Json sent to the application to define the desired goal.
     * @param builder
     * @return ResponseEntity<void>
     */
    @PostMapping()
    public ResponseEntity<Void> addGoal(@RequestBody Goal goal, UriComponentsBuilder builder) {
        boolean flag = goalService.addGoal(goal);
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(goal.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    /**
     * Retrieves a goal from the database.
     * <p>
     * The client passes the goal id of the desired goal.
     * Return to the user a 200 http status okay.
     *
     * @param goalId The desired goal's id.
     * @return ResponseEntity<Goal>
     */
    @GetMapping("/{goalId}")
    public ResponseEntity<Goal> getGoalById(@PathVariable("goalId") Integer goalId) {
        Goal goal = goalService.getGoalById(goalId);
        return new ResponseEntity<Goal>(goal, HttpStatus.OK);
    }

    /**
     * Updates an existing goal's status as completed.
     * <p>
     * The client passes the existing goal with updated values.
     * The goal is updated via a defined status enum.
     * Return to the user a 200 http status okay.
     *
     * @return ResponseEntity<Goal>
     */
    @PutMapping
    public ResponseEntity<Goal> updateGoal(@RequestBody Goal newValuesForExistingGoal) {
        goalService.updateGoal(newValuesForExistingGoal);
        return new ResponseEntity<Goal>(HttpStatus.OK);
    }

    /**
     * Deletes an existing goal from the database.
     * <p>
     * The client passes the goal id of the desired goal.
     * The goal is removed from the database.
     * Return to the user a 204 http no content status.
     *
     * @param goalId The desired goal's id.
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable("goalId") Integer goalId) {
        goalService.deleteGoal(goalId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
