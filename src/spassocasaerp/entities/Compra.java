/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spassocasaerp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tioza
 */
@Entity
@Table(name = "compra")
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findById", query = "SELECT c FROM Compra c WHERE c.id = :id"),
    @NamedQuery(name = "Compra.findByDataCompra", query = "SELECT c FROM Compra c WHERE c.dataCompra = :dataCompra"),
    @NamedQuery(name = "Compra.findByTotal", query = "SELECT c FROM Compra c WHERE c.total = :total")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dataCompra")
    @Temporal(TemporalType.DATE)
    private Date dataCompra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private BigDecimal total;
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    @ManyToOne
    private Cliente idCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompra")
    private List<Itemcompra> itemcompraList;

    public Compra(Integer id, Date dataCompra, BigDecimal total, Cliente idCliente, List<Itemcompra> itemcompraList) {
        this.id = id;
        this.dataCompra = dataCompra;
        this.total = total;
        this.idCliente = idCliente;
        this.itemcompraList = itemcompraList;
    }

    public Compra() {
    }

    public Compra(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
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
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spassocasaerp.entities.Compra[ id=" + id + " ]";
    }
    
}
