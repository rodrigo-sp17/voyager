package com.nauta.voyager.service;

import com.nauta.voyager.util.ServiceFactory;
import com.nauta.voyager.entity.Cabin;
import com.nauta.voyager.entity.Post;
import com.nauta.voyager.entity.Person;
import com.nauta.voyager.entity.Raft;
import com.nauta.voyager.entity.RaftKey;
import com.nauta.voyager.repository.FunctionRepository;
import com.nauta.voyager.repository.RaftRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RaftService {

    private final RaftRepository raftRepository;
    private final FunctionService functionService;
    private final PobService pobService;
    
    private Map<RaftKey, Raft> cacheRules;
    
    public RaftService(RaftRepository raftRepository) {
        this.raftRepository = raftRepository;
        functionService = ServiceFactory.getFunctionService();
        pobService = ServiceFactory.getPobService();
    }   
    
    private void loadCache() {
        Map<RaftKey, Raft> map = new HashMap<>();
        Map<String, Cabin> cabins = pobService.getAllCabins().stream()
                .collect(Collectors.toMap(Cabin::asKey, c -> c));
        Map<String, Post> functions = functionService.getAllFunctions()
                .stream()
                .collect(Collectors.toMap(Post::asKey, f -> f));
        
        Map<String, Raft> rafts = raftRepository.getAllRaftRules();
        rafts.forEach((k,v) -> {
            if (cabins.containsKey(k)) {
                map.put(cabins.get(k), v);
            } else if (functions.containsKey(k)) {
                map.put(functions.get(k), v);
            }
        });

        cacheRules = map;
    }
    
    public Map<RaftKey, Raft> getAllRaftRules() {
        loadCache();
        return cacheRules;
    }
    
    public String getRaftByPerson(final Person person) {
        String functionKey = person.getFunction().asKey();
        String cabinKey = person.getBoardingData().getCabin().asKey();
        
        Map<String, Raft> raftRules = raftRepository.getAllRaftRules();
        
        // Checks if Post produces a Raft
        Raft result = raftRules.get(functionKey);
        if (result != null) {            
            return result.textPT();
        } 
        
        // Checks if the Cabin produces a Raft
        result = raftRules.get(cabinKey);
        if (result != null) {
            return result.textPT();
        } else {
            // If neither has produced any Raft, there is no rule
            return "N/A";
        }        
    }   
    
    public Raft getRaftByKey(RaftKey key) {
        return raftRepository.findRaft(key.asKey());
    }
    
    public void addRaftRule(RaftKey key, Raft raft) {
        raftRepository.addRaftRule(key.asKey(), raft);      
    }
       
    public void removeRaftRule(RaftKey key) {
        raftRepository.removeRaftRule(key.asKey());
    }
}
