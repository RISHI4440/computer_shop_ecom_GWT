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
public class ItemtypeJpaController implements Serializable {

    public ItemtypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Itemtype itemtype) {
        if (itemtype.getItemCollection() == null) {
            itemtype.setItemCollection(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Item> attachedItemCollection = new ArrayList<Item>();
            for (Item itemCollectionItemToAttach : itemtype.getItemCollection()) {
                itemCollectionItemToAttach = em.getReference(itemCollectionItemToAttach.getClass(), itemCollectionItemToAttach.getItemid());
                attachedItemCollection.add(itemCollectionItemToAttach);
            }
            itemtype.setItemCollection(attachedItemCollection);
            em.persist(itemtype);
            for (Item itemCollectionItem : itemtype.getItemCollection()) {
                Itemtype oldItemtypeidOfItemCollectionItem = itemCollectionItem.getItemtypeid();
                itemCollectionItem.setItemtypeid(itemtype);
                itemCollectionItem = em.merge(itemCollectionItem);
                if (oldItemtypeidOfItemCollectionItem != null) {
                    oldItemtypeidOfItemCollectionItem.getItemCollection().remove(itemCollectionItem);
                    oldItemtypeidOfItemCollectionItem = em.merge(oldItemtypeidOfItemCollectionItem);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itemtype itemtype) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itemtype persistentItemtype = em.find(Itemtype.class, itemtype.getItemtypeid());
            Collection<Item> itemCollectionOld = persistentItemtype.getItemCollection();
            Collection<Item> itemCollectionNew = itemtype.getItemCollection();
            List<String> illegalOrphanMessages = null;
            for (Item itemCollectionOldItem : itemCollectionOld) {
                if (!itemCollectionNew.contains(itemCollectionOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemCollectionOldItem + " since its itemtypeid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Item> attachedItemCollectionNew = new ArrayList<Item>();
            for (Item itemCollectionNewItemToAttach : itemCollectionNew) {
                itemCollectionNewItemToAttach = em.getReference(itemCollectionNewItemToAttach.getClass(), itemCollectionNewItemToAttach.getItemid());
                attachedItemCollectionNew.add(itemCollectionNewItemToAttach);
            }
            itemCollectionNew = attachedItemCollectionNew;
            itemtype.setItemCollection(itemCollectionNew);
            itemtype = em.merge(itemtype);
            for (Item itemCollectionNewItem : itemCollectionNew) {
                if (!itemCollectionOld.contains(itemCollectionNewItem)) {
                    Itemtype oldItemtypeidOfItemCollectionNewItem = itemCollectionNewItem.getItemtypeid();
                    itemCollectionNewItem.setItemtypeid(itemtype);
                    itemCollectionNewItem = em.merge(itemCollectionNewItem);
                    if (oldItemtypeidOfItemCollectionNewItem != null && !oldItemtypeidOfItemCollectionNewItem.equals(itemtype)) {
                        oldItemtypeidOfItemCollectionNewItem.getItemCollection().remove(itemCollectionNewItem);
                        oldItemtypeidOfItemCollectionNewItem = em.merge(oldItemtypeidOfItemCollectionNewItem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemtype.getItemtypeid();
                if (findItemtype(id) == null) {
                    throw new NonexistentEntityException("The itemtype with id " + id + " no longer exists.");
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
            Itemtype itemtype;
            try {
                itemtype = em.getReference(Itemtype.class, id);
                itemtype.getItemtypeid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemtype with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Item> itemCollectionOrphanCheck = itemtype.getItemCollection();
            for (Item itemCollectionOrphanCheckItem : itemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Itemtype (" + itemtype + ") cannot be destroyed since the Item " + itemCollectionOrphanCheckItem + " in its itemCollection field has a non-nullable itemtypeid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(itemtype);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itemtype> findItemtypeEntities() {
        return findItemtypeEntities(true, -1, -1);
    }

    public List<Itemtype> findItemtypeEntities(int maxResults, int firstResult) {
        return findItemtypeEntities(false, maxResults, firstResult);
    }

    private List<Itemtype> findItemtypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itemtype.class));
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

    public Itemtype findItemtype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itemtype.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemtypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itemtype> rt = cq.from(Itemtype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
