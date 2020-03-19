package com.loganconnor44.dao;

import com.loganconnor44.entity.DatabaseUpdate;

import javax.persistence.EntityManager;

public interface IDatabaseUpdateDAO {
    void addUpdate(DatabaseUpdate databaseUpdate);
}