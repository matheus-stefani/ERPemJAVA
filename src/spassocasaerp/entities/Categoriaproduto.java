/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spassocasaerp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author tioza
 */
@Entity
@Table(name = "categoriaproduto")
@NamedQueries({
    @NamedQuery(name = "Categoriaproduto.findAll", query = "SELECT c FROM Categoriaproduto c"),
    @NamedQuery(name = "Categoriaproduto.findById", query = "SELECT c FROM Categoriaproduto c WHERE c.id = :id"),
    @NamedQuery(name = "Categoriaproduto.findByNome", query = "SELECT c FROM Categoriaproduto c WHERE c.nome = :nome")})
public class Categoriaproduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategoriaProduto")
    private List<Produto> produtoList;

    public Categoriaproduto() {
    }

    public Categoriaproduto(String nome) {
        
        this.nome = nome;
        this.produtoList = new ArrayList<Produto>();
    }

    public Categoriaproduto(Integer id) {
        this.id = id;
    }

    public Categoriaproduto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
        this.produtoList = new ArrayList<Produto>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriaproduto)) {
            return false;
        }
        Categoriaproduto other = (Categoriaproduto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spassocasaerp.entities.Categoriaproduto[ id=" + id + " ]";
    }
    
}
