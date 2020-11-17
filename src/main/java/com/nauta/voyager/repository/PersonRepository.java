
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
    
    public void delete(Long personId) {
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Person p = em.find(Person.class, personId);
        em.remove(p);
        em.getTransaction().commit();        
        
        em.close();
    }
    
    
}
