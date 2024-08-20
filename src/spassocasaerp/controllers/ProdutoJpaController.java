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
import spassocasaerp.entities.Categoriaproduto;
import spassocasaerp.entities.Fornecedor;
import spassocasaerp.entities.Itemvenda;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import spassocasaerp.controllers.exceptions.NonexistentEntityException;
import spassocasaerp.entities.Itemcompra;
import spassocasaerp.entities.Produto;

/**
 *
 * @author tioza
 */
public class ProdutoJpaController implements Serializable {

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produto) {
        if (produto.getItemvendaList() == null) {
            produto.setItemvendaList(new ArrayList<Itemvenda>());
        }
        if (produto.getItemcompraList() == null) {
            produto.setItemcompraList(new ArrayList<Itemcompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoriaproduto idCategoriaProduto = produto.getIdCategoriaProduto();
            if (idCategoriaProduto != null) {
                idCategoriaProduto = em.getReference(idCategoriaProduto.getClass(), idCategoriaProduto.getId());
                produto.setIdCategoriaProduto(idCategoriaProduto);
            }
            Fornecedor idFornecedor = produto.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor = em.getReference(idFornecedor.getClass(), idFornecedor.getId());
                produto.setIdFornecedor(idFornecedor);
            }
            List<Itemvenda> attachedItemvendaList = new ArrayList<Itemvenda>();
            for (Itemvenda itemvendaListItemvendaToAttach : produto.getItemvendaList()) {
                itemvendaListItemvendaToAttach = em.getReference(itemvendaListItemvendaToAttach.getClass(), itemvendaListItemvendaToAttach.getId());
                attachedItemvendaList.add(itemvendaListItemvendaToAttach);
            }
            produto.setItemvendaList(attachedItemvendaList);
            List<Itemcompra> attachedItemcompraList = new ArrayList<Itemcompra>();
            for (Itemcompra itemcompraListItemcompraToAttach : produto.getItemcompraList()) {
                itemcompraListItemcompraToAttach = em.getReference(itemcompraListItemcompraToAttach.getClass(), itemcompraListItemcompraToAttach.getId());
                attachedItemcompraList.add(itemcompraListItemcompraToAttach);
            }
            produto.setItemcompraList(attachedItemcompraList);
            em.persist(produto);
            if (idCategoriaProduto != null) {
                idCategoriaProduto.getProdutoList().add(produto);
                idCategoriaProduto = em.merge(idCategoriaProduto);
            }
            if (idFornecedor != null) {
                idFornecedor.getProdutoList().add(produto);
                idFornecedor = em.merge(idFornecedor);
            }
            for (Itemvenda itemvendaListItemvenda : produto.getItemvendaList()) {
                Produto oldIdProdutoOfItemvendaListItemvenda = itemvendaListItemvenda.getIdProduto();
                itemvendaListItemvenda.setIdProduto(produto);
                itemvendaListItemvenda = em.merge(itemvendaListItemvenda);
                if (oldIdProdutoOfItemvendaListItemvenda != null) {
                    oldIdProdutoOfItemvendaListItemvenda.getItemvendaList().remove(itemvendaListItemvenda);
                    oldIdProdutoOfItemvendaListItemvenda = em.merge(oldIdProdutoOfItemvendaListItemvenda);
                }
            }
            for (Itemcompra itemcompraListItemcompra : produto.getItemcompraList()) {
                Produto oldIdProdutoOfItemcompraListItemcompra = itemcompraListItemcompra.getIdProduto();
                itemcompraListItemcompra.setIdProduto(produto);
                itemcompraListItemcompra = em.merge(itemcompraListItemcompra);
                if (oldIdProdutoOfItemcompraListItemcompra != null) {
                    oldIdProdutoOfItemcompraListItemcompra.getItemcompraList().remove(itemcompraListItemcompra);
                    oldIdProdutoOfItemcompraListItemcompra = em.merge(oldIdProdutoOfItemcompraListItemcompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getId());
            Categoriaproduto idCategoriaProdutoOld = persistentProduto.getIdCategoriaProduto();
            Categoriaproduto idCategoriaProdutoNew = produto.getIdCategoriaProduto();
            Fornecedor idFornecedorOld = persistentProduto.getIdFornecedor();
            Fornecedor idFornecedorNew = produto.getIdFornecedor();
            List<Itemvenda> itemvendaListOld = persistentProduto.getItemvendaList();
            List<Itemvenda> itemvendaListNew = produto.getItemvendaList();
            List<Itemcompra> itemcompraListOld = persistentProduto.getItemcompraList();
            List<Itemcompra> itemcompraListNew = produto.getItemcompraList();
            if (idCategoriaProdutoNew != null) {
                idCategoriaProdutoNew = em.getReference(idCategoriaProdutoNew.getClass(), idCategoriaProdutoNew.getId());
                produto.setIdCategoriaProduto(idCategoriaProdutoNew);
            }
            if (idFornecedorNew != null) {
                idFornecedorNew = em.getReference(idFornecedorNew.getClass(), idFornecedorNew.getId());
                produto.setIdFornecedor(idFornecedorNew);
            }
            List<Itemvenda> attachedItemvendaListNew = new ArrayList<Itemvenda>();
            for (Itemvenda itemvendaListNewItemvendaToAttach : itemvendaListNew) {
                itemvendaListNewItemvendaToAttach = em.getReference(itemvendaListNewItemvendaToAttach.getClass(), itemvendaListNewItemvendaToAttach.getId());
                attachedItemvendaListNew.add(itemvendaListNewItemvendaToAttach);
            }
            itemvendaListNew = attachedItemvendaListNew;
            produto.setItemvendaList(itemvendaListNew);
            List<Itemcompra> attachedItemcompraListNew = new ArrayList<Itemcompra>();
            for (Itemcompra itemcompraListNewItemcompraToAttach : itemcompraListNew) {
                itemcompraListNewItemcompraToAttach = em.getReference(itemcompraListNewItemcompraToAttach.getClass(), itemcompraListNewItemcompraToAttach.getId());
                attachedItemcompraListNew.add(itemcompraListNewItemcompraToAttach);
            }
            itemcompraListNew = attachedItemcompraListNew;
            produto.setItemcompraList(itemcompraListNew);
            produto = em.merge(produto);
            if (idCategoriaProdutoOld != null && !idCategoriaProdutoOld.equals(idCategoriaProdutoNew)) {
                idCategoriaProdutoOld.getProdutoList().remove(produto);
                idCategoriaProdutoOld = em.merge(idCategoriaProdutoOld);
            }
            if (idCategoriaProdutoNew != null && !idCategoriaProdutoNew.equals(idCategoriaProdutoOld)) {
                idCategoriaProdutoNew.getProdutoList().add(produto);
                idCategoriaProdutoNew = em.merge(idCategoriaProdutoNew);
            }
            if (idFornecedorOld != null && !idFornecedorOld.equals(idFornecedorNew)) {
                idFornecedorOld.getProdutoList().remove(produto);
                idFornecedorOld = em.merge(idFornecedorOld);
            }
            if (idFornecedorNew != null && !idFornecedorNew.equals(idFornecedorOld)) {
                idFornecedorNew.getProdutoList().add(produto);
                idFornecedorNew = em.merge(idFornecedorNew);
            }
            for (Itemvenda itemvendaListOldItemvenda : itemvendaListOld) {
                if (!itemvendaListNew.contains(itemvendaListOldItemvenda)) {
                    itemvendaListOldItemvenda.setIdProduto(null);
                    itemvendaListOldItemvenda = em.merge(itemvendaListOldItemvenda);
                }
            }
            for (Itemvenda itemvendaListNewItemvenda : itemvendaListNew) {
                if (!itemvendaListOld.contains(itemvendaListNewItemvenda)) {
                    Produto oldIdProdutoOfItemvendaListNewItemvenda = itemvendaListNewItemvenda.getIdProduto();
                    itemvendaListNewItemvenda.setIdProduto(produto);
                    itemvendaListNewItemvenda = em.merge(itemvendaListNewItemvenda);
                    if (oldIdProdutoOfItemvendaListNewItemvenda != null && !oldIdProdutoOfItemvendaListNewItemvenda.equals(produto)) {
                        oldIdProdutoOfItemvendaListNewItemvenda.getItemvendaList().remove(itemvendaListNewItemvenda);
                        oldIdProdutoOfItemvendaListNewItemvenda = em.merge(oldIdProdutoOfItemvendaListNewItemvenda);
                    }
                }
            }
            for (Itemcompra itemcompraListOldItemcompra : itemcompraListOld) {
                if (!itemcompraListNew.contains(itemcompraListOldItemcompra)) {
                    itemcompraListOldItemcompra.setIdProduto(null);
                    itemcompraListOldItemcompra = em.merge(itemcompraListOldItemcompra);
                }
            }
            for (Itemcompra itemcompraListNewItemcompra : itemcompraListNew) {
                if (!itemcompraListOld.contains(itemcompraListNewItemcompra)) {
                    Produto oldIdProdutoOfItemcompraListNewItemcompra = itemcompraListNewItemcompra.getIdProduto();
                    itemcompraListNewItemcompra.setIdProduto(produto);
                    itemcompraListNewItemcompra = em.merge(itemcompraListNewItemcompra);
                    if (oldIdProdutoOfItemcompraListNewItemcompra != null && !oldIdProdutoOfItemcompraListNewItemcompra.equals(produto)) {
                        oldIdProdutoOfItemcompraListNewItemcompra.getItemcompraList().remove(itemcompraListNewItemcompra);
                        oldIdProdutoOfItemcompraListNewItemcompra = em.merge(oldIdProdutoOfItemcompraListNewItemcompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getId();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
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
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            Categoriaproduto idCategoriaProduto = produto.getIdCategoriaProduto();
            if (idCategoriaProduto != null) {
                idCategoriaProduto.getProdutoList().remove(produto);
                idCategoriaProduto = em.merge(idCategoriaProduto);
            }
            Fornecedor idFornecedor = produto.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor.getProdutoList().remove(produto);
                idFornecedor = em.merge(idFornecedor);
            }
            List<Itemvenda> itemvendaList = produto.getItemvendaList();
            for (Itemvenda itemvendaListItemvenda : itemvendaList) {
                itemvendaListItemvenda.setIdProduto(null);
                itemvendaListItemvenda = em.merge(itemvendaListItemvenda);
            }
            List<Itemcompra> itemcompraList = produto.getItemcompraList();
            for (Itemcompra itemcompraListItemcompra : itemcompraList) {
                itemcompraListItemcompra.setIdProduto(null);
                itemcompraListItemcompra = em.merge(itemcompraListItemcompra);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
