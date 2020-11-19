package com.nauta.voyager.repository;

import com.nauta.voyager.util.VoyagerContext;
import com.nauta.voyager.entity.Raft;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RaftRepository {
    
    private static final String RAFT_CONTEXT = "raftProperties";
    
    private final VoyagerContext context;
    
    private final Properties raftProperties;
    

    public RaftRepository(VoyagerContext context) {
        this.context = context;
        raftProperties = (Properties) context.getParam(RAFT_CONTEXT);
    }  
    
    private Map<String, Raft> propertiesToRules(Properties raftProperties) {
        Map<String, Raft> result = new HashMap<>();
        
        raftProperties.forEach((k,v) -> {
            result.put(k.toString(), Raft.parse(v.toString()));            
        });
        
        return result;
    }
            
    public Map<String, Raft> getAllRaftRules() {
        // evetytime, fetch and parse
        return propertiesToRules(raftProperties);
    }
    
    public Raft findRaft(String key) {
        Map<String, Raft> raftMap = propertiesToRules(raftProperties);
        return raftMap.get(key);
    }    
    
    public void addRaftRule(String key, Raft raft) {
        raftProperties.put(key, raft.id());
        context.addScreamer("raft");
        context.fireStateChanged();
    }
    
    public void removeRaftRule(String key) {
        raftProperties.remove(key);
        context.addScreamer("raft");
        context.fireStateChanged();
    }
    
}
