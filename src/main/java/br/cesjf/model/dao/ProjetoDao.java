/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.dao;

import br.cesjf.model.dao.GenericDao;
import br.cesjf.model.entites.Atividade;
import br.cesjf.model.entites.Projeto;
import br.cesjf.model.entites.exceptions.NonexistentEntityException;
import br.cesjf.model.entites.exceptions.PreexistingEntityException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author heitormaffra
 */
public class ProjetoDao extends GenericDao{

    public void create(Projeto projeto) throws PreexistingEntityException, Exception {
        if (projeto.getAtividadeList() == null) {
            projeto.setAtividadeList(new ArrayList<Atividade>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Atividade> attachedAtividadeList = new ArrayList<Atividade>();
            for (Atividade atividadeListAtividadeToAttach : projeto.getAtividadeList()) {
                atividadeListAtividadeToAttach = em.getReference(atividadeListAtividadeToAttach.getClass(), atividadeListAtividadeToAttach.getIdAtivd());
                attachedAtividadeList.add(atividadeListAtividadeToAttach);
            }
            projeto.setAtividadeList(attachedAtividadeList);
            em.persist(projeto);
            for (Atividade atividadeListAtividade : projeto.getAtividadeList()) {
                Projeto oldIdProjetoOfAtividadeListAtividade = atividadeListAtividade.getIdProjeto();
                atividadeListAtividade.setIdProjeto(projeto);
                atividadeListAtividade = em.merge(atividadeListAtividade);
                if (oldIdProjetoOfAtividadeListAtividade != null) {
                    oldIdProjetoOfAtividadeListAtividade.getAtividadeList().remove(atividadeListAtividade);
                    oldIdProjetoOfAtividadeListAtividade = em.merge(oldIdProjetoOfAtividadeListAtividade);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProjeto(projeto.getIdProjeto()) != null) {
                throw new PreexistingEntityException("Projeto " + projeto + " already exists.", ex);
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
            List<Atividade> atividadeListOld = persistentProjeto.getAtividadeList();
            List<Atividade> atividadeListNew = projeto.getAtividadeList();
            List<Atividade> attachedAtividadeListNew = new ArrayList<Atividade>();
            for (Atividade atividadeListNewAtividadeToAttach : atividadeListNew) {
                atividadeListNewAtividadeToAttach = em.getReference(atividadeListNewAtividadeToAttach.getClass(), atividadeListNewAtividadeToAttach.getIdAtivd());
                attachedAtividadeListNew.add(atividadeListNewAtividadeToAttach);
            }
            atividadeListNew = attachedAtividadeListNew;
            projeto.setAtividadeList(atividadeListNew);
            projeto = em.merge(projeto);
            for (Atividade atividadeListOldAtividade : atividadeListOld) {
                if (!atividadeListNew.contains(atividadeListOldAtividade)) {
                    atividadeListOldAtividade.setIdProjeto(null);
                    atividadeListOldAtividade = em.merge(atividadeListOldAtividade);
                }
            }
            for (Atividade atividadeListNewAtividade : atividadeListNew) {
                if (!atividadeListOld.contains(atividadeListNewAtividade)) {
                    Projeto oldIdProjetoOfAtividadeListNewAtividade = atividadeListNewAtividade.getIdProjeto();
                    atividadeListNewAtividade.setIdProjeto(projeto);
                    atividadeListNewAtividade = em.merge(atividadeListNewAtividade);
                    if (oldIdProjetoOfAtividadeListNewAtividade != null && !oldIdProjetoOfAtividadeListNewAtividade.equals(projeto)) {
                        oldIdProjetoOfAtividadeListNewAtividade.getAtividadeList().remove(atividadeListNewAtividade);
                        oldIdProjetoOfAtividadeListNewAtividade = em.merge(oldIdProjetoOfAtividadeListNewAtividade);
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
            List<Atividade> atividadeList = projeto.getAtividadeList();
            for (Atividade atividadeListAtividade : atividadeList) {
                atividadeListAtividade.setIdProjeto(null);
                atividadeListAtividade = em.merge(atividadeListAtividade);
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
