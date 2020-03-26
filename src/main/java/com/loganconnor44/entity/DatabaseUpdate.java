package com.loganconnor44.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "database_updates")
@Data
public class DatabaseUpdate {
    public static String CREATED = "created";
    /**
     * A unique identifier for a task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "remote_id")
    private Integer remoteId;

    @Column(name = "browser_id")
    private String browserId;

    @Column(name = "owner")
    private String owner;

    /**
     * The time stamp of the creation of this object.
     */
    @Column(name = "created", nullable = false)
    private Instant created;

    public DatabaseUpdate() {}

    public DatabaseUpdate(Integer remoteId, String browserId, String owner) {
        this.remoteId = remoteId;
        this.browserId = browserId;
        this.owner = owner;
        this.created = Instant.now();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
