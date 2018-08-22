package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}
