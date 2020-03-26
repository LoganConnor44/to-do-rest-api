package com.loganconnor44.service;

import com.loganconnor44.dao.IDatabaseUpdateDAO;
import com.loganconnor44.dao.ITaskDAO;
import com.loganconnor44.entity.DatabaseUpdate;
import com.loganconnor44.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseUpdateService implements IDatabaseUpdateService{
    @Autowired
    private IDatabaseUpdateDAO databaseUpdateDAO;

    @Override
    public synchronized boolean addDatabaseUpdate(DatabaseUpdate databaseUpdate) {
        this.databaseUpdateDAO.addUpdate(databaseUpdate);
        return true;
    }

    @Override
    public List<DatabaseUpdate> getUpdatesByOwner(String owner) {
        return this.databaseUpdateDAO.getUpdatesByOwner(owner);
    }
}
