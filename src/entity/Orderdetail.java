/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author john
 */
@Entity
@Table(name = "ORDERDETAIL", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderdetail.findAll", query = "SELECT o FROM Orderdetail o"),
    @NamedQuery(name = "Orderdetail.findByOrderdetailid", query = "SELECT o FROM Orderdetail o WHERE o.orderdetailid = :orderdetailid"),
    @NamedQuery(name = "Orderdetail.findByQuantity", query = "SELECT o FROM Orderdetail o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "Orderdetail.findByPrice", query = "SELECT o FROM Orderdetail o WHERE o.price = :price")})
public class Orderdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERDETAILID", nullable = false)
    private Integer orderdetailid;
    @Basic(optional = false)
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
    @Basic(optional = false)
    @Column(name = "PRICE", nullable = false)
    private double price;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID", nullable = false)
    @ManyToOne(optional = false)
    private Sorder orderid;
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID", nullable = false)
    @ManyToOne(optional = false)
    private Item itemid;

    public Orderdetail() {
    }

    public Orderdetail(Integer orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    public Orderdetail(Integer orderdetailid, int quantity, double price) {
        this.orderdetailid = orderdetailid;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getOrderdetailid() {
        return orderdetailid;
    }

    public void setOrderdetailid(Integer orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Sorder getOrderid() {
        return orderid;
    }

    public void setOrderid(Sorder orderid) {
        this.orderid = orderid;
    }

    public Item getItemid() {
        return itemid;
    }

    public void setItemid(Item itemid) {
        this.itemid = itemid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderdetailid != null ? orderdetailid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderdetail)) {
            return false;
        }
        Orderdetail other = (Orderdetail) object;
        if ((this.orderdetailid == null && other.orderdetailid != null) || (this.orderdetailid != null && !this.orderdetailid.equals(other.orderdetailid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Orderdetail[ orderdetailid=" + orderdetailid + " ]";
    }
    
}
