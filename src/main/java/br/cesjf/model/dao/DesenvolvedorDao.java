/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.dao;

import br.cesjf.model.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.Desenvolvedor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
            Collection<Atividade> attachedAtividadeCollection = new ArrayList<Atividade>();
            for (Atividade atividadeCollectionAtividadeToAttach : desenvolvedor.getAtividadeCollection()) {
                atividadeCollectionAtividadeToAttach = em.getReference(atividadeCollectionAtividadeToAttach.getClass(), atividadeCollectionAtividadeToAttach.getIdAtivd());
                attachedAtividadeCollection.add(atividadeCollectionAtividadeToAttach);
            }
            desenvolvedor.setAtividadeCollection(attachedAtividadeCollection);
            em.persist(desenvolvedor);
            for (Atividade atividadeCollectionAtividade : desenvolvedor.getAtividadeCollection()) {
                Desenvolvedor oldIdDesenvOfAtividadeCollectionAtividade = atividadeCollectionAtividade.getIdDesenv();
                atividadeCollectionAtividade.setIdDesenv(desenvolvedor);
                atividadeCollectionAtividade = em.merge(atividadeCollectionAtividade);
                if (oldIdDesenvOfAtividadeCollectionAtividade != null) {
                    oldIdDesenvOfAtividadeCollectionAtividade.getAtividadeCollection().remove(atividadeCollectionAtividade);
                    oldIdDesenvOfAtividadeCollectionAtividade = em.merge(oldIdDesenvOfAtividadeCollectionAtividade);
                }
            }
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

    public void edit(Desenvolvedor desenvolvedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Desenvolvedor persistentDesenvolvedor = em.find(Desenvolvedor.class, desenvolvedor.getIdDesenv());
            Collection<Atividade> atividadeCollectionOld = persistentDesenvolvedor.getAtividadeCollection();
            Collection<Atividade> atividadeCollectionNew = desenvolvedor.getAtividadeCollection();
            Collection<Atividade> attachedAtividadeCollectionNew = new ArrayList<Atividade>();
            for (Atividade atividadeCollectionNewAtividadeToAttach : atividadeCollectionNew) {
                atividadeCollectionNewAtividadeToAttach = em.getReference(atividadeCollectionNewAtividadeToAttach.getClass(), atividadeCollectionNewAtividadeToAttach.getIdAtivd());
                attachedAtividadeCollectionNew.add(atividadeCollectionNewAtividadeToAttach);
            }
            atividadeCollectionNew = attachedAtividadeCollectionNew;
            desenvolvedor.setAtividadeCollection(atividadeCollectionNew);
            desenvolvedor = em.merge(desenvolvedor);
            for (Atividade atividadeCollectionOldAtividade : atividadeCollectionOld) {
                if (!atividadeCollectionNew.contains(atividadeCollectionOldAtividade)) {
                    atividadeCollectionOldAtividade.setIdDesenv(null);
                    atividadeCollectionOldAtividade = em.merge(atividadeCollectionOldAtividade);
                }
            }
            for (Atividade atividadeCollectionNewAtividade : atividadeCollectionNew) {
                if (!atividadeCollectionOld.contains(atividadeCollectionNewAtividade)) {
                    Desenvolvedor oldIdDesenvOfAtividadeCollectionNewAtividade = atividadeCollectionNewAtividade.getIdDesenv();
                    atividadeCollectionNewAtividade.setIdDesenv(desenvolvedor);
                    atividadeCollectionNewAtividade = em.merge(atividadeCollectionNewAtividade);
                    if (oldIdDesenvOfAtividadeCollectionNewAtividade != null && !oldIdDesenvOfAtividadeCollectionNewAtividade.equals(desenvolvedor)) {
                        oldIdDesenvOfAtividadeCollectionNewAtividade.getAtividadeCollection().remove(atividadeCollectionNewAtividade);
                        oldIdDesenvOfAtividadeCollectionNewAtividade = em.merge(oldIdDesenvOfAtividadeCollectionNewAtividade);
                    }
                }
            }
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
            Collection<Atividade> atividadeCollection = desenvolvedor.getAtividadeCollection();
            for (Atividade atividadeCollectionAtividade : atividadeCollection) {
                atividadeCollectionAtividade.setIdDesenv(null);
                atividadeCollectionAtividade = em.merge(atividadeCollectionAtividade);
            }
            em.remove(desenvolvedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Desenvolvedor> findDesenvolvedorEntities() {
        return findDesenvolvedorEntities(true, -1, -1);
    }

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

    public Desenvolvedor findDesenvolvedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Desenvolvedor.class, id);
        } finally {
            em.close();
        }
    }

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
