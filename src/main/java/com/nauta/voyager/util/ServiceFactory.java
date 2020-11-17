package com.nauta.voyager.util;

import com.nauta.voyager.util.VoyagerContext;
import com.nauta.voyager.service.VesselService;
import com.nauta.voyager.repository.FunctionRepository;
import com.nauta.voyager.repository.PersonRepository;
import com.nauta.voyager.repository.PobRepository;
import com.nauta.voyager.repository.RaftRepository;
import com.nauta.voyager.service.ExporterService;
import com.nauta.voyager.service.FunctionService;
import com.nauta.voyager.service.PersonService;
import com.nauta.voyager.service.PobService;
import com.nauta.voyager.service.RaftService;
import javax.persistence.EntityManagerFactory;

public class ServiceFactory {
    
    private static EntityManagerFactory sf;

    public ServiceFactory(EntityManagerFactory EntityManagerFactory) {
        sf = EntityManagerFactory;
    }    
        
    public static PersonService getPersonService() {
         return new PersonService(
                 new PersonRepository(
                         sf));
    }
    
    public static PobService getPobService() {
        return new PobService(
        VoyagerContext.getContext(),
        new PobRepository(
                sf),
        new PersonRepository(
                sf));
    }
    
    public static FunctionService getFunctionService() {
        return new FunctionService(
                new FunctionRepository(sf.createEntityManager()));
    }
   
    public static RaftService getRaftService() {            
        return new RaftService(new RaftRepository(VoyagerContext.getContext()));
    }
    
    public static ExporterService getExporterService() {
        return new ExporterService();
    }
    
    public static VesselService getVesselService() {
        return new VesselService();
    }
}
