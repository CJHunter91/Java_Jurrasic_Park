package db;

import models.Paddock;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class DBHelper {

    private static Session session;
    private static Transaction transaction;

    public static void save(Object object) {
        session = HibernateUtil.getSessionFactory().openSession();
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

    public static List<Paddock> getAllInstances(Class classType) {
        List<Paddock> listOfPaddocks;
        session = HibernateUtil.getSessionFactory().openSession();
        try{
            transaction = session.beginTransaction();
            listOfPaddocks = session.createCriteria(classType).list();
            return listOfPaddocks;
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
