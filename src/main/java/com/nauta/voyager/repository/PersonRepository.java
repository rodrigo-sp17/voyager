
package com.nauta.voyager.repository;

import com.nauta.voyager.entity.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PersonRepository {
    
    private final EntityManagerFactory emf;

    public PersonRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public List<Person> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Person> result = em.createQuery("select p from Person p")
                .getResultList();
        em.getTransaction().commit();
        em.close();
        return result;
    }
    
    public List<Person> findAllByBoardingStatus(boolean status) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String query = "select p from Person p "
                + "where boarded = :status";
        List<Person> persons = em.createQuery(query)
                .setParameter("status", status)
                .getResultList();
        em.getTransaction().commit();
        em.close();
        return persons;       
    }
    
    public List<Person> findByCrew(String crew) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String query = "select p from Person p "
                + "where crew = :crew";
        List<Person> persons = em.createQuery(query)
                .setParameter("crew", crew)
                .getResultList();
        em.getTransaction().commit();
        em.close();
        return persons;
    }
    
    public List<Person> findByCrewAndCrewMember(String crew,
            boolean isCrewMember) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String query = "select p from Person p "
                + "where crew = :crew "
                + "and function.crewMember = :isCrewMember";
        List<Person> persons = em.createQuery(query)
                .setParameter("crew", crew)
                .setParameter("isCrewMember", isCrewMember)
                .getResultList();
        em.getTransaction().commit();
        em.close();
        return persons;
    }
    
    public List<Person> findByCrewMember(boolean isCrewMember) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String query = "select p from Person p "
                + "where function.crewMember = :isCrewMember";
        List<Person> persons = em.createQuery(query)
                .setParameter("isCrewMember", isCrewMember)
                .getResultList();
        em.getTransaction().commit();
        em.close();
        
        return persons;
    }
    
    public Person findById(Long personId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person p = em.find(Person.class, personId);
        em.getTransaction().commit();
        em.close();
        
        return p;
    }
    
    public boolean exists(Long personId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        boolean result = em.getReference(Person.class, personId) != null;        
        em.getTransaction().commit();
        em.close();
        
        return result;
    }   
    
    public void save(Person p) { 
        EntityManager em = emf.createEntityManager();
               
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();        
        
        em.close();
    }
    
    public Person update(Person p) {
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Person savedPerson = em.merge(p);
        em.getTransaction().commit();
        
        em.close();
        
        return savedPerson;
    }
    
    public void updateAll(List<Person> persons) {
        EntityManager em = emf.createEntityManager();        
        em.getTransaction().begin();
        for (Person p : persons) {
            em.merge(p);
        }       
        em.getTransaction().commit();
        
        em.close();        
    }
    
    public void unboardAll() {
        EntityManager em = emf.createEntityManager();        
        em.getTransaction().begin();
        em.createQuery("update Person p "
                + "set boarded = false where boarded = true")
                .executeUpdate();        
        em.getTransaction().commit();        
        em.close();  
    }
    
    public void unboardByCrew(String crew, boolean isCrewMember) {
        EntityManager em = emf.createEntityManager();        
        em.getTransaction().begin();
        em.createQuery("update Person p "
                + "set boarded = false "
                + "where crew = :crew "
                + "and function.crewMember = :isCrewMember")
                .setParameter("crew", crew)
                .setParameter("isCrewMember", isCrewMember)
                .executeUpdate();        
        em.getTransaction().commit();        
        em.close();  
    }
    
    public void delete(Long personId) {
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Person p = em.find(Person.class, personId);
        em.remove(p);
        em.getTransaction().commit();        
        
        em.close();
    }
    
    
}
