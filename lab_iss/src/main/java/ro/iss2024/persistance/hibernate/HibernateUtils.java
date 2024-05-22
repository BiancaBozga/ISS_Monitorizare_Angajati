package ro.iss2024.persistance.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ro.iss2024.domain.Angajat;
import ro.iss2024.domain.Sarcina;
import ro.iss2024.domain.Sef;


public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }

    private static  SessionFactory createNewSessionFactory(){
        sessionFactory = new Configuration()
                .addAnnotatedClass(Angajat.class)
                .addAnnotatedClass(Sef.class)
                .addAnnotatedClass(Sarcina.class)

                .buildSessionFactory();
        return sessionFactory;
    }

    public static  void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
}