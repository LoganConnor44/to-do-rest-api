package com.loganconnor44.dto;

import com.loganconnor44.helpers.Difficulty;
import com.loganconnor44.helpers.Importance;
import com.loganconnor44.helpers.Status;
import lombok.Data;

@Data
public class TaskDto {

    public String getId() {
        return id;
    }

    public Integer getRemoteId() {
        return remoteId;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public Status getStatus() {
        return status;
    }

    public Long getCreated() {
        return created;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public Long getDeadline() {
        return deadline;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Importance getImportance() {
        return importance;
    }

    private String id;
    private Integer remoteId;
    private String name;
    private String owner;
    private Status status;
    private Long created;
    private Long lastModified;
    private Long deadline;
    private Difficulty difficulty;
    private Importance importance;
}