package com.keysoft.bucktrackerjpa.controller;

import com.keysoft.bucktrackerjpa.entity.Task;
import com.keysoft.bucktrackerjpa.entity.Goal;
import com.keysoft.bucktrackerjpa.entity.Application;
import com.keysoft.bucktrackerjpa.entity.Release;
import com.keysoft.bucktrackerjpa.entity.Ticket;
import com.keysoft.bucktrackerjpa.service.ITaskService;
import com.keysoft.bucktrackerjpa.service.IGoalService;
import com.keysoft.bucktrackerjpa.service.IApplicationService;
import com.keysoft.bucktrackerjpa.service.IReleaseService;
import com.keysoft.bucktrackerjpa.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/tza")
public class TrackzillaController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IGoalService goalService;

    @Autowired
    private IApplicationService applicationService;

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private IReleaseService releaseService;

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

    @PostMapping("/application")
    public ResponseEntity<Void> addApplication(@RequestBody Application application, UriComponentsBuilder builder) {
        boolean flag = applicationService.addApplication(application);
        if (!flag) return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/application/{id}").buildAndExpand(application.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/application/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable("id") Integer id) {
        Application app = applicationService.getApplicationById(id);
        return new ResponseEntity<Application>(app, HttpStatus.OK);

    }

    @PutMapping("/application")
    public ResponseEntity<Application> updateApplication(@RequestBody Application application) {
        applicationService.updateApplication(application);
        return new ResponseEntity<Application>(application, HttpStatus.OK);
    }

    @DeleteMapping("/application/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") Integer id) {
        applicationService.deleteApplication(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") Integer id) {
        Ticket ticket = ticketService.getTicketById(id);
        return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);

    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> list = ticketService.getAllTickets();
        return new ResponseEntity<List<Ticket>>(list, HttpStatus.OK);
    }

    @PostMapping("/ticket")
    public ResponseEntity<Void> addTicket(@RequestBody Ticket ticket, UriComponentsBuilder builder) {
        ticketService.addTicket(ticket);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/ticket/{id}").buildAndExpand(ticket.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/ticket")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) {
        ticketService.updateTicket(ticket);
        return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Integer id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/ticket/{id}")
    public ResponseEntity<Ticket> closeTicket(@PathVariable("id") Integer id) {
        ticketService.closeTicket(id);
        return new ResponseEntity<Ticket>(HttpStatus.OK);
    }

    @PostMapping("/release")
    public ResponseEntity<Void> addRelease(@RequestBody Release release, UriComponentsBuilder builder) {
        releaseService.addRelease(release);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/release").buildAndExpand(release.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/release/{appid}/{releaseid}")
    public ResponseEntity<Void> addApptoRelease(@PathVariable("appid") Integer appid, @PathVariable("releaseid") Integer releaseid, UriComponentsBuilder builder) {
        releaseService.addApplication(appid, releaseid);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}