package com.nauta.voyager.service;

import com.nauta.voyager.entity.Post;
import com.nauta.voyager.repository.FunctionRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FunctionService {
    
    private final FunctionRepository functionRepository;

    public FunctionService(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }
    
    public List<Post> getAllFunctions() {
        return functionRepository.findAll();
    }    
}
