package com.example.application.model.database;

import com.example.application.model.entity.Record;
import jakarta.persistence.*;

import java.util.List;

public class RecordManager {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");

    public void saveOrUpdateRecord(int boardSize, long timeMillis) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            TypedQuery<Record> query = em.createQuery(
                    "SELECT r FROM Record r WHERE r.boardSize = :boardSize", Record.class);
            query.setParameter("boardSize", boardSize);
            List<Record> results = query.getResultList();

            if (!results.isEmpty()) {
                Record existing = results.get(0);
                if (timeMillis < existing.getTimeMillis()) {
                    existing.setTimeMillis(timeMillis);
                    em.merge(existing);
                }
            } else {
                Record newRecord = new Record(boardSize, timeMillis);
                em.persist(newRecord);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Record getBestRecord(int boardSize) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Record> query = em.createQuery(
                    "SELECT r FROM Record r WHERE r.boardSize = :boardSize", Record.class);
            query.setParameter("boardSize", boardSize);
            List<Record> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
}