/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.dao;

import br.cesjf.model.dao.exceptions.NonexistentEntityException;
import br.cesjf.model.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.Projeto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author heitormaffra
 */
public class ProjetoDao extends GenericDao{

    public boolean create(Projeto projeto) throws PreexistingEntityException, Exception {
        
        boolean salvo = false;
        if (projeto.getAtividadeCollection() == null) {
            projeto.setAtividadeCollection(new ArrayList<Atividade>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Atividade> attachedAtividadeCollection = new ArrayList<Atividade>();
            for (Atividade atividadeCollectionAtividadeToAttach : projeto.getAtividadeCollection()) {
                atividadeCollectionAtividadeToAttach = em.getReference(atividadeCollectionAtividadeToAttach.getClass(), atividadeCollectionAtividadeToAttach.getIdAtivd());
                attachedAtividadeCollection.add(atividadeCollectionAtividadeToAttach);
            }
            projeto.setAtividadeCollection(attachedAtividadeCollection);
            em.persist(projeto);
            for (Atividade atividadeCollectionAtividade : projeto.getAtividadeCollection()) {
                Projeto oldIdProjetoOfAtividadeCollectionAtividade = atividadeCollectionAtividade.getIdProjeto();
                atividadeCollectionAtividade.setIdProjeto(projeto);
                atividadeCollectionAtividade = em.merge(atividadeCollectionAtividade);
                if (oldIdProjetoOfAtividadeCollectionAtividade != null) {
                    oldIdProjetoOfAtividadeCollectionAtividade.getAtividadeCollection().remove(atividadeCollectionAtividade);
                    oldIdProjetoOfAtividadeCollectionAtividade = em.merge(oldIdProjetoOfAtividadeCollectionAtividade);
                }
            }
            em.getTransaction().commit();
            salvo = true;
            return salvo;
        } catch (Exception ex) {
            if (findProjeto(projeto.getIdProjeto()) != null) {
                throw new PreexistingEntityException("Projeto " + projeto + " já existe.", ex);
            }
            throw ex;
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Projeto projeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projeto persistentProjeto = em.find(Projeto.class, projeto.getIdProjeto());
            Collection<Atividade> atividadeCollectionOld = persistentProjeto.getAtividadeCollection();
            Collection<Atividade> atividadeCollectionNew = projeto.getAtividadeCollection();
            Collection<Atividade> attachedAtividadeCollectionNew = new ArrayList<Atividade>();
            for (Atividade atividadeCollectionNewAtividadeToAttach : atividadeCollectionNew) {
                atividadeCollectionNewAtividadeToAttach = em.getReference(atividadeCollectionNewAtividadeToAttach.getClass(), atividadeCollectionNewAtividadeToAttach.getIdAtivd());
                attachedAtividadeCollectionNew.add(atividadeCollectionNewAtividadeToAttach);
            }
            atividadeCollectionNew = attachedAtividadeCollectionNew;
            projeto.setAtividadeCollection(atividadeCollectionNew);
            projeto = em.merge(projeto);
            for (Atividade atividadeCollectionOldAtividade : atividadeCollectionOld) {
                if (!atividadeCollectionNew.contains(atividadeCollectionOldAtividade)) {
                    atividadeCollectionOldAtividade.setIdProjeto(null);
                    atividadeCollectionOldAtividade = em.merge(atividadeCollectionOldAtividade);
                }
            }
            for (Atividade atividadeCollectionNewAtividade : atividadeCollectionNew) {
                if (!atividadeCollectionOld.contains(atividadeCollectionNewAtividade)) {
                    Projeto oldIdProjetoOfAtividadeCollectionNewAtividade = atividadeCollectionNewAtividade.getIdProjeto();
                    atividadeCollectionNewAtividade.setIdProjeto(projeto);
                    atividadeCollectionNewAtividade = em.merge(atividadeCollectionNewAtividade);
                    if (oldIdProjetoOfAtividadeCollectionNewAtividade != null && !oldIdProjetoOfAtividadeCollectionNewAtividade.equals(projeto)) {
                        oldIdProjetoOfAtividadeCollectionNewAtividade.getAtividadeCollection().remove(atividadeCollectionNewAtividade);
                        oldIdProjetoOfAtividadeCollectionNewAtividade = em.merge(oldIdProjetoOfAtividadeCollectionNewAtividade);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = projeto.getIdProjeto();
                if (findProjeto(id) == null) {
                    throw new NonexistentEntityException("The projeto with id " + id + " no longer exists.");
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
            Projeto projeto;
            try {
                projeto = em.getReference(Projeto.class, id);
                projeto.getIdProjeto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projeto with id " + id + " no longer exists.", enfe);
            }
            Collection<Atividade> atividadeCollection = projeto.getAtividadeCollection();
            for (Atividade atividadeCollectionAtividade : atividadeCollection) {
                atividadeCollectionAtividade.setIdProjeto(null);
                atividadeCollectionAtividade = em.merge(atividadeCollectionAtividade);
            }
            em.remove(projeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Projeto> findProjetoEntities() {
        return findProjetoEntities(true, -1, -1);
    }

    public List<Projeto> findProjetoEntities(int maxResults, int firstResult) {
        return findProjetoEntities(false, maxResults, firstResult);
    }

    private List<Projeto> findProjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projeto.class));
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

    public Projeto findProjeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projeto> rt = cq.from(Projeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
