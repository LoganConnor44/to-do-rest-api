package com.loganconnor44.dao;

import com.loganconnor44.entity.DatabaseUpdate;

import javax.persistence.EntityManager;
import java.util.List;

public interface IDatabaseUpdateDAO {
    void addUpdate(DatabaseUpdate databaseUpdate);
    List<DatabaseUpdate> getUpdatesByOwner(String owner);
}