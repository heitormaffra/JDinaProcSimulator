/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.model.dao;

import br.cesjf.model.dao.exceptions.NonexistentEntityException;
import br.cesjf.model.entities.Atividade;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.cesjf.model.entities.Projeto;
import br.cesjf.model.entities.Desenvolvedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author heitormaffra
 */
public class AtividadeDao extends GenericDao {

    /**
     *
     * @param atividade
     * @return
     */
    public boolean create(Atividade atividade) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Projeto idProjeto = atividade.getIdProjeto();
            if (idProjeto != null) {
                idProjeto = em.getReference(idProjeto.getClass(), idProjeto.getIdProjeto());
                atividade.setIdProjeto(idProjeto);
            }
            Desenvolvedor idDesenv = atividade.getIdDesenv();
            if (idDesenv != null) {
                idDesenv = em.getReference(idDesenv.getClass(), idDesenv.getIdDesenv());
                atividade.setIdDesenv(idDesenv);
            }
            em.persist(atividade);
            if (idProjeto != null) {
                idProjeto.getAtividadeCollection().add(atividade);
                idProjeto = em.merge(idProjeto);
            }
            if (idDesenv != null) {
                idDesenv.getAtividadeCollection().add(atividade);
                idDesenv = em.merge(idDesenv);
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

    public void edit(Atividade atividade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Atividade persistentAtividade = em.find(Atividade.class, atividade.getIdAtivd());
            Projeto idProjetoOld = persistentAtividade.getIdProjeto();
            Projeto idProjetoNew = atividade.getIdProjeto();
            Desenvolvedor idDesenvOld = persistentAtividade.getIdDesenv();
            Desenvolvedor idDesenvNew = atividade.getIdDesenv();
            if (idProjetoNew != null) {
                idProjetoNew = em.getReference(idProjetoNew.getClass(), idProjetoNew.getIdProjeto());
                atividade.setIdProjeto(idProjetoNew);
            }
            if (idDesenvNew != null) {
                idDesenvNew = em.getReference(idDesenvNew.getClass(), idDesenvNew.getIdDesenv());
                atividade.setIdDesenv(idDesenvNew);
            }
            atividade = em.merge(atividade);
            if (idProjetoOld != null && !idProjetoOld.equals(idProjetoNew)) {
                idProjetoOld.getAtividadeCollection().remove(atividade);
                idProjetoOld = em.merge(idProjetoOld);
            }
            if (idProjetoNew != null && !idProjetoNew.equals(idProjetoOld)) {
                idProjetoNew.getAtividadeCollection().add(atividade);
                idProjetoNew = em.merge(idProjetoNew);
            }
            if (idDesenvOld != null && !idDesenvOld.equals(idDesenvNew)) {
                idDesenvOld.getAtividadeCollection().remove(atividade);
                idDesenvOld = em.merge(idDesenvOld);
            }
            if (idDesenvNew != null && !idDesenvNew.equals(idDesenvOld)) {
                idDesenvNew.getAtividadeCollection().add(atividade);
                idDesenvNew = em.merge(idDesenvNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividade.getIdAtivd();
                if (findAtividade(id) == null) {
                    throw new NonexistentEntityException("The atividade with id " + id + " no longer exists.");
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
            Atividade atividade;
            try {
                atividade = em.getReference(Atividade.class, id);
                atividade.getIdAtivd();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividade with id " + id + " no longer exists.", enfe);
            }
            Projeto idProjeto = atividade.getIdProjeto();
            if (idProjeto != null) {
                idProjeto.getAtividadeCollection().remove(atividade);
                idProjeto = em.merge(idProjeto);
            }
            Desenvolvedor idDesenv = atividade.getIdDesenv();
            if (idDesenv != null) {
                idDesenv.getAtividadeCollection().remove(atividade);
                idDesenv = em.merge(idDesenv);
            }
            em.remove(atividade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividade> findAtividadeEntities() {
        return findAtividadeEntities(true, -1, -1);
    }

    public List<Atividade> findAtividadeEntities(int maxResults, int firstResult) {
        return findAtividadeEntities(false, maxResults, firstResult);
    }

    private List<Atividade> findAtividadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividade.class));
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

    public Atividade findAtividade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividade.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividade> rt = cq.from(Atividade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
