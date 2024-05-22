package ro.iss2024.persistance.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ro.iss2024.domain.Sef;

public class SefRepository {

    public int size() {
        return 0;
    }


    public Long save(Sef entity) {
        Long id = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            System.out.println("Am ajuns la save");
            session.beginTransaction();

            // Verifică dacă există deja un Sef cu același id și parola
            Query<Sef> query = session.createQuery("FROM Sef WHERE id = :id AND password = :passwd", Sef.class);
            query.setParameter("id", entity.getId());
            query.setParameter("passwd", entity.getPassword()); // asigură-te că passwd este un atribut al clasei Sef
            Sef existingSef = query.uniqueResult();

            if (existingSef == null) {
                session.persist(entity);
                id = entity.getId(); // Obține id-ul entității salvate
                session.getTransaction().commit();
            } else {
                System.out.println("Seful există deja. Nu se adaugă.");
                session.getTransaction().rollback();
            }
        } catch (HibernateException e) {
            // Gestionează excepțiile legate de Hibernate
            e.printStackTrace();
            throw new RuntimeException("Failed to save entity", e);
        }
        return id; // Returnează id-ul entității salvate sau null dacă nu a fost salvat
    }



    public Sef find(Long identifier) {
        return null;
    }


    public Sef update(Sef entity) {
        return null;
    }


    public Sef delete(Long identifier) {
        return null;
    }


    public Iterable<Sef> findAll() {
        return null;
    }


    public Sef findByUsernameandPwd(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Sef where username=:usernameS and password=:passwordS ", Sef.class)
                    .setParameter("usernameS", username)
                    .setParameter("passwordS",password)
                    .getSingleResultOrNull();
        }
    }
}
