package com.nauta.voyager.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

public interface RaftKey {
    
    
    
    String asKey();
    
    RaftKeyType getType();
    
}
