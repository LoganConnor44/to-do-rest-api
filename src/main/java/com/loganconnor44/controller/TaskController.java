package com.loganconnor44.controller;

import com.loganconnor44.entity.Goal;
import com.loganconnor44.entity.Task;
import com.loganconnor44.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/to-do/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping()
    public ResponseEntity<Void> addTask(@RequestBody Task task, UriComponentsBuilder builder) {
        boolean flag = this.taskService.addTask(task);
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(task.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/{parent-task-id}/create-sub-task")
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
        headers.setLocation(builder.path("/{id}").buildAndExpand(subTask.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    /**
     * Retrieves a task from the database.
     * <p>
     * The client passes the task id of the desired goal.
     * Return to the user a 200 http status okay.
     *
     * @param taskId The desired task's id.
     * @return ResponseEntity<Task>
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable("taskId") Integer taskId) {
        Task task = taskService.getTaskById(taskId);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> markTaskAsComplete(@PathVariable("taskId") Integer taskId) {
        taskService.markTaskAsComplete(taskId);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Integer taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
