package com.log.parser.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.log.parser.model.Event;

/**
 * Class to handle CRUD operations for @{@link Event}
 */
public class EventDao {
    private static final Logger logger = LoggerFactory.getLogger(EventDao.class);
    private static final EntityManagerFactory emFactoryObj;
    private static final String PERSISTENCE_UNIT_NAME = "DemoPersistence";

    static {
        emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static EntityManager getEntityManager() {
        return emFactoryObj.createEntityManager();
    }

    public boolean saveEvent(Event event) {
        logger.trace("will save event  {}", event);
        EntityManager entityMgr = getEntityManager();
        entityMgr.getTransaction().begin();
        entityMgr.persist(event);
        entityMgr.getTransaction().commit();
        entityMgr.clear();
        logger.trace("Event Successfully Inserted In The Database : {}", event.getEventId());
        return true;
    }

    public Event getEvent(String id) {
        EntityManager entityMgr = null;
        Event event = null;
        try {
            entityMgr = getEntityManager();
            entityMgr.getTransaction().begin();
            Query query = entityMgr.createQuery("FROM Event ev where ev.eventId=:id ");
            query.setParameter("id", id);
            List<Event> events = query.getResultList();
            for (Iterator iterator = events.iterator(); iterator.hasNext(); ) {
                event = (Event) iterator.next();
            }
            entityMgr.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(entityMgr).clear();
        }
        return event;
    }
}
