package ro.iss2024.persistance.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ro.iss2024.domain.Angajat;
import ro.iss2024.domain.Sef;

public class AngajatRepository {

    public Angajat findByUsernameandPwd(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Angajat where username=:usernameS and password=:passwordS ", Angajat.class)
                    .setParameter("usernameS", username)
                    .setParameter("passwordS",password)
                    .getSingleResultOrNull();
        }
    }


    public int size() {
        return 0;
    }


    public Long save(Angajat entity) {
        Long id = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            System.out.println("Am ajuns la save");
            session.beginTransaction();

            // Verifică dacă există deja un Sef cu același id și parola
            Query<Angajat> query = session.createQuery("FROM Angajat WHERE id = :id ", Angajat.class);
            query.setParameter("id", entity.getId());
            Angajat existingSef = query.uniqueResult();

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


    public Angajat find(Long identifier) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Angajat where id=:idE ", Angajat.class)
                    .setParameter("idE", identifier)
                    .getSingleResultOrNull();
        }
    }


    public Angajat update(Angajat entity) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            return session.find(Angajat.class, entity.getId());
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            return null;
        }
    }


    public Angajat delete(Long identifier) {
        Angajat toDelete = find(identifier);
        if (toDelete != null) {
            Transaction tx = null;
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                tx = session.beginTransaction();

                // Șterge angajatul, iar datorită cascade delete, vor fi șterse și înregistrările din tabelul atasks asociate cu acest angajat
                session.delete(toDelete);

                tx.commit();
                return toDelete;
            } catch (Exception ex) {
                System.out.println(ex);
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        } else {
            return null;
        }
    }


    public Iterable<Angajat> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Angajat ", Angajat.class).getResultList();
        }
    }

    public Angajat findByUsername(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Angajat where username=:usernameS ", Angajat.class)
                    .setParameter("usernameS", username)
                    .getSingleResultOrNull();
        }
    }
}
