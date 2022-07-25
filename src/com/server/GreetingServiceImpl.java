package com.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;




import com.client.GreetingService;
import com.eneuron.Manager;
import com.shared.CompanyBean;
import com.shared.CuserBean;
import com.shared.FieldVerifier;
import com.shared.ItemBean;
import com.shared.ItemtypeBean;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import entity.Company;
import entity.Cuser;
import entity.CuserJpaController;
import entity.Item;
import entity.Itemtype;
import entity.ItemtypeJpaController;
import entity.Orderdetail;
import entity.OrderdetailJpaController;
import entity.Sorder;
import entity.SorderJpaController;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public List<ItemtypeBean> listItemtypeServer() {
		
		List<ItemtypeBean> list = new ArrayList<ItemtypeBean>();
		List<Itemtype> listItemType = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			ItemtypeJpaController controller = new ItemtypeJpaController(emf);
			
			//listCompany = controller.findCompanyEntities(20, id);
			listItemType = controller.findItemtypeEntities();
			
			
			for (Iterator<Itemtype> iterator = listItemType.iterator(); iterator.hasNext();) {
				Itemtype entity = (Itemtype) iterator.next();
				
				ItemtypeBean bean = new ItemtypeBean();
				bean.setItemtypeid(entity.getItemtypeid());
				bean.setItemtypename(entity.getItemtypename());
				
				List<Item> listItem  = (List<Item>) entity.getItemCollection();
				List<ItemBean> listItemBean = new ArrayList<ItemBean>();
				
				for(int i=0;i<listItem.size();i++) {
					
					Item item = listItem.get(i);
					ItemBean itemBean = new ItemBean();
					
					Company company = item.getCompanyid();
					CompanyBean companyBean = new CompanyBean();
					
					companyBean.setCompanyid(company.getCompanyid());
					companyBean.setCompanyname(company.getCompanyname());
					itemBean.setCompanyid(companyBean);
					
					itemBean.setItemid(item.getItemid());
					itemBean.setItemname(item.getItemname());
					itemBean.setLongdesc(item.getLongdesc());
					itemBean.setModelno(item.getModelno());
					itemBean.setPrice(item.getPrice());
					itemBean.setShortdesc(item.getShortdesc());
										
					//finally
					listItemBean.add(itemBean);
					
				}
				bean.setItemCollection(listItemBean);
				
				//System.out.println("1");
				
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
		
			Manager m = new Manager();
			if(m.validate()<=0) {
				
				return null;
			}
			
		}
		
		return list;
	}

	@Override
	public String[] saveLoginServer(CuserBean bean) {
		
		String status[]={"",""};
		
		Cuser entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			CuserJpaController controller = new CuserJpaController(emf);
			
				entity = new Cuser();
				entity.setAddress(bean.getAddress());
				entity.setCity(bean.getCity());
				entity.setEmailid(bean.getEmailid());
				entity.setMobile(bean.getMobile());
				entity.setPassword(bean.getPassword());
				entity.setPhone(bean.getPhone());
				entity.setUsername(bean.getUsername());
				
				
								
				controller.create(entity);
							
					
			status[0] = "true";
			status[1] = ""+entity.getEmailid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public String[] loginServer(CuserBean cuserBean) {
		
		EntityManager em =null;
		Cuser entity = null;
		String status[]={"",""};
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			em = emf.createEntityManager();
			
			Query query = em.createQuery("SELECT c FROM Cuser c WHERE c.emailid = :emailid and c.password = :password");
			query.setParameter("emailid", cuserBean.getEmailid());
			query.setParameter("password", cuserBean.getPassword());
			
			entity = (Cuser) query.getSingleResult();
						
				
			if(entity!=null) {
					
				status[0] = "true";
			}
				
						
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			em.close();
			
			Manager m = new Manager();
			if(m.validate()<=0) {
				
				return null;
			}
		}
		
		return status;
	}

	@Override
	public String[] saveOrderServer(List<ItemBean> listCart, CuserBean cuserBean) {
		
		String status[]={"",""};
		
		Sorder entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			SorderJpaController controller = new SorderJpaController(emf);
			OrderdetailJpaController controller2 = new OrderdetailJpaController(emf);
						
				double total = 0;
				for(int i=0;i<listCart.size();i++) {
					
					ItemBean itemBean = listCart.get(i);
					total = total + itemBean.getPrice();	
				}
			
				entity = new Sorder();
				
				Cuser cuser = new Cuser();
				cuser.setEmailid(cuserBean.getEmailid());
				entity.setEmailid(cuser);
				entity.setOrderdate(new Date());
				entity.setStatus(0);
				entity.setTotal(total);
							
								
				controller.create(entity);
				
				
				for(int i=0;i<listCart.size();i++) {
					
					ItemBean itemBean = listCart.get(i);
					
					Orderdetail entity2 = new Orderdetail();
					entity2.setOrderid(entity);
					
					Item item = new Item(itemBean.getItemid());
					entity2.setItemid(item);
					entity2.setPrice(itemBean.getPrice());
					entity2.setQuantity(1);
										
					controller2.create(entity2);
				}
			
					
			status[0] = "true";
			status[1] = ""+entity.getEmailid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
			
			Manager m = new Manager();
			if(m.validate()<=0) {
				
				return null;
			}
			
		}
		
		return status;
	}
}
