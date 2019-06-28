package com.loganconnor44.controller;

import com.loganconnor44.entity.Task;
import com.loganconnor44.entity.Goal;
import com.loganconnor44.service.ITaskService;
import com.loganconnor44.service.IGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/to-do")
public class ToDoController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IGoalService goalService;

    @PostMapping("/task")
    public ResponseEntity<Void> addTask(@RequestBody Task task, UriComponentsBuilder builder) {
        boolean flag = taskService.addTask(task);
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/task/{id}").buildAndExpand(task.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/task/{parent-task-id}/create-sub-task")
    public ResponseEntity<Void> addSubTaskToTask(
            @PathVariable("parent-task-id") Integer parentTaskId,
            @RequestBody Task subTask,
            UriComponentsBuilder builder
    ) {
        boolean flag = taskService.addTask(subTask);
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        taskService.addSubTask(parentTaskId, subTask);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/task/{id}").buildAndExpand(subTask.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<Task> markTaskAsComplete(@PathVariable("taskId") Integer taskId) {
        taskService.markTaskAsComplete(taskId);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Integer taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/goal")
    public ResponseEntity<Void> addGoal(@RequestBody Goal goal, UriComponentsBuilder builder) {
        boolean flag = goalService.addGoal(goal);
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/goal/{id}").buildAndExpand(goal.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/goal/{goalId}")
    public ResponseEntity<Goal> markGoalAsComplete(@PathVariable("goalId") Integer goalId) {
        goalService.markGoalAsComplete(goalId);
        return new ResponseEntity<Goal>(HttpStatus.OK);
    }
}