package com.loganconnor44.dao;

import com.loganconnor44.entity.DatabaseUpdate;
import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
@Data
public class DatabaseUpdateDAO implements IDatabaseUpdateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUpdate(DatabaseUpdate databaseUpdate) {
        entityManager.persist(databaseUpdate);
    }
}