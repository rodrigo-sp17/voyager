/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.pob;

import com.nauta.voyager.people.Person;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Pob {
    private int pobId;
    
    private List<Person> members;
    
    private int numMembers;
            
    private LocalDate dateIssued;
    
    private String crew;

    public Pob(int pobId, List<Person> members,
            LocalDate dateIssued, String crew) {
        this.pobId = pobId;
        this.members = members;
        this.numMembers = members.size();
        this.dateIssued = dateIssued;
        this.crew = crew;
    }

    public int getPobId() {
        return pobId;
    }

    public void setPobId(int pobId) {
        this.pobId = pobId;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }
    
    
    
   
}
