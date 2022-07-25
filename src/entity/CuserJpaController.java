/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.IllegalOrphanException;
import entity.exceptions.NonexistentEntityException;
import entity.exceptions.PreexistingEntityException;
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
public class CuserJpaController implements Serializable {

    public CuserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuser cuser) throws PreexistingEntityException, Exception {
        if (cuser.getSorderCollection() == null) {
            cuser.setSorderCollection(new ArrayList<Sorder>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Sorder> attachedSorderCollection = new ArrayList<Sorder>();
            for (Sorder sorderCollectionSorderToAttach : cuser.getSorderCollection()) {
                sorderCollectionSorderToAttach = em.getReference(sorderCollectionSorderToAttach.getClass(), sorderCollectionSorderToAttach.getOrderid());
                attachedSorderCollection.add(sorderCollectionSorderToAttach);
            }
            cuser.setSorderCollection(attachedSorderCollection);
            em.persist(cuser);
            for (Sorder sorderCollectionSorder : cuser.getSorderCollection()) {
                Cuser oldEmailidOfSorderCollectionSorder = sorderCollectionSorder.getEmailid();
                sorderCollectionSorder.setEmailid(cuser);
                sorderCollectionSorder = em.merge(sorderCollectionSorder);
                if (oldEmailidOfSorderCollectionSorder != null) {
                    oldEmailidOfSorderCollectionSorder.getSorderCollection().remove(sorderCollectionSorder);
                    oldEmailidOfSorderCollectionSorder = em.merge(oldEmailidOfSorderCollectionSorder);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuser(cuser.getEmailid()) != null) {
                throw new PreexistingEntityException("Cuser " + cuser + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuser cuser) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuser persistentCuser = em.find(Cuser.class, cuser.getEmailid());
            Collection<Sorder> sorderCollectionOld = persistentCuser.getSorderCollection();
            Collection<Sorder> sorderCollectionNew = cuser.getSorderCollection();
            List<String> illegalOrphanMessages = null;
            for (Sorder sorderCollectionOldSorder : sorderCollectionOld) {
                if (!sorderCollectionNew.contains(sorderCollectionOldSorder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sorder " + sorderCollectionOldSorder + " since its emailid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Sorder> attachedSorderCollectionNew = new ArrayList<Sorder>();
            for (Sorder sorderCollectionNewSorderToAttach : sorderCollectionNew) {
                sorderCollectionNewSorderToAttach = em.getReference(sorderCollectionNewSorderToAttach.getClass(), sorderCollectionNewSorderToAttach.getOrderid());
                attachedSorderCollectionNew.add(sorderCollectionNewSorderToAttach);
            }
            sorderCollectionNew = attachedSorderCollectionNew;
            cuser.setSorderCollection(sorderCollectionNew);
            cuser = em.merge(cuser);
            for (Sorder sorderCollectionNewSorder : sorderCollectionNew) {
                if (!sorderCollectionOld.contains(sorderCollectionNewSorder)) {
                    Cuser oldEmailidOfSorderCollectionNewSorder = sorderCollectionNewSorder.getEmailid();
                    sorderCollectionNewSorder.setEmailid(cuser);
                    sorderCollectionNewSorder = em.merge(sorderCollectionNewSorder);
                    if (oldEmailidOfSorderCollectionNewSorder != null && !oldEmailidOfSorderCollectionNewSorder.equals(cuser)) {
                        oldEmailidOfSorderCollectionNewSorder.getSorderCollection().remove(sorderCollectionNewSorder);
                        oldEmailidOfSorderCollectionNewSorder = em.merge(oldEmailidOfSorderCollectionNewSorder);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cuser.getEmailid();
                if (findCuser(id) == null) {
                    throw new NonexistentEntityException("The cuser with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuser cuser;
            try {
                cuser = em.getReference(Cuser.class, id);
                cuser.getEmailid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuser with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sorder> sorderCollectionOrphanCheck = cuser.getSorderCollection();
            for (Sorder sorderCollectionOrphanCheckSorder : sorderCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuser (" + cuser + ") cannot be destroyed since the Sorder " + sorderCollectionOrphanCheckSorder + " in its sorderCollection field has a non-nullable emailid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cuser);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuser> findCuserEntities() {
        return findCuserEntities(true, -1, -1);
    }

    public List<Cuser> findCuserEntities(int maxResults, int firstResult) {
        return findCuserEntities(false, maxResults, firstResult);
    }

    private List<Cuser> findCuserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuser.class));
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

    public Cuser findCuser(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuser.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuser> rt = cq.from(Cuser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
