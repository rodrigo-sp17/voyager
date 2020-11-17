package com.nauta.voyager.service;

import com.nauta.voyager.repository.PobRepository;
import com.nauta.voyager.util.VoyagerContext;
import com.nauta.voyager.entity.Person;
import com.nauta.voyager.entity.Cabin;
import com.nauta.voyager.entity.Pob;
import com.nauta.voyager.repository.PersonRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PobService {
    
    private final VoyagerContext context;
    private final PobRepository pobRepository;
    private final PersonRepository personRepository;

    public PobService(VoyagerContext context, PobRepository pobRepository,
            PersonRepository personRepository) {
        this.context = context;
        this.pobRepository = pobRepository;
        this.personRepository = personRepository;
    } 
    
    public List<Cabin> getAllCabins() {
        String cabinString = context.getLocalProperty("cabins");        
        String[] cabins = cabinString.split(",");
        
        List<Cabin> result = new ArrayList<>();        
        for (String s : cabins) {
            result.add(new Cabin(s));
        }
        
        return result;
    }
    
    public List<String> getAllCrews() {
        String value = context.getLocalProperty("crews");
        String[] crews = value.split(",");
        return Arrays.asList(crews);
    }
    
    public List<String> getAllShifts() {
        String value = context.getLocalProperty("shifts");
        String[] shifts = value.split(",");
        return Arrays.asList(shifts);
    }
    
    public List<Person> getAllNonBoardedPeople() {
        return personRepository.findAllByBoardingStatus(false);
    }
    
    public List<Person> getAllBoardedPeople() {
        return personRepository.findAllByBoardingStatus(true);
    } 
    
    
    public Pob getLastPob() {
        Pob pob = pobRepository.findById(1L);
        if (pob == null) {
            pob = new Pob(LocalDate.now(),
                    "Açu - RJ", LocalDate.now(),
                    "", ChronoUnit.DAYS.addTo(LocalDate.now(), 28),
                    "A");
            pobRepository.save(pob);
        }
        return pob;
    }
    
    public void savePob(Pob pob) {
        pob.setPobId(1L);
        pobRepository.save(pob);
    }
}
