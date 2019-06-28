package com.loganconnor44.entity;

import com.loganconnor44.helpers.Status;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.Instant;
import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    /**
     * A unique identifier for a task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id", nullable = false)
    private Integer id;

    /**
     * The associated / parent goal of the current task.
     */
    @ManyToOne
    @JoinTable(
            name = "goals_tasks",
            joinColumns = @JoinColumn(name = "task_fk"),
            inverseJoinColumns = @JoinColumn(name = "goal_fk")
    )
    private Goal goal;

    /**
     * The name of the task.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The description of the task.
     */
    @Column(length = 2000)
    private String description;

    /**
     * The subtasks that are associated with the current task.
     */
    @ManyToOne()
    @JoinTable(
            name = "tasks_subtasks",
            joinColumns = @JoinColumn(name = "parent_task_fk"),
            inverseJoinColumns = @JoinColumn(name = "child_subtask_fk")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task parentTask;

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

    /**
     * The status of the current task.
     */
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public Task() {
        this.lastModified = Instant.now();
        this.created = Instant.now();
    }

    public Task(
            int id,
            String name,
            String description,
            Goal goal,
            Task parentTask,
            Status status
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.goal = goal;
        this.parentTask = parentTask;
        this.status = status;
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

    public Goal getGoal() {
        return this.goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Task getParentTask() {
        return this.parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
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