package ro.iss2024.persistance.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ro.iss2024.domain.Angajat;

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
        return null;
    }


    public Angajat find(Long identifier) {
        return null;
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
        return null;
    }


    public Iterable<Angajat> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Angajat ", Angajat.class).getResultList();
        }
    }
}
