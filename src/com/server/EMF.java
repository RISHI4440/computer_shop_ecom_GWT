package com.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {
    
    private EMF() {}

    public static EntityManagerFactory get(String pu) {
        
        EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory(pu);
        return emfInstance;
    }
}
