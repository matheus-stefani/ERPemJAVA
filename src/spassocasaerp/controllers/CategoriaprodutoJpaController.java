/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spassocasaerp.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import spassocasaerp.entities.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import spassocasaerp.controllers.exceptions.NonexistentEntityException;
import spassocasaerp.entities.Categoriaproduto;

/**
 *
 * @author tioza
 */
public class CategoriaprodutoJpaController implements Serializable {

    public CategoriaprodutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoriaproduto categoriaproduto) {
        if (categoriaproduto.getProdutoList() == null) {
            categoriaproduto.setProdutoList(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Produto> attachedProdutoList = new ArrayList<Produto>();
            for (Produto produtoListProdutoToAttach : categoriaproduto.getProdutoList()) {
                produtoListProdutoToAttach = em.getReference(produtoListProdutoToAttach.getClass(), produtoListProdutoToAttach.getId());
                attachedProdutoList.add(produtoListProdutoToAttach);
            }
            categoriaproduto.setProdutoList(attachedProdutoList);
            em.persist(categoriaproduto);
            for (Produto produtoListProduto : categoriaproduto.getProdutoList()) {
                Categoriaproduto oldIdCategoriaProdutoOfProdutoListProduto = produtoListProduto.getIdCategoriaProduto();
                produtoListProduto.setIdCategoriaProduto(categoriaproduto);
                produtoListProduto = em.merge(produtoListProduto);
                if (oldIdCategoriaProdutoOfProdutoListProduto != null) {
                    oldIdCategoriaProdutoOfProdutoListProduto.getProdutoList().remove(produtoListProduto);
                    oldIdCategoriaProdutoOfProdutoListProduto = em.merge(oldIdCategoriaProdutoOfProdutoListProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoriaproduto categoriaproduto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriaproduto persistentCategoriaproduto = em.find(Categoriaproduto.class, categoriaproduto.getId());
            List<Produto> produtoListOld = persistentCategoriaproduto.getProdutoList();
            List<Produto> produtoListNew = categoriaproduto.getProdutoList();
            List<Produto> attachedProdutoListNew = new ArrayList<Produto>();
            for (Produto produtoListNewProdutoToAttach : produtoListNew) {
                produtoListNewProdutoToAttach = em.getReference(produtoListNewProdutoToAttach.getClass(), produtoListNewProdutoToAttach.getId());
                attachedProdutoListNew.add(produtoListNewProdutoToAttach);
            }
            produtoListNew = attachedProdutoListNew;
            categoriaproduto.setProdutoList(produtoListNew);
            categoriaproduto = em.merge(categoriaproduto);
            for (Produto produtoListOldProduto : produtoListOld) {
                if (!produtoListNew.contains(produtoListOldProduto)) {
                    produtoListOldProduto.setIdCategoriaProduto(null);
                    produtoListOldProduto = em.merge(produtoListOldProduto);
                }
            }
            for (Produto produtoListNewProduto : produtoListNew) {
                if (!produtoListOld.contains(produtoListNewProduto)) {
                    Categoriaproduto oldIdCategoriaProdutoOfProdutoListNewProduto = produtoListNewProduto.getIdCategoriaProduto();
                    produtoListNewProduto.setIdCategoriaProduto(categoriaproduto);
                    produtoListNewProduto = em.merge(produtoListNewProduto);
                    if (oldIdCategoriaProdutoOfProdutoListNewProduto != null && !oldIdCategoriaProdutoOfProdutoListNewProduto.equals(categoriaproduto)) {
                        oldIdCategoriaProdutoOfProdutoListNewProduto.getProdutoList().remove(produtoListNewProduto);
                        oldIdCategoriaProdutoOfProdutoListNewProduto = em.merge(oldIdCategoriaProdutoOfProdutoListNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriaproduto.getId();
                if (findCategoriaproduto(id) == null) {
                    throw new NonexistentEntityException("The categoriaproduto with id " + id + " no longer exists.");
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
            Categoriaproduto categoriaproduto;
            try {
                categoriaproduto = em.getReference(Categoriaproduto.class, id);
                categoriaproduto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaproduto with id " + id + " no longer exists.", enfe);
            }
            List<Produto> produtoList = categoriaproduto.getProdutoList();
            for (Produto produtoListProduto : produtoList) {
                produtoListProduto.setIdCategoriaProduto(null);
                produtoListProduto = em.merge(produtoListProduto);
            }
            em.remove(categoriaproduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoriaproduto> findCategoriaprodutoEntities() {
        return findCategoriaprodutoEntities(true, -1, -1);
    }

    public List<Categoriaproduto> findCategoriaprodutoEntities(int maxResults, int firstResult) {
        return findCategoriaprodutoEntities(false, maxResults, firstResult);
    }

    private List<Categoriaproduto> findCategoriaprodutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoriaproduto.class));
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

    public Categoriaproduto findCategoriaproduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoriaproduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaprodutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoriaproduto> rt = cq.from(Categoriaproduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
