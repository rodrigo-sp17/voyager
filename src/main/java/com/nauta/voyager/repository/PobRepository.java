package com.nauta.voyager.repository;

import com.nauta.voyager.entity.Pob;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class PobRepository {

    private final EntityManagerFactory emf;

    public PobRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public Pob findById(Long pobId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Pob pob = em.find(Pob.class, pobId);
        em.getTransaction().commit();
        em.close();        
        
        return pob;
    }
     
    
    public void save(Pob pob) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(pob);
        em.getTransaction().commit();
        em.close();
    }
}
