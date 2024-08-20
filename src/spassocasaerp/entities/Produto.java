/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spassocasaerp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import spassocasaerp.controllers.CategoriaprodutoJpaController;
import spassocasaerp.controllers.FornecedorJpaController;

/**
 *
 * @author tioza
 */
@Entity
@Table(name = "produto")
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findById", query = "SELECT p FROM Produto p WHERE p.id = :id"),
    @NamedQuery(name = "Produto.findByNome", query = "SELECT p FROM Produto p WHERE p.nome = :nome"),
    @NamedQuery(name = "Produto.findByPreco", query = "SELECT p FROM Produto p WHERE p.preco = :preco"),
    @NamedQuery(name = "Produto.findByEstoque", query = "SELECT p FROM Produto p WHERE p.estoque = :estoque")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Lob
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preco")
    private BigDecimal preco;
    @Basic(optional = false)
    @Column(name = "estoque")
    private int estoque;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProduto")
    private List<Itemvenda> itemvendaList;
    @JoinColumn(name = "idCategoriaProduto", referencedColumnName = "id")
    @ManyToOne
    private Categoriaproduto idCategoriaProduto;
    @JoinColumn(name = "idFornecedor", referencedColumnName = "id")
    @ManyToOne
    private Fornecedor idFornecedor;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "idProduto")
    private List<Itemcompra> itemcompraList;

    public Produto( String nome, String descricao, BigDecimal preco, int estoque, 
            Categoriaproduto idCategoriaProduto, Fornecedor idFornecedor) {
        
        
        
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.itemvendaList = new ArrayList<Itemvenda>() {};
        
       /*
            Categoriaproduto pegar = categoriaC.findCategoriaproduto( idCategoriaProduto);    
            
           
            if(pegar == null) throw new ClassFormatError("Id do categoria não existe");
         */   
        this.idCategoriaProduto =  idCategoriaProduto;
      
      /*
        Fornecedor pegar2 = fornecedorC.findFornecedor( idFornecedor);     
        
        if(pegar2==null) throw new ClassFormatError("Id do fornecedor não existe");
        */
        this.idFornecedor =  idFornecedor;
        
        
        
        
        this.itemcompraList = new ArrayList<Itemcompra>() {};
    }

    public Produto() {
    }

    public Produto(Integer id,  String nome, String descricao, BigDecimal preco, 
            int estoque, Categoriaproduto idCategoriaProduto, Fornecedor idFornecedor) {
        this.id = id;
        
        
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.itemvendaList = new ArrayList<Itemvenda>() {};
        this.idCategoriaProduto = idCategoriaProduto;
        this.idFornecedor = idFornecedor;
        this.itemcompraList = new ArrayList<Itemcompra>() {};
    }
    

    public Produto(Integer id, String nome, int estoque) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public List<Itemvenda> getItemvendaList() {
        return itemvendaList;
    }

    public void setItemvendaList(List<Itemvenda> itemvendaList) {
        this.itemvendaList = itemvendaList;
    }

    public Categoriaproduto getIdCategoriaProduto() {
        return idCategoriaProduto;
    }

    public void setIdCategoriaProduto(Categoriaproduto idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public List<Itemcompra> getItemcompraList() {
        return itemcompraList;
    }

    public void setItemcompraList(List<Itemcompra> itemcompraList) {
        this.itemcompraList = itemcompraList;
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
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spassocasaerp.entities.Produto[ id=" + id + " ]";
    }
    
}
