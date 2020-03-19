package com.loganconnor44.controller;

import com.loganconnor44.dto.TaskDto;
import com.loganconnor44.entity.Goal;
import com.loganconnor44.entity.Task;
import com.loganconnor44.service.ITaskService;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.modelmapper.ModelMapper;

import javax.print.attribute.standard.Destination;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/to-do/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping()
    public ResponseEntity<Void> addTask(@RequestBody TaskDto taskDto, UriComponentsBuilder builder) {
        Task task = this.convertToEntity(taskDto);
        boolean flag = this.taskService.addTask(task);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(task.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Void> updateTask(@RequestBody TaskDto taskDto) {
        Task task = this.convertToEntity(taskDto);
        boolean flag = this.taskService.updateTask(task);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{parent-task-id}/create-sub-task")
    public ResponseEntity<Void> addSubTaskToTask(
            @PathVariable("parent-task-id") Integer parentTaskId,
            @RequestBody Task subTask,
            UriComponentsBuilder builder
    ) {
        boolean flag = taskService.addTask(subTask);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        taskService.addSubTask(parentTaskId, subTask);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(subTask.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTasksByOwner(@RequestParam(value="owner") String owner) {
        List<Task> task = taskService.getTasksByOwner(owner);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("total-count-for/{owner}")
    public ResponseEntity<Long> getCountOfTasksById(@PathVariable("owner") String owner) {
        Long taskCount = taskService.getCountOfTasksByOwner(owner);
        return new ResponseEntity<>(taskCount, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Integer taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Task convertToEntity(TaskDto taskDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<TaskDto, Task>() {
            @Override
            protected void configure() {
                if (source.getRemoteId() != null) {
                    map(source.getRemoteId()).setId(null);
                }
                map(source.getId()).setBrowserId(null);
                map(source.getCreated()).setCreated(null);
                map(source.getLastModified()).setLastModified(null);
                map(source.getDifficulty()).setDifficulty(null);
                map(source.getImportance()).setImportance(null);
                map(source.getStatus()).setStatus(null);
                map(source.getOwner()).setOwner(null);
                map(source.getName()).setName(null);
                map().setDeadline(null);
            }
        });
        return modelMapper.map(taskDto, Task.class);
    }
}
