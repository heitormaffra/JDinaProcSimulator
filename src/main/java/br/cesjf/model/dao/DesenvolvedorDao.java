/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.dao;

import br.cesjf.model.dao.exceptions.NonexistentEntityException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.cesjf.model.entities.Equipe;
import java.util.ArrayList;
import java.util.Collection;
import br.cesjf.model.entities.Atividade;
import br.cesjf.model.entities.Desenvolvedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author heitormaffra
 */
public class DesenvolvedorDao extends GenericDao {

    public void create(Desenvolvedor desenvolvedor) throws Exception {
        if (desenvolvedor.getEquipeCollection() == null) {
            desenvolvedor.setEquipeCollection(new ArrayList<Equipe>());
        }
        if (desenvolvedor.getAtividadeCollection() == null) {
            desenvolvedor.setAtividadeCollection(new ArrayList<Atividade>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Equipe> attachedEquipeCollection = new ArrayList<Equipe>();
            for (Equipe equipeCollectionEquipeToAttach : desenvolvedor.getEquipeCollection()) {
                equipeCollectionEquipeToAttach = em.getReference(equipeCollectionEquipeToAttach.getClass(), equipeCollectionEquipeToAttach.getIdEquipe());
                attachedEquipeCollection.add(equipeCollectionEquipeToAttach);
            }
            desenvolvedor.setEquipeCollection(attachedEquipeCollection);
            Collection<Atividade> attachedAtividadeCollection = new ArrayList<Atividade>();
            for (Atividade atividadeCollectionAtividadeToAttach : desenvolvedor.getAtividadeCollection()) {
                atividadeCollectionAtividadeToAttach = em.getReference(atividadeCollectionAtividadeToAttach.getClass(), atividadeCollectionAtividadeToAttach.getIdAtivd());
                attachedAtividadeCollection.add(atividadeCollectionAtividadeToAttach);
            }
            desenvolvedor.setAtividadeCollection(attachedAtividadeCollection);
            em.persist(desenvolvedor);
            for (Equipe equipeCollectionEquipe : desenvolvedor.getEquipeCollection()) {
                Desenvolvedor oldIdDesenvOfEquipeCollectionEquipe = equipeCollectionEquipe.getIdDesenv();
                equipeCollectionEquipe.setIdDesenv(desenvolvedor);
                equipeCollectionEquipe = em.merge(equipeCollectionEquipe);
                if (oldIdDesenvOfEquipeCollectionEquipe != null) {
                    oldIdDesenvOfEquipeCollectionEquipe.getEquipeCollection().remove(equipeCollectionEquipe);
                    oldIdDesenvOfEquipeCollectionEquipe = em.merge(oldIdDesenvOfEquipeCollectionEquipe);
                }
            }
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
        } catch (PersistenceException ex) {
            throw new PersistenceException("Ero ao salvar registro", ex);
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
            Collection<Equipe> equipeCollectionOld = persistentDesenvolvedor.getEquipeCollection();
            Collection<Equipe> equipeCollectionNew = desenvolvedor.getEquipeCollection();
            Collection<Atividade> atividadeCollectionOld = persistentDesenvolvedor.getAtividadeCollection();
            Collection<Atividade> atividadeCollectionNew = desenvolvedor.getAtividadeCollection();
            Collection<Equipe> attachedEquipeCollectionNew = new ArrayList<Equipe>();
            for (Equipe equipeCollectionNewEquipeToAttach : equipeCollectionNew) {
                equipeCollectionNewEquipeToAttach = em.getReference(equipeCollectionNewEquipeToAttach.getClass(), equipeCollectionNewEquipeToAttach.getIdEquipe());
                attachedEquipeCollectionNew.add(equipeCollectionNewEquipeToAttach);
            }
            equipeCollectionNew = attachedEquipeCollectionNew;
            desenvolvedor.setEquipeCollection(equipeCollectionNew);
            Collection<Atividade> attachedAtividadeCollectionNew = new ArrayList<Atividade>();
            for (Atividade atividadeCollectionNewAtividadeToAttach : atividadeCollectionNew) {
                atividadeCollectionNewAtividadeToAttach = em.getReference(atividadeCollectionNewAtividadeToAttach.getClass(), atividadeCollectionNewAtividadeToAttach.getIdAtivd());
                attachedAtividadeCollectionNew.add(atividadeCollectionNewAtividadeToAttach);
            }
            atividadeCollectionNew = attachedAtividadeCollectionNew;
            desenvolvedor.setAtividadeCollection(atividadeCollectionNew);
            desenvolvedor = em.merge(desenvolvedor);
            for (Equipe equipeCollectionOldEquipe : equipeCollectionOld) {
                if (!equipeCollectionNew.contains(equipeCollectionOldEquipe)) {
                    equipeCollectionOldEquipe.setIdDesenv(null);
                    equipeCollectionOldEquipe = em.merge(equipeCollectionOldEquipe);
                }
            }
            for (Equipe equipeCollectionNewEquipe : equipeCollectionNew) {
                if (!equipeCollectionOld.contains(equipeCollectionNewEquipe)) {
                    Desenvolvedor oldIdDesenvOfEquipeCollectionNewEquipe = equipeCollectionNewEquipe.getIdDesenv();
                    equipeCollectionNewEquipe.setIdDesenv(desenvolvedor);
                    equipeCollectionNewEquipe = em.merge(equipeCollectionNewEquipe);
                    if (oldIdDesenvOfEquipeCollectionNewEquipe != null && !oldIdDesenvOfEquipeCollectionNewEquipe.equals(desenvolvedor)) {
                        oldIdDesenvOfEquipeCollectionNewEquipe.getEquipeCollection().remove(equipeCollectionNewEquipe);
                        oldIdDesenvOfEquipeCollectionNewEquipe = em.merge(oldIdDesenvOfEquipeCollectionNewEquipe);
                    }
                }
            }
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
            Collection<Equipe> equipeCollection = desenvolvedor.getEquipeCollection();
            for (Equipe equipeCollectionEquipe : equipeCollection) {
                equipeCollectionEquipe.setIdDesenv(null);
                equipeCollectionEquipe = em.merge(equipeCollectionEquipe);
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
