/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.dao;

import br.cesjf.model.dao.exceptions.NonexistentEntityException;
import br.cesjf.model.dao.exceptions.PreexistingEntityException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.cesjf.model.entities.Desenvolvedor;
import br.cesjf.model.entities.Equipe;
import java.util.List;
import javax.persistence.EntityManager;
/**
 *
 * @author heitormaffra
 */
public class EquipeDao extends GenericDao{

    public void create(Equipe equipe) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Desenvolvedor idDesenv = equipe.getIdDesenv();
            if (idDesenv != null) {
                idDesenv = em.getReference(idDesenv.getClass(), idDesenv.getIdDesenv());
                equipe.setIdDesenv(idDesenv);
            }
            em.persist(equipe);
            if (idDesenv != null) {
                idDesenv.getEquipeCollection().add(equipe);
                idDesenv = em.merge(idDesenv);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquipe(equipe.getIdEquipe()) != null) {
                throw new PreexistingEntityException("Equipe " + equipe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipe equipe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe persistentEquipe = em.find(Equipe.class, equipe.getIdEquipe());
            Desenvolvedor idDesenvOld = persistentEquipe.getIdDesenv();
            Desenvolvedor idDesenvNew = equipe.getIdDesenv();
            if (idDesenvNew != null) {
                idDesenvNew = em.getReference(idDesenvNew.getClass(), idDesenvNew.getIdDesenv());
                equipe.setIdDesenv(idDesenvNew);
            }
            equipe = em.merge(equipe);
            if (idDesenvOld != null && !idDesenvOld.equals(idDesenvNew)) {
                idDesenvOld.getEquipeCollection().remove(equipe);
                idDesenvOld = em.merge(idDesenvOld);
            }
            if (idDesenvNew != null && !idDesenvNew.equals(idDesenvOld)) {
                idDesenvNew.getEquipeCollection().add(equipe);
                idDesenvNew = em.merge(idDesenvNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipe.getIdEquipe();
                if (findEquipe(id) == null) {
                    throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipe equipe;
            try {
                equipe = em.getReference(Equipe.class, id);
                equipe.getIdEquipe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.", enfe);
            }
            Desenvolvedor idDesenv = equipe.getIdDesenv();
            if (idDesenv != null) {
                idDesenv.getEquipeCollection().remove(equipe);
                idDesenv = em.merge(idDesenv);
            }
            em.remove(equipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipe> findEquipeEntities() {
        return findEquipeEntities(true, -1, -1);
    }

    public List<Equipe> findEquipeEntities(int maxResults, int firstResult) {
        return findEquipeEntities(false, maxResults, firstResult);
    }

    private List<Equipe> findEquipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipe.class));
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

    public Equipe findEquipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipe> rt = cq.from(Equipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
