package db;

import models.Paddock;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.UUID;

public class DBHelper {

    private Session session;
    private Transaction transaction;
    private SessionFactory sessionFactory;

    public DBHelper(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void save(Object object) {
        session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
        catch (HibernateException error) {
            transaction.rollback();
            error.printStackTrace();
            throw new Error("Could not save object to database", error);
        }
        finally {
            session.close();
        }
    }

    public <T> T getInstance(Class classType, UUID id){
        session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(classType);
            criteria.add(Restrictions.eq("id", id));
            return (T) criteria.uniqueResult();
        }
        catch (HibernateException error) {
            transaction.rollback();
            error.printStackTrace();
            throw new Error("Could not get instance from database", error);
        }
    }

    public <T> List<T> getAllInstances(Class<T> classInstance) {
        List<T> objectList;
        session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            objectList = session.createCriteria(classInstance).list();
            return objectList;
        }
        catch (HibernateException error) {
            transaction.rollback();
            error.printStackTrace();
            throw new Error("Could not get all object from database", error);
        }
        finally {
            session.close();
        }
    }
}
