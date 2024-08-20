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
import spassocasaerp.entities.Fornecedor;

/**
 *
 * @author tioza
 */
public class FornecedorJpaController implements Serializable {

    public FornecedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fornecedor fornecedor) {
        if (fornecedor.getProdutoList() == null) {
            fornecedor.setProdutoList(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Produto> attachedProdutoList = new ArrayList<Produto>();
            for (Produto produtoListProdutoToAttach : fornecedor.getProdutoList()) {
                produtoListProdutoToAttach = em.getReference(produtoListProdutoToAttach.getClass(), produtoListProdutoToAttach.getId());
                attachedProdutoList.add(produtoListProdutoToAttach);
            }
            fornecedor.setProdutoList(attachedProdutoList);
            em.persist(fornecedor);
            for (Produto produtoListProduto : fornecedor.getProdutoList()) {
                Fornecedor oldIdFornecedorOfProdutoListProduto = produtoListProduto.getIdFornecedor();
                produtoListProduto.setIdFornecedor(fornecedor);
                produtoListProduto = em.merge(produtoListProduto);
                if (oldIdFornecedorOfProdutoListProduto != null) {
                    oldIdFornecedorOfProdutoListProduto.getProdutoList().remove(produtoListProduto);
                    oldIdFornecedorOfProdutoListProduto = em.merge(oldIdFornecedorOfProdutoListProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fornecedor fornecedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor persistentFornecedor = em.find(Fornecedor.class, fornecedor.getId());
            List<Produto> produtoListOld = persistentFornecedor.getProdutoList();
            List<Produto> produtoListNew = fornecedor.getProdutoList();
            List<Produto> attachedProdutoListNew = new ArrayList<Produto>();
            for (Produto produtoListNewProdutoToAttach : produtoListNew) {
                produtoListNewProdutoToAttach = em.getReference(produtoListNewProdutoToAttach.getClass(), produtoListNewProdutoToAttach.getId());
                attachedProdutoListNew.add(produtoListNewProdutoToAttach);
            }
            produtoListNew = attachedProdutoListNew;
            fornecedor.setProdutoList(produtoListNew);
            fornecedor = em.merge(fornecedor);
            for (Produto produtoListOldProduto : produtoListOld) {
                if (!produtoListNew.contains(produtoListOldProduto)) {
                    produtoListOldProduto.setIdFornecedor(null);
                    produtoListOldProduto = em.merge(produtoListOldProduto);
                }
            }
            for (Produto produtoListNewProduto : produtoListNew) {
                if (!produtoListOld.contains(produtoListNewProduto)) {
                    Fornecedor oldIdFornecedorOfProdutoListNewProduto = produtoListNewProduto.getIdFornecedor();
                    produtoListNewProduto.setIdFornecedor(fornecedor);
                    produtoListNewProduto = em.merge(produtoListNewProduto);
                    if (oldIdFornecedorOfProdutoListNewProduto != null && !oldIdFornecedorOfProdutoListNewProduto.equals(fornecedor)) {
                        oldIdFornecedorOfProdutoListNewProduto.getProdutoList().remove(produtoListNewProduto);
                        oldIdFornecedorOfProdutoListNewProduto = em.merge(oldIdFornecedorOfProdutoListNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fornecedor.getId();
                if (findFornecedor(id) == null) {
                    throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.");
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
            Fornecedor fornecedor;
            try {
                fornecedor = em.getReference(Fornecedor.class, id);
                fornecedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.", enfe);
            }
            List<Produto> produtoList = fornecedor.getProdutoList();
            for (Produto produtoListProduto : produtoList) {
                produtoListProduto.setIdFornecedor(null);
                produtoListProduto = em.merge(produtoListProduto);
            }
            em.remove(fornecedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fornecedor> findFornecedorEntities() {
        return findFornecedorEntities(true, -1, -1);
    }

    public List<Fornecedor> findFornecedorEntities(int maxResults, int firstResult) {
        return findFornecedorEntities(false, maxResults, firstResult);
    }

    private List<Fornecedor> findFornecedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fornecedor.class));
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

    public Fornecedor findFornecedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornecedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornecedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fornecedor> rt = cq.from(Fornecedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
