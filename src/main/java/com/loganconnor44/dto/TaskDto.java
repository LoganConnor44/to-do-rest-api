package com.loganconnor44.dto;

import com.loganconnor44.helpers.Difficulty;
import com.loganconnor44.helpers.Importance;
import com.loganconnor44.helpers.Status;
import lombok.Data;
import java.time.Instant;

@Data
public class TaskDto {

    private Integer id;
    private Integer remoteId;
    private String name;
    private String owner;
    private Status status;
    private Long created;
    private Long lastModified;
    private Difficulty difficulty;
    private Importance importance;
}