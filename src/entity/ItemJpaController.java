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
public class ItemJpaController implements Serializable {

    public ItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Item item) {
        if (item.getOrderdetailCollection() == null) {
            item.setOrderdetailCollection(new ArrayList<Orderdetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itemtype itemtypeid = item.getItemtypeid();
            if (itemtypeid != null) {
                itemtypeid = em.getReference(itemtypeid.getClass(), itemtypeid.getItemtypeid());
                item.setItemtypeid(itemtypeid);
            }
            Company companyid = item.getCompanyid();
            if (companyid != null) {
                companyid = em.getReference(companyid.getClass(), companyid.getCompanyid());
                item.setCompanyid(companyid);
            }
            Collection<Orderdetail> attachedOrderdetailCollection = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionOrderdetailToAttach : item.getOrderdetailCollection()) {
                orderdetailCollectionOrderdetailToAttach = em.getReference(orderdetailCollectionOrderdetailToAttach.getClass(), orderdetailCollectionOrderdetailToAttach.getOrderdetailid());
                attachedOrderdetailCollection.add(orderdetailCollectionOrderdetailToAttach);
            }
            item.setOrderdetailCollection(attachedOrderdetailCollection);
            em.persist(item);
            if (itemtypeid != null) {
                itemtypeid.getItemCollection().add(item);
                itemtypeid = em.merge(itemtypeid);
            }
            if (companyid != null) {
                companyid.getItemCollection().add(item);
                companyid = em.merge(companyid);
            }
            for (Orderdetail orderdetailCollectionOrderdetail : item.getOrderdetailCollection()) {
                Item oldItemidOfOrderdetailCollectionOrderdetail = orderdetailCollectionOrderdetail.getItemid();
                orderdetailCollectionOrderdetail.setItemid(item);
                orderdetailCollectionOrderdetail = em.merge(orderdetailCollectionOrderdetail);
                if (oldItemidOfOrderdetailCollectionOrderdetail != null) {
                    oldItemidOfOrderdetailCollectionOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionOrderdetail);
                    oldItemidOfOrderdetailCollectionOrderdetail = em.merge(oldItemidOfOrderdetailCollectionOrderdetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Item item) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item persistentItem = em.find(Item.class, item.getItemid());
            Itemtype itemtypeidOld = persistentItem.getItemtypeid();
            Itemtype itemtypeidNew = item.getItemtypeid();
            Company companyidOld = persistentItem.getCompanyid();
            Company companyidNew = item.getCompanyid();
            Collection<Orderdetail> orderdetailCollectionOld = persistentItem.getOrderdetailCollection();
            Collection<Orderdetail> orderdetailCollectionNew = item.getOrderdetailCollection();
            List<String> illegalOrphanMessages = null;
            for (Orderdetail orderdetailCollectionOldOrderdetail : orderdetailCollectionOld) {
                if (!orderdetailCollectionNew.contains(orderdetailCollectionOldOrderdetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderdetail " + orderdetailCollectionOldOrderdetail + " since its itemid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemtypeidNew != null) {
                itemtypeidNew = em.getReference(itemtypeidNew.getClass(), itemtypeidNew.getItemtypeid());
                item.setItemtypeid(itemtypeidNew);
            }
            if (companyidNew != null) {
                companyidNew = em.getReference(companyidNew.getClass(), companyidNew.getCompanyid());
                item.setCompanyid(companyidNew);
            }
            Collection<Orderdetail> attachedOrderdetailCollectionNew = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionNewOrderdetailToAttach : orderdetailCollectionNew) {
                orderdetailCollectionNewOrderdetailToAttach = em.getReference(orderdetailCollectionNewOrderdetailToAttach.getClass(), orderdetailCollectionNewOrderdetailToAttach.getOrderdetailid());
                attachedOrderdetailCollectionNew.add(orderdetailCollectionNewOrderdetailToAttach);
            }
            orderdetailCollectionNew = attachedOrderdetailCollectionNew;
            item.setOrderdetailCollection(orderdetailCollectionNew);
            item = em.merge(item);
            if (itemtypeidOld != null && !itemtypeidOld.equals(itemtypeidNew)) {
                itemtypeidOld.getItemCollection().remove(item);
                itemtypeidOld = em.merge(itemtypeidOld);
            }
            if (itemtypeidNew != null && !itemtypeidNew.equals(itemtypeidOld)) {
                itemtypeidNew.getItemCollection().add(item);
                itemtypeidNew = em.merge(itemtypeidNew);
            }
            if (companyidOld != null && !companyidOld.equals(companyidNew)) {
                companyidOld.getItemCollection().remove(item);
                companyidOld = em.merge(companyidOld);
            }
            if (companyidNew != null && !companyidNew.equals(companyidOld)) {
                companyidNew.getItemCollection().add(item);
                companyidNew = em.merge(companyidNew);
            }
            for (Orderdetail orderdetailCollectionNewOrderdetail : orderdetailCollectionNew) {
                if (!orderdetailCollectionOld.contains(orderdetailCollectionNewOrderdetail)) {
                    Item oldItemidOfOrderdetailCollectionNewOrderdetail = orderdetailCollectionNewOrderdetail.getItemid();
                    orderdetailCollectionNewOrderdetail.setItemid(item);
                    orderdetailCollectionNewOrderdetail = em.merge(orderdetailCollectionNewOrderdetail);
                    if (oldItemidOfOrderdetailCollectionNewOrderdetail != null && !oldItemidOfOrderdetailCollectionNewOrderdetail.equals(item)) {
                        oldItemidOfOrderdetailCollectionNewOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionNewOrderdetail);
                        oldItemidOfOrderdetailCollectionNewOrderdetail = em.merge(oldItemidOfOrderdetailCollectionNewOrderdetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = item.getItemid();
                if (findItem(id) == null) {
                    throw new NonexistentEntityException("The item with id " + id + " no longer exists.");
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
            Item item;
            try {
                item = em.getReference(Item.class, id);
                item.getItemid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The item with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orderdetail> orderdetailCollectionOrphanCheck = item.getOrderdetailCollection();
            for (Orderdetail orderdetailCollectionOrphanCheckOrderdetail : orderdetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the Orderdetail " + orderdetailCollectionOrphanCheckOrderdetail + " in its orderdetailCollection field has a non-nullable itemid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Itemtype itemtypeid = item.getItemtypeid();
            if (itemtypeid != null) {
                itemtypeid.getItemCollection().remove(item);
                itemtypeid = em.merge(itemtypeid);
            }
            Company companyid = item.getCompanyid();
            if (companyid != null) {
                companyid.getItemCollection().remove(item);
                companyid = em.merge(companyid);
            }
            em.remove(item);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Item> findItemEntities() {
        return findItemEntities(true, -1, -1);
    }

    public List<Item> findItemEntities(int maxResults, int firstResult) {
        return findItemEntities(false, maxResults, firstResult);
    }

    private List<Item> findItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
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

    public Item findItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
