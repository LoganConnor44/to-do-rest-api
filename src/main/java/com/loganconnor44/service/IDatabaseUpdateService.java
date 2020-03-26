package com.loganconnor44.service;

import com.loganconnor44.entity.DatabaseUpdate;
import com.loganconnor44.entity.Task;

import java.util.List;

public interface IDatabaseUpdateService {
    boolean addDatabaseUpdate(DatabaseUpdate update);
    List<DatabaseUpdate> getUpdatesByOwner(String owner);
}
