package com.loganconnor44.entity;

import com.loganconnor44.helpers.Difficulty;
import com.loganconnor44.helpers.Importance;
import com.loganconnor44.helpers.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.Instant;
import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    /**
     * A unique identifier for a task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id", nullable = false)
    private Integer id;

    @Column(name = "browser_id")
    private Integer browserId;

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
    private Status status = Status.ACTIVE;

    /**
     * A time stamp of the deadline for this task.
     */
    @Column(name = "deadline")
    private Instant deadline;

    /**
     * The difficulty of the current task.
     */
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty = Difficulty.NORMAL;

    /**
     * The importance of the current task.
     */
    @Enumerated(EnumType.STRING)
    private Importance importance = Importance.MEDIUM;

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
        //this.lastModified = Instant.now();
        //this.created = Instant.now();
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
}