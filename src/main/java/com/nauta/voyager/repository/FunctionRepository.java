package com.nauta.voyager.repository;

import com.nauta.voyager.entity.Post;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class FunctionRepository {
    
    private final EntityManager entityManager;

    public FunctionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }   
      
    public List<Post> findAll() {
        return entityManager.createQuery("select p from Post p")
                .getResultList();
    }
    
    public Post findById(Long functionId) {
        return entityManager.find(Post.class, functionId);
    }
    
}
