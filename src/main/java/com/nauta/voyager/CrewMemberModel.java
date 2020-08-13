/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.util.*;

/*
TODO:
* Implement Update
- Check if notifying listeners implementation is adequate
- Implement updateModel method
*/

/**
 *
 * @author rodrigo
 */
public class CrewMemberModel extends StateNotifier {
    
    private List<CrewMember> crewMemberList;
            
    public CrewMemberModel() {        
        // Builds a mock crewlist for testing purposes
        crewMemberList = new ArrayList<>();
        crewMemberList.add(0, new CrewMember("Alfafa", "WSUT", "Horse")); 
        crewMemberList.add(1, new CrewMember("Batata", "SISTAC", "Veggie")); 
        crewMemberList.add(2, new CrewMember("Foo", "WILSON", "Alien")); 
        crewMemberList.add(3, new CrewMember("Charlie", "ABG", "Person")); 
        crewMemberList.add(4, new CrewMember("Delta", "Delta2", "Bitch")); 
    }
       
    
    // Creates crewmember with new ID
    public void insertCrewMember(CrewMember member) {        
        crewMemberList.add(member);
        fireStateChanged();     
    }

    // Gets crewmember with specified ID
    public CrewMember getCrewMember(int id) {
        for (CrewMember c : crewMemberList) {
            if (c.getId() == id) {
                return c;
            } 
        }
        System.err.println("CrewMemberModel: getCrewMember(...) could not find CrewMember in list");
        return null;               
    }
    
    // Updates crew member with specified ID
    public int updateCrewMember(int id, CrewMember updatedMember) {
        // TODO       
        fireStateChanged();        
        return 0;
    }
    
    // Deletes crew member with specified ID
    public int deleteCrewMember(int id) {
        boolean removed = crewMemberList.removeIf(n -> (n.getId() == id));
        if (removed) {
            fireStateChanged();
            return 0;
        } else {
            System.err.println("CrewMemberModel: deleteCrewMember() could not find the member to delete");            
            return -1;
        }
    }
        
    // Getter
    public List<CrewMember> getList() { return crewMemberList; }
    
    // Updates model after edition
    public void updateModel() {
        fireStateChanged();        
    }
    
}
