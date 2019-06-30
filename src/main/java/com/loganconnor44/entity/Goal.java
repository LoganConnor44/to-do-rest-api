package com.loganconnor44.entity;

import com.loganconnor44.helpers.Status;
import java.time.Instant;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
@Table(name = "goals")
public class Goal {
    /**
     * A unique identifier for a goal.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "goal_id", nullable = false)
    private Integer id;

    /**
     * The name of the goal.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The description of the goal.
     */
    @Column(length = 2000)
    private String description;

    /**
     * The owner of the goal.
     */
    @Column(name = "owner", nullable = false)
    private String owner;

    /**
     * The status of the current task.
     */
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    /**
     * A time stamp of the last time this object was modified.
     */
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    /**
     * The time stamp of the creation of this object.
     */
    @Column(name = "created", nullable = false)
    private Instant created;

    public Goal() {
        this.lastModified = Instant.now();
        this.created = Instant.now();
    }

    public Goal(
            String name,
            String description,
            String owner
    ) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.lastModified = Instant.now();
        this.created = Instant.now();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Instant getCreated() {
        return this.created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}