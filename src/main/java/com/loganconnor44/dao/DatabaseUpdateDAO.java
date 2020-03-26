package com.loganconnor44.dao;

import com.loganconnor44.entity.DatabaseUpdate;
import com.loganconnor44.entity.Task;
import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Data
public class DatabaseUpdateDAO implements IDatabaseUpdateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUpdate(DatabaseUpdate databaseUpdate) {
        this.entityManager.persist(databaseUpdate);
    }

    @Override
    public List<DatabaseUpdate> getUpdatesByOwner(String owner) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<DatabaseUpdate> query = cb.createQuery(DatabaseUpdate.class);
        Root<DatabaseUpdate> root = query.from(DatabaseUpdate.class);
        query.select(root).where(cb.equal(root.get("owner"), owner));
        query.orderBy(cb.desc(root.get(DatabaseUpdate.CREATED)));

        TypedQuery<DatabaseUpdate> qry = this.entityManager.createQuery(query);

        return qry.getResultList();
    }
}