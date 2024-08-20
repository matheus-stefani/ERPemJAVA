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
import spassocasaerp.entities.Compra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import spassocasaerp.controllers.exceptions.NonexistentEntityException;
import spassocasaerp.entities.Cliente;
import spassocasaerp.entities.Venda;

/**
 *
 * @author tioza
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getCompraList() == null) {
            cliente.setCompraList(new ArrayList<Compra>());
        }
        if (cliente.getVendaList() == null) {
            cliente.setVendaList(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : cliente.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getId());
                attachedCompraList.add(compraListCompraToAttach);
            }
            cliente.setCompraList(attachedCompraList);
            List<Venda> attachedVendaList = new ArrayList<Venda>();
            for (Venda vendaListVendaToAttach : cliente.getVendaList()) {
                vendaListVendaToAttach = em.getReference(vendaListVendaToAttach.getClass(), vendaListVendaToAttach.getId());
                attachedVendaList.add(vendaListVendaToAttach);
            }
            cliente.setVendaList(attachedVendaList);
            em.persist(cliente);
            for (Compra compraListCompra : cliente.getCompraList()) {
                Cliente oldIdClienteOfCompraListCompra = compraListCompra.getIdCliente();
                compraListCompra.setIdCliente(cliente);
                compraListCompra = em.merge(compraListCompra);
                if (oldIdClienteOfCompraListCompra != null) {
                    oldIdClienteOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldIdClienteOfCompraListCompra = em.merge(oldIdClienteOfCompraListCompra);
                }
            }
            for (Venda vendaListVenda : cliente.getVendaList()) {
                Cliente oldIdClienteOfVendaListVenda = vendaListVenda.getIdCliente();
                vendaListVenda.setIdCliente(cliente);
                vendaListVenda = em.merge(vendaListVenda);
                if (oldIdClienteOfVendaListVenda != null) {
                    oldIdClienteOfVendaListVenda.getVendaList().remove(vendaListVenda);
                    oldIdClienteOfVendaListVenda = em.merge(oldIdClienteOfVendaListVenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            List<Compra> compraListOld = persistentCliente.getCompraList();
            List<Compra> compraListNew = cliente.getCompraList();
            List<Venda> vendaListOld = persistentCliente.getVendaList();
            List<Venda> vendaListNew = cliente.getVendaList();
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getId());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            cliente.setCompraList(compraListNew);
            List<Venda> attachedVendaListNew = new ArrayList<Venda>();
            for (Venda vendaListNewVendaToAttach : vendaListNew) {
                vendaListNewVendaToAttach = em.getReference(vendaListNewVendaToAttach.getClass(), vendaListNewVendaToAttach.getId());
                attachedVendaListNew.add(vendaListNewVendaToAttach);
            }
            vendaListNew = attachedVendaListNew;
            cliente.setVendaList(vendaListNew);
            cliente = em.merge(cliente);
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    compraListOldCompra.setIdCliente(null);
                    compraListOldCompra = em.merge(compraListOldCompra);
                }
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Cliente oldIdClienteOfCompraListNewCompra = compraListNewCompra.getIdCliente();
                    compraListNewCompra.setIdCliente(cliente);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldIdClienteOfCompraListNewCompra != null && !oldIdClienteOfCompraListNewCompra.equals(cliente)) {
                        oldIdClienteOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldIdClienteOfCompraListNewCompra = em.merge(oldIdClienteOfCompraListNewCompra);
                    }
                }
            }
            for (Venda vendaListOldVenda : vendaListOld) {
                if (!vendaListNew.contains(vendaListOldVenda)) {
                    vendaListOldVenda.setIdCliente(null);
                    vendaListOldVenda = em.merge(vendaListOldVenda);
                }
            }
            for (Venda vendaListNewVenda : vendaListNew) {
                if (!vendaListOld.contains(vendaListNewVenda)) {
                    Cliente oldIdClienteOfVendaListNewVenda = vendaListNewVenda.getIdCliente();
                    vendaListNewVenda.setIdCliente(cliente);
                    vendaListNewVenda = em.merge(vendaListNewVenda);
                    if (oldIdClienteOfVendaListNewVenda != null && !oldIdClienteOfVendaListNewVenda.equals(cliente)) {
                        oldIdClienteOfVendaListNewVenda.getVendaList().remove(vendaListNewVenda);
                        oldIdClienteOfVendaListNewVenda = em.merge(oldIdClienteOfVendaListNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Compra> compraList = cliente.getCompraList();
            for (Compra compraListCompra : compraList) {
                compraListCompra.setIdCliente(null);
                compraListCompra = em.merge(compraListCompra);
            }
            List<Venda> vendaList = cliente.getVendaList();
            for (Venda vendaListVenda : vendaList) {
                vendaListVenda.setIdCliente(null);
                vendaListVenda = em.merge(vendaListVenda);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
