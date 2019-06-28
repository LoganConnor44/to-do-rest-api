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

    @GetMapping("/{goalId}")
    public ResponseEntity<Goal> getGoalById(@PathVariable("goalId") Integer goalId) {
        Goal goal = goalService.getGoalById(goalId);
        return new ResponseEntity<Goal>(goal, HttpStatus.OK);
    }

    @PutMapping("/{goalId}")
    public ResponseEntity<Goal> markGoalAsComplete(@PathVariable("goalId") Integer goalId) {
        goalService.markGoalAsComplete(goalId);
        return new ResponseEntity<Goal>(HttpStatus.OK);
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable("goalId") Integer goalId) {
        goalService.deleteGoal(goalId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
