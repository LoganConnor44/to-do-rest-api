package com.loganconnor44.entity;

import com.loganconnor44.helpers.Difficulty;
import com.loganconnor44.helpers.Importance;
import com.loganconnor44.helpers.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrowserId() {
        return browserId;
    }

    public void setBrowserId(String browserId) {
        this.browserId = browserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public Instant getCreated() {
        return created;
    }

    /**
     * A unique identifier for a task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id", nullable = false)
    private Integer id;

    @Column(name = "browser_id")
    private String browserId;

    /**
     * The name of the task.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The name of the task.
     */
    @Column(name = "owner", nullable = false)
    private String owner;



    /**
     * The status of the current task.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * A time stamp of the deadline for this task.
     */
    @Column(name = "deadline")
    @Setter(AccessLevel.NONE)
    private Instant deadline;

    /**
     * The difficulty of the current task.
     */
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    /**
     * The importance of the current task.
     */
    @Enumerated(EnumType.STRING)
    private Importance importance;

    /**
     * A time stamp of the last time this object was modified.
     */
    @Column(name = "last_modified", nullable = false)
    @Setter(AccessLevel.NONE)
    private Instant lastModified;

    /**
     * The time stamp of the creation of this object.
     */
    @Column(name = "created", nullable = false)
    @Setter(AccessLevel.NONE)
    private Instant created;

    public Task() {
    }

    public Task(
            int id,
            String name,
            String description,
            Task parentTask,
            Status status
    ) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.lastModified = Instant.now();
        this.created = Instant.now();
    }

    public void setLastModified(Object date) {
        if (date instanceof Long) {
            this.lastModified = Instant.ofEpochSecond( (Long) date );
        }
        if (date instanceof Instant) {
            this.lastModified = (Instant) date;
        }
    }

    public void setCreated(Object date) {
        if (date instanceof Long) {
            this.created = Instant.ofEpochSecond( (Long) date );
        }
        if (date instanceof Instant) {
            this.created = (Instant) date;
        }
    }

    public void setDeadline(Object date) {
        if (date == null) {
            this.deadline = null;
        }
        if (date instanceof Long) {
            this.deadline = Instant.ofEpochSecond( (Long) date );
        }
        if (date instanceof Instant) {
            this.deadline = (Instant) date;
        }
    }
}