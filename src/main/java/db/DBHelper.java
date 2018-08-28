package db;

import models.Paddock;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

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

    public <T> List<T> getAllInstances(Class<T> classType) {
        List<T> objectList;
        session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            objectList = session.createCriteria(classType).list();
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
