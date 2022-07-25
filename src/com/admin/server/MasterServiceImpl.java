/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.admin.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.admin.client.MasterService;
import com.admin.shared.CompanyBean;
import com.admin.shared.CuserBean;
import com.admin.shared.ItemBean;
import com.admin.shared.ItemtypeBean;
import com.admin.shared.OrderdetailBean;
import com.admin.shared.SorderBean;
import com.eneuron.Manager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.server.EMF;


import entity.Company;
import entity.CompanyJpaController;
import entity.Item;
import entity.ItemJpaController;
import entity.Itemtype;
import entity.ItemtypeJpaController;
import entity.Orderdetail;
import entity.Sorder;


public class MasterServiceImpl extends RemoteServiceServlet implements MasterService {

	private static final long serialVersionUID = 1L;

	@Override
	public String[] saveCompanyServer(CompanyBean bean, boolean isUpdate) {
		
		String status[]={"",""};
		
		Company entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			CompanyJpaController controller = new CompanyJpaController(emf);
			
			
			if(isUpdate) {
				
//				entity = controller.findCompany(bean.getCompanyid());
//				
//				if(entity!=null) {
//					
//					entity.setCompanyname(bean.getCompanyname());
//					controller.edit(entity);
//				}
				
				
			}else {
				
				entity = new Company();
				entity.setCompanyname(bean.getCompanyname());
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getCompanyid();
			
			
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

	@Override
	public List<CompanyBean> listCompanyServer(int id) {
		
		List<CompanyBean> list = new ArrayList<CompanyBean>();
		List<Company> listCompany = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			CompanyJpaController controller = new CompanyJpaController(emf);
			
			//listCompany = controller.findCompanyEntities(20, id);
			listCompany = controller.findCompanyEntities();
			
			for (Iterator<Company> iterator = listCompany.iterator(); iterator.hasNext();) {
				Company city = (Company) iterator.next();
				
				CompanyBean bean = new CompanyBean();
				bean.setCompanyid(city.getCompanyid());
				bean.setCompanyname(city.getCompanyname());
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
	public String[] saveItemTypeServer(ItemtypeBean bean, boolean isUpdate) {
		
		String status[]={"",""};
		EntityManager em =null;
		Itemtype entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			ItemtypeJpaController controller = new ItemtypeJpaController(emf);
			
			
			if(isUpdate) {
				
//				entity = controller.findCompany(bean.getCompanyid());
//				
//				if(entity!=null) {
//					
//					entity.setCompanyname(bean.getCompanyname());
//					controller.edit(entity);
//				}
				
				
			}else {
				
				entity = new Itemtype();
				entity.setItemtypename(bean.getItemtypename());
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getItemtypeid();
			
			
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

	@Override
	public List<ItemtypeBean> listItemTypeServer(int id) {
		
		List<ItemtypeBean> list = new ArrayList<ItemtypeBean>();
		List<Itemtype> listCompany = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			ItemtypeJpaController controller = new ItemtypeJpaController(emf);
			
			//listCompany = controller.findCompanyEntities(20, id);
			listCompany = controller.findItemtypeEntities();
			
			for (Iterator<Itemtype> iterator = listCompany.iterator(); iterator.hasNext();) {
				Itemtype city = (Itemtype) iterator.next();
				
				ItemtypeBean bean = new ItemtypeBean();
				bean.setItemtypeid(city.getItemtypeid());
				bean.setItemtypename(city.getItemtypename());
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
	public List<ItemBean> listItemServer(int id) {
		
		List<ItemBean> list = new ArrayList<ItemBean>();
		List<Item> listCompany = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			ItemJpaController controller = new ItemJpaController(emf);
			
			//listCompany = controller.findCompanyEntities(20, id);
			listCompany = controller.findItemEntities();
			
			for (Iterator<Item> iterator = listCompany.iterator(); iterator.hasNext();) {
				Item city = (Item) iterator.next();
				
				ItemBean bean = new ItemBean();
				
				CompanyBean companyBean = new CompanyBean();
				companyBean.setCompanyid(city.getCompanyid().getCompanyid());
				companyBean.setCompanyname(city.getCompanyid().getCompanyname());
				bean.setCompanyid(companyBean);
				
				bean.setItemid(companyBean.getCompanyid());
				bean.setItemname(city.getItemname());
				bean.setPrice(city.getPrice());
				bean.setModelno(city.getModelno());
				
				ItemtypeBean itemtypeBean = new ItemtypeBean();
				itemtypeBean.setItemtypeid(city.getItemtypeid().getItemtypeid());
				itemtypeBean.setItemtypename(city.getItemtypeid().getItemtypename());
				bean.setItemtypeid(itemtypeBean);
				
				bean.setLongdesc(city.getLongdesc());
				bean.setShortdesc(city.getShortdesc());
				
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
	public String[] saveItemServer(ItemBean bean, boolean isUpdate) {
		
		String status[]={"",""};
		EntityManager em =null;
		Item entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			ItemJpaController controller = new ItemJpaController(emf);
			
			
			if(isUpdate) {
				
//				entity = controller.findCompany(bean.getCompanyid());
//				
//				if(entity!=null) {
//					
//					entity.setCompanyname(bean.getCompanyname());
//					controller.edit(entity);
//				}
				
				
			}else {
				
				entity = new Item();
				
				Company companyid = new Company();
				companyid.setCompanyid(bean.getCompanyid().getCompanyid());
				entity.setCompanyid(companyid);
				
				entity.setItemname(bean.getItemname());
				
				Itemtype itemtype = new Itemtype();
				itemtype.setItemtypeid(bean.getItemtypeid().getItemtypeid());
				entity.setItemtypeid(itemtype);
				
				entity.setLongdesc(bean.getLongdesc());
				entity.setModelno(bean.getModelno());
				entity.setPrice(bean.getPrice());
				entity.setShortdesc(bean.getShortdesc());
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getItemid();
			
			
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

	@Override
	public List<SorderBean> listOrderServer(int id) {
		
		EntityManager em =null;
		List<SorderBean> list = new ArrayList<SorderBean>();
		List<Sorder> listSorder = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			em = emf.createEntityManager();
			
			Query query = em.createNamedQuery("Sorder.findByStatus");
			query.setParameter("status", 0);
						
			//query.setMaxResults(20);
			
			listSorder = query.getResultList();
						
			for (Iterator<Sorder> iterator = listSorder.iterator(); iterator.hasNext();) {
				Sorder sorder = (Sorder) iterator.next();
				
				SorderBean bean = new SorderBean();
				
				CuserBean cuserBean = new CuserBean();
				cuserBean.setAddress(sorder.getEmailid().getAddress());
				cuserBean.setCity(sorder.getEmailid().getCity());
				cuserBean.setEmailid(sorder.getEmailid().getEmailid());
				cuserBean.setMobile(sorder.getEmailid().getMobile());
				cuserBean.setPhone(sorder.getEmailid().getPhone());
				cuserBean.setUsername(sorder.getEmailid().getUsername());
				bean.setEmailid(cuserBean);
				
				System.out.println(sorder.getEmailid().getUsername()+" "+sorder.getEmailid().getMobile());
				
				bean.setOrderdate(sorder.getOrderdate());
				bean.setOrderid(sorder.getOrderid());
				bean.setStatus(0);
				bean.setTotal(sorder.getTotal());
				
				List<Orderdetail> listOrderDetail = (List<Orderdetail>) sorder.getOrderdetailCollection();
				
				List<OrderdetailBean> listOrderBean = new ArrayList<OrderdetailBean>();
				
				for(int i=0;i<listOrderDetail.size();i++) {
					
					Orderdetail entity2 = listOrderDetail.get(i);
					OrderdetailBean bean2 = new OrderdetailBean();
					
					Item item = entity2.getItemid();
					ItemBean itemBean = new ItemBean();
					itemBean.setItemid(item.getItemid());
					itemBean.setItemname(item.getItemname());
					itemBean.setPrice(item.getPrice());
					bean2.setItemid(itemBean);
					
					
					
					bean2.setOrderdetailid(entity2.getOrderdetailid());
					bean2.setPrice(entity2.getPrice());
					
					listOrderBean.add(bean2);
					
				}
				bean.setOrderdetailCollection(listOrderBean);
				
				
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			em.close();
		}
		
		return list;
	}

	@Override
	public String[] delCompanyServer(String text) {
		
		String status[]={"",""};
		
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			CompanyJpaController controller = new CompanyJpaController(emf);
				
								
			controller.destroy(Integer.parseInt(text));
				
								
			status[0] = "true";
			
			
			
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

	@Override
	public String[] delItemTypeServer(String text) {
		
		String status[]={"",""};
		
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			ItemtypeJpaController controller = new ItemtypeJpaController(emf);
				
								
			controller.destroy(Integer.parseInt(text));
				
								
			status[0] = "true";
			
			
			
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

	@Override
	public String[] delItemServer(String text) {
		
		String status[]={"",""};
		
		
		try {
		
			EntityManagerFactory emf = EMF.get("ESmartPU");
			ItemJpaController controller = new ItemJpaController(emf);
											
			controller.destroy(Integer.parseInt(text));
												
			status[0] = "true";
						
			
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
