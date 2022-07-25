/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.IllegalOrphanException;
import entity.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author john
 */
public class SorderJpaController implements Serializable {

    public SorderJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sorder sorder) {
        if (sorder.getOrderdetailCollection() == null) {
            sorder.setOrderdetailCollection(new ArrayList<Orderdetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuser emailid = sorder.getEmailid();
            if (emailid != null) {
                emailid = em.getReference(emailid.getClass(), emailid.getEmailid());
                sorder.setEmailid(emailid);
            }
            Collection<Orderdetail> attachedOrderdetailCollection = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionOrderdetailToAttach : sorder.getOrderdetailCollection()) {
                orderdetailCollectionOrderdetailToAttach = em.getReference(orderdetailCollectionOrderdetailToAttach.getClass(), orderdetailCollectionOrderdetailToAttach.getOrderdetailid());
                attachedOrderdetailCollection.add(orderdetailCollectionOrderdetailToAttach);
            }
            sorder.setOrderdetailCollection(attachedOrderdetailCollection);
            em.persist(sorder);
            if (emailid != null) {
                emailid.getSorderCollection().add(sorder);
                emailid = em.merge(emailid);
            }
            for (Orderdetail orderdetailCollectionOrderdetail : sorder.getOrderdetailCollection()) {
                Sorder oldOrderidOfOrderdetailCollectionOrderdetail = orderdetailCollectionOrderdetail.getOrderid();
                orderdetailCollectionOrderdetail.setOrderid(sorder);
                orderdetailCollectionOrderdetail = em.merge(orderdetailCollectionOrderdetail);
                if (oldOrderidOfOrderdetailCollectionOrderdetail != null) {
                    oldOrderidOfOrderdetailCollectionOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionOrderdetail);
                    oldOrderidOfOrderdetailCollectionOrderdetail = em.merge(oldOrderidOfOrderdetailCollectionOrderdetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sorder sorder) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sorder persistentSorder = em.find(Sorder.class, sorder.getOrderid());
            Cuser emailidOld = persistentSorder.getEmailid();
            Cuser emailidNew = sorder.getEmailid();
            Collection<Orderdetail> orderdetailCollectionOld = persistentSorder.getOrderdetailCollection();
            Collection<Orderdetail> orderdetailCollectionNew = sorder.getOrderdetailCollection();
            List<String> illegalOrphanMessages = null;
            for (Orderdetail orderdetailCollectionOldOrderdetail : orderdetailCollectionOld) {
                if (!orderdetailCollectionNew.contains(orderdetailCollectionOldOrderdetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderdetail " + orderdetailCollectionOldOrderdetail + " since its orderid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (emailidNew != null) {
                emailidNew = em.getReference(emailidNew.getClass(), emailidNew.getEmailid());
                sorder.setEmailid(emailidNew);
            }
            Collection<Orderdetail> attachedOrderdetailCollectionNew = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionNewOrderdetailToAttach : orderdetailCollectionNew) {
                orderdetailCollectionNewOrderdetailToAttach = em.getReference(orderdetailCollectionNewOrderdetailToAttach.getClass(), orderdetailCollectionNewOrderdetailToAttach.getOrderdetailid());
                attachedOrderdetailCollectionNew.add(orderdetailCollectionNewOrderdetailToAttach);
            }
            orderdetailCollectionNew = attachedOrderdetailCollectionNew;
            sorder.setOrderdetailCollection(orderdetailCollectionNew);
            sorder = em.merge(sorder);
            if (emailidOld != null && !emailidOld.equals(emailidNew)) {
                emailidOld.getSorderCollection().remove(sorder);
                emailidOld = em.merge(emailidOld);
            }
            if (emailidNew != null && !emailidNew.equals(emailidOld)) {
                emailidNew.getSorderCollection().add(sorder);
                emailidNew = em.merge(emailidNew);
            }
            for (Orderdetail orderdetailCollectionNewOrderdetail : orderdetailCollectionNew) {
                if (!orderdetailCollectionOld.contains(orderdetailCollectionNewOrderdetail)) {
                    Sorder oldOrderidOfOrderdetailCollectionNewOrderdetail = orderdetailCollectionNewOrderdetail.getOrderid();
                    orderdetailCollectionNewOrderdetail.setOrderid(sorder);
                    orderdetailCollectionNewOrderdetail = em.merge(orderdetailCollectionNewOrderdetail);
                    if (oldOrderidOfOrderdetailCollectionNewOrderdetail != null && !oldOrderidOfOrderdetailCollectionNewOrderdetail.equals(sorder)) {
                        oldOrderidOfOrderdetailCollectionNewOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionNewOrderdetail);
                        oldOrderidOfOrderdetailCollectionNewOrderdetail = em.merge(oldOrderidOfOrderdetailCollectionNewOrderdetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sorder.getOrderid();
                if (findSorder(id) == null) {
                    throw new NonexistentEntityException("The sorder with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sorder sorder;
            try {
                sorder = em.getReference(Sorder.class, id);
                sorder.getOrderid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sorder with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orderdetail> orderdetailCollectionOrphanCheck = sorder.getOrderdetailCollection();
            for (Orderdetail orderdetailCollectionOrphanCheckOrderdetail : orderdetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sorder (" + sorder + ") cannot be destroyed since the Orderdetail " + orderdetailCollectionOrphanCheckOrderdetail + " in its orderdetailCollection field has a non-nullable orderid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cuser emailid = sorder.getEmailid();
            if (emailid != null) {
                emailid.getSorderCollection().remove(sorder);
                emailid = em.merge(emailid);
            }
            em.remove(sorder);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sorder> findSorderEntities() {
        return findSorderEntities(true, -1, -1);
    }

    public List<Sorder> findSorderEntities(int maxResults, int firstResult) {
        return findSorderEntities(false, maxResults, firstResult);
    }

    private List<Sorder> findSorderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sorder.class));
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

    public Sorder findSorder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sorder.class, id);
        } finally {
            em.close();
        }
    }

    public int getSorderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sorder> rt = cq.from(Sorder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
