package com.loganconnor44.service;

import com.loganconnor44.dao.IDatabaseUpdateDAO;
import com.loganconnor44.dao.ITaskDAO;
import com.loganconnor44.entity.DatabaseUpdate;
import com.loganconnor44.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUpdateService implements IDatabaseUpdateService{
    @Autowired
    private IDatabaseUpdateDAO databaseUpdateDAO;

    /**
     * Adds the current task to the database.
     *
     * @param task A child object of a goal.
     * @return boolean
     */
    @Override
    public synchronized boolean addDatabaseUpdate(DatabaseUpdate databaseUpdate) {
        databaseUpdateDAO.addUpdate(databaseUpdate);
        return true;
    }
}
