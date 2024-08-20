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
import spassocasaerp.entities.Cliente;
import spassocasaerp.entities.Itemcompra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import spassocasaerp.controllers.exceptions.NonexistentEntityException;
import spassocasaerp.entities.Compra;

/**
 *
 * @author tioza
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) {
        if (compra.getItemcompraList() == null) {
            compra.setItemcompraList(new ArrayList<Itemcompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = compra.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getId());
                compra.setIdCliente(idCliente);
            }
            List<Itemcompra> attachedItemcompraList = new ArrayList<Itemcompra>();
            for (Itemcompra itemcompraListItemcompraToAttach : compra.getItemcompraList()) {
                itemcompraListItemcompraToAttach = em.getReference(itemcompraListItemcompraToAttach.getClass(), itemcompraListItemcompraToAttach.getId());
                attachedItemcompraList.add(itemcompraListItemcompraToAttach);
            }
            compra.setItemcompraList(attachedItemcompraList);
            em.persist(compra);
            if (idCliente != null) {
                idCliente.getCompraList().add(compra);
                idCliente = em.merge(idCliente);
            }
            for (Itemcompra itemcompraListItemcompra : compra.getItemcompraList()) {
                Compra oldIdCompraOfItemcompraListItemcompra = itemcompraListItemcompra.getIdCompra();
                itemcompraListItemcompra.setIdCompra(compra);
                itemcompraListItemcompra = em.merge(itemcompraListItemcompra);
                if (oldIdCompraOfItemcompraListItemcompra != null) {
                    oldIdCompraOfItemcompraListItemcompra.getItemcompraList().remove(itemcompraListItemcompra);
                    oldIdCompraOfItemcompraListItemcompra = em.merge(oldIdCompraOfItemcompraListItemcompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getId());
            Cliente idClienteOld = persistentCompra.getIdCliente();
            Cliente idClienteNew = compra.getIdCliente();
            List<Itemcompra> itemcompraListOld = persistentCompra.getItemcompraList();
            List<Itemcompra> itemcompraListNew = compra.getItemcompraList();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getId());
                compra.setIdCliente(idClienteNew);
            }
            List<Itemcompra> attachedItemcompraListNew = new ArrayList<Itemcompra>();
            for (Itemcompra itemcompraListNewItemcompraToAttach : itemcompraListNew) {
                itemcompraListNewItemcompraToAttach = em.getReference(itemcompraListNewItemcompraToAttach.getClass(), itemcompraListNewItemcompraToAttach.getId());
                attachedItemcompraListNew.add(itemcompraListNewItemcompraToAttach);
            }
            itemcompraListNew = attachedItemcompraListNew;
            compra.setItemcompraList(itemcompraListNew);
            compra = em.merge(compra);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getCompraList().remove(compra);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getCompraList().add(compra);
                idClienteNew = em.merge(idClienteNew);
            }
            for (Itemcompra itemcompraListOldItemcompra : itemcompraListOld) {
                if (!itemcompraListNew.contains(itemcompraListOldItemcompra)) {
                    itemcompraListOldItemcompra.setIdCompra(null);
                    itemcompraListOldItemcompra = em.merge(itemcompraListOldItemcompra);
                }
            }
            for (Itemcompra itemcompraListNewItemcompra : itemcompraListNew) {
                if (!itemcompraListOld.contains(itemcompraListNewItemcompra)) {
                    Compra oldIdCompraOfItemcompraListNewItemcompra = itemcompraListNewItemcompra.getIdCompra();
                    itemcompraListNewItemcompra.setIdCompra(compra);
                    itemcompraListNewItemcompra = em.merge(itemcompraListNewItemcompra);
                    if (oldIdCompraOfItemcompraListNewItemcompra != null && !oldIdCompraOfItemcompraListNewItemcompra.equals(compra)) {
                        oldIdCompraOfItemcompraListNewItemcompra.getItemcompraList().remove(itemcompraListNewItemcompra);
                        oldIdCompraOfItemcompraListNewItemcompra = em.merge(oldIdCompraOfItemcompraListNewItemcompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getId();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            Cliente idCliente = compra.getIdCliente();
            if (idCliente != null) {
                idCliente.getCompraList().remove(compra);
                idCliente = em.merge(idCliente);
            }
            List<Itemcompra> itemcompraList = compra.getItemcompraList();
            for (Itemcompra itemcompraListItemcompra : itemcompraList) {
                itemcompraListItemcompra.setIdCompra(null);
                itemcompraListItemcompra = em.merge(itemcompraListItemcompra);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
