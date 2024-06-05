package ro.iss2024.persistance.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ro.iss2024.domain.Angajat;
import ro.iss2024.domain.Sarcina;
import ro.iss2024.domain.Sef;

import java.time.LocalDateTime;
import java.util.*;

public class SarcinaRepository {

    public int size() {
        return 0;
    }
    public List<Sarcina> findSarciniByAngajatAndTime(Long angajatId, LocalDateTime fromTime) {
        List<Sarcina> sarcini = new ArrayList<>();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            String hql = "FROM Sarcina s WHERE s.receptor.id = :angajatId AND s.dataOraAtribuire >= :fromTime";
            Query<Sarcina> query = session.createQuery(hql, Sarcina.class);
            query.setParameter("angajatId", angajatId);
            query.setParameter("fromTime", fromTime);
            sarcini = query.list();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to find sarcini by angajat and time", e);
        }

        return sarcini;
    }

    public List<Sarcina> findTop3ByDescriere() {
        List<Sarcina> top3Sarcini = new ArrayList<>();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            // HQL query to find top 3 most frequent descriptions
            String hqlDescrieri = "SELECT s.descriere " +
                    "FROM Sarcina s " +
                    "GROUP BY s.descriere " +
                    "ORDER BY COUNT(s.descriere) DESC";
            Query<String> queryDescrieri = session.createQuery(hqlDescrieri, String.class);
            queryDescrieri.setMaxResults(3);
            List<String> top3Descrieri = queryDescrieri.list();

            if (!top3Descrieri.isEmpty()) {
                // HQL query to get tasks with the top 3 descriptions
                String hqlTasks = "FROM Sarcina s WHERE s.descriere IN (:descriptions)";
                Query<Sarcina> queryTasks = session.createQuery(hqlTasks, Sarcina.class);
                queryTasks.setParameter("descriptions", top3Descrieri);
                List<Sarcina> sarcini = queryTasks.list();

                // Add only one task per description until we have 3 tasks
                Set<String> addedDescrieri = new HashSet<>();
                for (Sarcina sarcina : sarcini) {
                    if (addedDescrieri.size() >= 3) {
                        break;
                    }
                    if (!addedDescrieri.contains(sarcina.getDescriere())) {
                        top3Sarcini.add(sarcina);
                        addedDescrieri.add(sarcina.getDescriere());
                    }
                }
            }

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get top 3 sarcini by description", e);
        }

        return top3Sarcini;
    }




    public Long save(Sarcina entity) {
        Long id = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            System.out.println("Am ajuns la save sarcina ");
            session.beginTransaction();

                session.persist(entity);
                id = entity.getId(); // Obține id-ul entității salvate
                session.getTransaction().commit();

        } catch (HibernateException e) {
            // Gestionează excepțiile legate de Hibernate
            e.printStackTrace();
            throw new RuntimeException("Failed to save entity", e);
        }
        return id; // Returnează id-ul entității salvate sau null dacă nu a fost salvat
    }


    public Sarcina find(Long identifier) {
        return null;
    }


    public Sarcina update(Sarcina entity) {
        return null;
    }


    public Sarcina delete(Long identifier) {
        return null;
    }


    public Iterable<Sarcina> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Sarcina ", Sarcina.class).getResultList();
        }
    }
}
