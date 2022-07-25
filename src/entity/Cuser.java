/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author john
 */
@Entity
@Table(name = "CUSER", catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuser.findAll", query = "SELECT c FROM Cuser c"),
    @NamedQuery(name = "Cuser.findByEmailid", query = "SELECT c FROM Cuser c WHERE c.emailid = :emailid"),
    @NamedQuery(name = "Cuser.findByPassword", query = "SELECT c FROM Cuser c WHERE c.password = :password"),
    @NamedQuery(name = "Cuser.findByUsername", query = "SELECT c FROM Cuser c WHERE c.username = :username"),
    @NamedQuery(name = "Cuser.findByAddress", query = "SELECT c FROM Cuser c WHERE c.address = :address"),
    @NamedQuery(name = "Cuser.findByCity", query = "SELECT c FROM Cuser c WHERE c.city = :city"),
    @NamedQuery(name = "Cuser.findByPhone", query = "SELECT c FROM Cuser c WHERE c.phone = :phone"),
    @NamedQuery(name = "Cuser.findByMobile", query = "SELECT c FROM Cuser c WHERE c.mobile = :mobile")})
public class Cuser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EMAILID", nullable = false, length = 50)
    private String emailid;
    @Basic(optional = false)
    @Column(name = "PASSWORD", nullable = false, length = 50)
    private String password;
    @Column(name = "USERNAME", length = 20)
    private String username;
    @Column(name = "ADDRESS", length = 100)
    private String address;
    @Column(name = "CITY", length = 20)
    private String city;
    @Column(name = "PHONE", length = 20)
    private String phone;
    @Column(name = "MOBILE", length = 20)
    private String mobile;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emailid")
    private Collection<Sorder> sorderCollection;

    public Cuser() {
    }

    public Cuser(String emailid) {
        this.emailid = emailid;
    }

    public Cuser(String emailid, String password) {
        this.emailid = emailid;
        this.password = password;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @XmlTransient
    public Collection<Sorder> getSorderCollection() {
        return sorderCollection;
    }

    public void setSorderCollection(Collection<Sorder> sorderCollection) {
        this.sorderCollection = sorderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailid != null ? emailid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuser)) {
            return false;
        }
        Cuser other = (Cuser) object;
        if ((this.emailid == null && other.emailid != null) || (this.emailid != null && !this.emailid.equals(other.emailid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cuser[ emailid=" + emailid + " ]";
    }
    
}
