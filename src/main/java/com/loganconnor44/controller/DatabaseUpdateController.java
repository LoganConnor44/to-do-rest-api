package com.loganconnor44.controller;

import com.loganconnor44.entity.DatabaseUpdate;
import com.loganconnor44.service.IDatabaseUpdateService;
import com.loganconnor44.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/to-do/updates")
public class DatabaseUpdateController {

    @Autowired
    private IDatabaseUpdateService databaseUpdateService;

    @GetMapping("by-owner/{owner}")
    public ResponseEntity<List<DatabaseUpdate>> getCountOfTasksById(@PathVariable("owner") String owner) {
        List<DatabaseUpdate> updates = databaseUpdateService.getUpdatesByOwner(owner);
        return new ResponseEntity<>(updates, HttpStatus.OK);
    }
}
