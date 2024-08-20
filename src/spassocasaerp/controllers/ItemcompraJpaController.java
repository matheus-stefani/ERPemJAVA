/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spassocasaerp.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import spassocasaerp.controllers.exceptions.NonexistentEntityException;
import spassocasaerp.entities.Compra;
import spassocasaerp.entities.Itemcompra;
import spassocasaerp.entities.Produto;

/**
 *
 * @author tioza
 */
public class ItemcompraJpaController implements Serializable {

    public ItemcompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Itemcompra itemcompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra idCompra = itemcompra.getIdCompra();
            if (idCompra != null) {
                idCompra = em.getReference(idCompra.getClass(), idCompra.getId());
                itemcompra.setIdCompra(idCompra);
            }
            Produto idProduto = itemcompra.getIdProduto();
            if (idProduto != null) {
                idProduto = em.getReference(idProduto.getClass(), idProduto.getId());
                itemcompra.setIdProduto(idProduto);
            }
            em.persist(itemcompra);
            if (idCompra != null) {
                idCompra.getItemcompraList().add(itemcompra);
                idCompra = em.merge(idCompra);
            }
            if (idProduto != null) {
                idProduto.getItemcompraList().add(itemcompra);
                idProduto = em.merge(idProduto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itemcompra itemcompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itemcompra persistentItemcompra = em.find(Itemcompra.class, itemcompra.getId());
            Compra idCompraOld = persistentItemcompra.getIdCompra();
            Compra idCompraNew = itemcompra.getIdCompra();
            Produto idProdutoOld = persistentItemcompra.getIdProduto();
            Produto idProdutoNew = itemcompra.getIdProduto();
            if (idCompraNew != null) {
                idCompraNew = em.getReference(idCompraNew.getClass(), idCompraNew.getId());
                itemcompra.setIdCompra(idCompraNew);
            }
            if (idProdutoNew != null) {
                idProdutoNew = em.getReference(idProdutoNew.getClass(), idProdutoNew.getId());
                itemcompra.setIdProduto(idProdutoNew);
            }
            itemcompra = em.merge(itemcompra);
            if (idCompraOld != null && !idCompraOld.equals(idCompraNew)) {
                idCompraOld.getItemcompraList().remove(itemcompra);
                idCompraOld = em.merge(idCompraOld);
            }
            if (idCompraNew != null && !idCompraNew.equals(idCompraOld)) {
                idCompraNew.getItemcompraList().add(itemcompra);
                idCompraNew = em.merge(idCompraNew);
            }
            if (idProdutoOld != null && !idProdutoOld.equals(idProdutoNew)) {
                idProdutoOld.getItemcompraList().remove(itemcompra);
                idProdutoOld = em.merge(idProdutoOld);
            }
            if (idProdutoNew != null && !idProdutoNew.equals(idProdutoOld)) {
                idProdutoNew.getItemcompraList().add(itemcompra);
                idProdutoNew = em.merge(idProdutoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemcompra.getId();
                if (findItemcompra(id) == null) {
                    throw new NonexistentEntityException("The itemcompra with id " + id + " no longer exists.");
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
            Itemcompra itemcompra;
            try {
                itemcompra = em.getReference(Itemcompra.class, id);
                itemcompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemcompra with id " + id + " no longer exists.", enfe);
            }
            Compra idCompra = itemcompra.getIdCompra();
            if (idCompra != null) {
                idCompra.getItemcompraList().remove(itemcompra);
                idCompra = em.merge(idCompra);
            }
            Produto idProduto = itemcompra.getIdProduto();
            if (idProduto != null) {
                idProduto.getItemcompraList().remove(itemcompra);
                idProduto = em.merge(idProduto);
            }
            em.remove(itemcompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itemcompra> findItemcompraEntities() {
        return findItemcompraEntities(true, -1, -1);
    }

    public List<Itemcompra> findItemcompraEntities(int maxResults, int firstResult) {
        return findItemcompraEntities(false, maxResults, firstResult);
    }

    private List<Itemcompra> findItemcompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itemcompra.class));
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

    public Itemcompra findItemcompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itemcompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemcompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itemcompra> rt = cq.from(Itemcompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
