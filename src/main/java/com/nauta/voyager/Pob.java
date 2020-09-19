/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Pob {
    private int pobId;
    
    private List<CrewMember> members;
    
    private int numMembers;
            
    private LocalDate dateIssued;    

    public Pob(int pobId, List<CrewMember> members, LocalDate dateIssued) {
        this.pobId = pobId;
        this.members = members;
        this.numMembers = members.size();
        this.dateIssued = dateIssued;
    }

    public int getPobId() {
        return pobId;
    }

    public void setPobId(int pobId) {
        this.pobId = pobId;
    }

    public List<CrewMember> getMembers() {
        return members;
    }

    public void setMembers(List<CrewMember> members) {
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
    
    
    
   
}
