/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author john
 */
@Entity
@Table(name = "SORDER", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sorder.findAll", query = "SELECT s FROM Sorder s"),
    @NamedQuery(name = "Sorder.findByOrderid", query = "SELECT s FROM Sorder s WHERE s.orderid = :orderid"),
    @NamedQuery(name = "Sorder.findByOrderdate", query = "SELECT s FROM Sorder s WHERE s.orderdate = :orderdate"),
    @NamedQuery(name = "Sorder.findByTotal", query = "SELECT s FROM Sorder s WHERE s.total = :total"),
    @NamedQuery(name = "Sorder.findByStatus", query = "SELECT s FROM Sorder s WHERE s.status = :status")})
public class Sorder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERID", nullable = false)
    private Integer orderid;
    @Basic(optional = false)
    @Column(name = "ORDERDATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderdate;
    @Basic(optional = false)
    @Column(name = "TOTAL", nullable = false)
    private double total;
    @Basic(optional = false)
    @Column(name = "STATUS", nullable = false)
    private int status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderid")
    private Collection<Orderdetail> orderdetailCollection;
    @JoinColumn(name = "EMAILID", referencedColumnName = "EMAILID", nullable = false)
    @ManyToOne(optional = false)
    private Cuser emailid;

    public Sorder() {
    }

    public Sorder(Integer orderid) {
        this.orderid = orderid;
    }

    public Sorder(Integer orderid, Date orderdate, double total, int status) {
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.total = total;
        this.status = status;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Orderdetail> getOrderdetailCollection() {
        return orderdetailCollection;
    }

    public void setOrderdetailCollection(Collection<Orderdetail> orderdetailCollection) {
        this.orderdetailCollection = orderdetailCollection;
    }

    public Cuser getEmailid() {
        return emailid;
    }

    public void setEmailid(Cuser emailid) {
        this.emailid = emailid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sorder)) {
            return false;
        }
        Sorder other = (Sorder) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sorder[ orderid=" + orderid + " ]";
    }
    
}
