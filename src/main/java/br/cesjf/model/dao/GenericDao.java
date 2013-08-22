/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author heitor.filho
 */
public class GenericDao {

    private EntityManager emg = null;

    /**
     *
     */
    public GenericDao() {
        //emg = Persistence.createEntityManagerFactory("persistence").createEntityManager();
    }

    /**
     *
     */
    protected void createEntityManager() {
        //emg = Persistence.createEntityManagerFactory("persistence").createEntityManager();
    }
    
    /**
     *
     * @return
     */
    public EntityManager getEntityManager(){
        if(emg==null || !emg.isOpen()){
            emg = Persistence.createEntityManagerFactory("persistence").createEntityManager();
        }
        return emg ;
    }

    @Override
    public void finalize() throws Throwable {
        if (this.emg != null && this.emg.isOpen()) {
            this.emg.close();
            this.emg = null;
        }
        super.finalize();
    }
}
