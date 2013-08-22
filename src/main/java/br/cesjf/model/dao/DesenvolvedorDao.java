/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.dao;

import br.cesjf.model.dao.exceptions.NonexistentEntityException;
import br.cesjf.model.entites.Desenvolvedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author heitormaffra
 */
public class DesenvolvedorDao extends GenericDao {

    /**
     *
     * @param desenvolvedor
     * @return
     */
    public boolean create(Desenvolvedor desenvolvedor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(desenvolvedor);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;


        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @param desenvolvedor
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Desenvolvedor desenvolvedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            desenvolvedor = em.merge(desenvolvedor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = desenvolvedor.getIdDesenv();
                if (findDesenvolvedor(id) == null) {
                    throw new NonexistentEntityException("The desenvolvedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @param id
     * @throws NonexistentEntityException
     */
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Desenvolvedor desenvolvedor;
            try {
                desenvolvedor = em.getReference(Desenvolvedor.class, id);
                desenvolvedor.getIdDesenv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The desenvolvedor with id " + id + " no longer exists.", enfe);
            }
            em.remove(desenvolvedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     *
     * @return
     */
    public List<Desenvolvedor> findDesenvolvedorEntities() {
        return findDesenvolvedorEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<Desenvolvedor> findDesenvolvedorEntities(int maxResults, int firstResult) {
        return findDesenvolvedorEntities(false, maxResults, firstResult);
    }

    private List<Desenvolvedor> findDesenvolvedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Desenvolvedor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public Desenvolvedor findDesenvolvedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Desenvolvedor.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @return
     */
    public int getDesenvolvedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Desenvolvedor> rt = cq.from(Desenvolvedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
