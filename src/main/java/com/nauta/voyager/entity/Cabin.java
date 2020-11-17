package com.nauta.voyager.entity;

import javax.persistence.Embeddable;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class Cabin implements RaftKey {
    
    @ColumnDefault(value = "''")
    private final String cabinName;

    public Cabin() {
        cabinName = "";
    }
       
    public Cabin(String cabinName) {
        this.cabinName = cabinName;
    }
    
    public String getName() { return cabinName; }
    
    @Override
    public String asKey() {
        return cabinName;
    }

    @Override
    public RaftKeyType getType() {
        return RaftKeyType.CABIN;
    }

    @Override
    public String toString() {
        return cabinName;
    }   

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cabin) {
            Cabin that = (Cabin) obj;
            return this.cabinName.equals(that.cabinName);
        }
        return false;
    }   
}
