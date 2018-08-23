package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.mockito.Mockito.*;

import org.hibernate.Transaction;
import org.junit.*;
import org.mockito.Mock;

public class DBHelperTest {
    SessionFactory sessionFactory;
    Session session;
    Transaction transaction;
    DBHelper underTest;
    Object testObject;
    @Before
    public void before(){
        //setup mocks
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        //setup class underTest and testObjects
        underTest = new DBHelper(sessionFactory);
        testObject = new Object();

        //return new mock objects so we can test their methods are being called
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void save_ShouldCall_SessionFactory_OpenSession(){
        underTest.save(testObject);

        verify(sessionFactory, times(1)).openSession();
    }

    @Test
    public void save_ShouldCall_Session_beginTransaction(){
        underTest.save(testObject);

        verify(session, times(1)).beginTransaction();
    }

    @Test
    public void save_ShouldCall_Session_save_With_Passed_Object(){
        underTest.save(testObject);

        verify(session, times(1)).save(testObject);
    }

    @Test
    public void save_ShouldCall_Transaction_commit(){
        underTest.save(testObject);

        verify(transaction,times(1)).commit();
    }

    @Test(expected = Error.class)
    public void save_ShouldCall_Transaction_rollback_When_TryBlock_Throws(){
        HibernateException exception = new HibernateException("WillThrowOnSession.Save");
        when(session.save(testObject)).thenThrow(exception);

        underTest.save(testObject);

        verify(transaction,times(1)).rollback();

    }

    @Test
    public void save_ShouldCall_Transaction_close(){
        underTest.save(testObject);

        verify(session,times(1)).close();
    }
}
