package com.admin.client;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.admin.client.MasterService.Util;
import com.admin.shared.CompanyBean;
import com.admin.shared.ItemBean;
import com.admin.shared.ItemtypeBean;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;


public class ItemPanel extends LazyPanel{
	
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_open, anc_list;
	Label label;
	
	TextBox tb_search;
	DialogBox dialog;
	FlexTable flexData;
	
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_item, tb_model, tb_price;
	TextArea areaShort, areaLong;
	ListBox listBoxItemType, listBoxCompany;
	
	Button b_new, b_save, b_close, b_open, b_del;
	public boolean isUpdate, isSave;
	Label lab;
	DialogBox box;
	MasterServiceAsync masterService = Util.getInstance();
	Label l_status, l_title;
	int id=0;
	List<ItemBean> listItem = new ArrayList<ItemBean>();
	List<CompanyBean> listCompany = new ArrayList<CompanyBean>();
	List<ItemtypeBean> listItemtype = new ArrayList<ItemtypeBean>();
	ItemBean bean;
		
	public ItemPanel() {
	}
	
	@Override
	protected Widget createWidget() {
				
		
		dp = new DockLayoutPanel(Unit.PX);
		dp.setStyleName("dock3");
		
		dp.addNorth(createHeader(), 35);
		dp.add(createData());
		
		//scrollPanel.setWidget(dp);
		createDialog();
		isUpdate = false;
		lab.setText("Manage Item");
		
		return dp;
	}
	
	private Widget createData() {
		
		flexData = new FlexTable();
		flexData.setStyleName("data");
		flexData.setWidth("100%");
		//flexData.setBorderWidth(1);
		flexData.setCellPadding(0);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		//scrollPanel.setAlwaysShowScrollBars(true);
		//scrollPanel.setStyleName("content");
		scrollPanel.setWidth("100%");
		//scrollPanel.setSize("100%", "100px");
		
		scrollPanel.setWidget(flexData);
		
		return scrollPanel;
	}
	
	private Widget createHeader() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("controls");
		
		/* left panel */
		
		HorizontalPanel hp1 = new HorizontalPanel();
		hp.add(hp1);
		
		l_title = new Label("Item");
		l_title.setStyleName("title");
		hp1.add(l_title);
		
		anc_manage = new Anchor("Manage");
		anc_manage.setStyleName("mynew");
		anc_manage.setAccessKey('M');
		anc_manage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				createDialog();
				isUpdate = false;
				lab.setText("Manage Item");
				tb_item.setFocus(true);
				
			}
		});
		hp1.add(anc_manage);
		
		
		/* right Panel */
		HorizontalPanel hp2 = new HorizontalPanel();
		//hp2.setBorderWidth(1);
		hp.add(hp2);
		hp.setCellHorizontalAlignment(hp2, HasHorizontalAlignment.ALIGN_RIGHT);
		
		anc_list = new Anchor("List");
		anc_list.setStyleName("mylist");
		anc_list.setAccessKey('S');
		anc_list.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				masterService.listItemServer(id, new AsyncCallback<List<ItemBean>>() {
					
					@Override
					public void onSuccess(List<ItemBean> result) {
						
						flexData.removeAllRows();
						listItem.clear();
						
						for (Iterator<ItemBean> iterator = result.iterator(); iterator
								.hasNext();) {
							ItemBean cityBean = (ItemBean) iterator.next();
							listItem.add(cityBean);
							
							HorizontalPanel hp = new HorizontalPanel();
							hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
							hp.setStyleName("back");
							
							Anchor anc = new Anchor(cityBean.getItemname());
							anc.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									
									
									Anchor anc = (Anchor) event.getSource();
									createAnchor(anc);
									
																		
								}
							});
							
							int count = flexData.getRowCount();
							anc.setTitle(""+cityBean.getItemid());
							
							hp.add(anc);
							flexData.setWidget(count, 0, hp);
							
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
						box.center();
						box.show();
					}
				});
				
			}
		});
		hp2.add(anc_list);
		
		/* search */
		tb_search = new TextBox();
		tb_search.setStyleName("searchbox");
		tb_search.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				
				int code = event.getNativeKeyCode();
				
				if(code==13) {
				
					String q = tb_search.getText();
					
					if(!q.isEmpty()) {
						
						search(q);
					}
					
				}
				
			}
		});
		hp2.add(tb_search);
		hp2.setCellVerticalAlignment(tb_search, HasVerticalAlignment.ALIGN_MIDDLE);
		hp2.add(new HTML("&nbsp;&nbsp;"));
		
		anc_search = new Anchor("Search");
		anc_search.setStyleName("mysearch");
		anc_search.setAccessKey('S');
		anc_search.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				String q = tb_search.getText();
				
				if(!q.isEmpty()) {
					
					search(q);
				}
				
			}
		});
		hp2.add(anc_search);
		
		
		
		return hp;
		
	}
	
	private void createDialog() {
		
		box = new DialogBox(true);
		box.setAnimationEnabled(true);
		
		vp = new VerticalPanel();
				
		lab = new Label("Manage Item");
		lab.setStyleName("banner");
		vp.add(lab);
		
		vp.add(new HTML("&nbsp;"));
		
		flex = new FlexTable();
		flex.setWidth("100%");
		flex.setCellPadding(5);
		flex.setCellSpacing(0);
		
		
		
		FlexCellFormatter formatter = flex.getFlexCellFormatter();
		
		l_status = new Label(":");
		flex.setWidget(0, 1, l_status);
		//formatter.setColSpan(0, 0, 2);
		
		Label label = new Label("Item ID");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_id = new TextBox();
		tb_id.setStyleName("textbox");
		tb_id.setEnabled(false);
		flex.setWidget(1, 1, tb_id);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Item Type");
		label.setStyleName("label");
		flex.setWidget(2, 0, label);
		
		listBoxItemType = new ListBox();
		listBoxItemType.setStyleName("listbox");
		flex.setWidget(2, 1, listBoxItemType);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		listItemtype();
		
		label = new Label("Company Name");
		label.setStyleName("label");
		flex.setWidget(3, 0, label);
		
		listBoxCompany = new ListBox();
		listBoxCompany.setStyleName("listbox");
		flex.setWidget(3, 1, listBoxCompany);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		listCompany();
				
		
		//Item name
		 label = new Label("Item Name");
		 label.setStyleName("label");
		 flex.setWidget(4, 0, label);
			
		tb_item = new TextBox();
		tb_item.setStyleName("textbox");
		tb_item.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				
				int code = event.getNativeKeyCode();
				
				if(code==13) {
				
					save();
					
				}
								
			}
		});
		flex.setWidget(4, 1, tb_item);
		formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Model No");
		 label.setStyleName("label");
		 flex.setWidget(5, 0, label);
		 			
		 tb_model = new TextBox();
		 tb_model.setStyleName("textbox");
		 flex.setWidget(5, 1, tb_model);
		 formatter.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Short Desc");
		 label.setStyleName("label");
		 flex.setWidget(6, 0, label);
		 
		 areaShort = new TextArea();
		 areaShort.setWidth("245px");
		 flex.setWidget(6, 1, areaShort);
		 formatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		 
		 
		 label = new Label("Long Desc");
		 label.setStyleName("label");
		 flex.setWidget(7, 0, label);
		 
		 areaLong = new TextArea();
		 areaLong.setWidth("245px");
		 flex.setWidget(7, 1, areaLong);
		 formatter.setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		 label = new Label("Price");
		 label.setStyleName("label");
		 flex.setWidget(8, 0, label);
		 			
		 tb_price = new TextBox();
		 tb_price.setStyleName("textbox");
		 flex.setWidget(8, 1, tb_price);
		 formatter.setHorizontalAlignment(8, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				

			//buttons
		Grid grid = new Grid(1, 4);
		
		b_new = new Button("New");
		b_new.setWidth("60px");
		b_new.setAccessKey('N');
		b_new.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				isUpdate = false;
				tb_id.setText("");
				l_status.setText(":");
				tb_item.setText("");
				tb_model.setText("");
				areaShort.setText("");
				areaLong.setText("");
				tb_price.setText("");
				lab.setText("New Item");
				tb_item.setFocus(true);
				
			}
		});
		grid.setWidget(0, 0, b_new);
		
		b_save = new Button("Save");
		b_save.setWidth("60px");
		b_save.setAccessKey('S');
		b_save.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				save();
					
			}
		});
		grid.setWidget(0, 1, b_save);
		
		b_del = new Button("Del");
		b_del.setWidth("60px");
		b_del.setAccessKey('D');
		b_del.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				delete();
					
			}
		});
		grid.setWidget(0, 2, b_del);
		
		b_close = new Button("Close");
		b_close.setWidth("60px");
		b_close.setAccessKey('C');
		b_close.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				box.setVisible(false);
				
			}
		});
		grid.setWidget(0, 3, b_close);
		
		flex.setWidget(9, 0, grid);
		formatter.setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setColSpan(9, 0, 3);
			
		vp.add(flex);
			
				
		box.setWidget(vp);
		box.center();
	}
	
	private void search(String q) {
	

		
	}
	
	private void createAnchor(Anchor anc) {
		
		createDialog();
		isUpdate = true;
		lab.setText("Open Item");
		
		Integer id = Integer.parseInt(anc.getTitle());
		
		
		for(int i=0;i<listItem.size();i++) {
			
			bean = listItem.get(i);
			
			if(bean.getItemid()==id) {
		
				tb_id.setText(""+bean.getItemid());
				tb_item.setText(bean.getItemname());
				tb_model.setText(bean.getModelno());
				tb_price.setText(""+bean.getPrice());
				areaShort.setText(bean.getShortdesc());
				areaLong.setText(bean.getLongdesc());
				
				//
				int companyId = bean.getCompanyid().getCompanyid();
				int itemTypeId = bean.getItemtypeid().getItemtypeid();
				
				for(int j=0;j<listCompany.size();j++) {
					
					CompanyBean companyBean = listCompany.get(j);
					
					if(companyBean.getCompanyid()==companyId) {
						
						listBoxCompany.setSelectedIndex(j);
						break;
					}
				}
				
				for(int j=0;j<listItemtype.size();j++) {
					
					ItemtypeBean itemtypeBean = listItemtype.get(j);
					
					if(itemtypeBean.getItemtypeid()==itemTypeId) {
						
						listBoxItemType.setSelectedIndex(j);
						break;
					}
				}
				
				tb_item.setFocus(true);
				
				break;
			}
		}
	}
	
	private void save() {
		
		String city = tb_item.getText();
		
		if(city.trim().length()>0&&tb_price.getText().trim().length()>0) {
			
		
		if(isUpdate) {//update start
			
			if(bean.getItemname().equalsIgnoreCase(tb_item.getText())){
				
				isSave = false;
				
			}else {
				
				isSave = true;
			}
			
			if(isSave) {//save only if the record changed
				
				bean.setItemid(Integer.parseInt(tb_id.getText()));
				
			}
			
		}else { //fresh save
			
				isSave = true;
				bean = new ItemBean();
			
		}
		
		
		if(isSave) {//Save
			
			bean.setItemname(tb_item.getText().toUpperCase());
			bean.setItemtypeid(listItemtype.get(listBoxItemType.getSelectedIndex()));
			bean.setCompanyid(listCompany.get(listBoxCompany.getSelectedIndex()));
			bean.setItemname(tb_item.getText());
			bean.setLongdesc(areaLong.getText());
			bean.setModelno(tb_model.getText());
			bean.setPrice(Double.parseDouble(tb_price.getText()));
			bean.setShortdesc(areaShort.getText());
			
			
			masterService.saveItemServer(bean, isUpdate, new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						
						bean.setItemid(Integer.parseInt(result[1]));
						tb_id.setText(result[1]);
						tb_item.setText(tb_item.getText().toUpperCase());
						l_status.setText("Saved");
						
						HorizontalPanel hp = new HorizontalPanel();
						hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
						hp.setStyleName("back");
						
						Anchor anc = new Anchor(tb_item.getText());
						anc.setTitle(""+bean.getItemid());
						anc.setTitle(tb_id.getText());
						
						anc.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								
								Anchor anc = (Anchor) event.getSource();
								createDialog();
								isUpdate = true;
								lab.setText("Open Item");
								tb_id.setText(anc.getTitle());
								tb_item.setText(anc.getText());
								tb_item.setFocus(true);
																
							}
						});
						
						
						
						//hp.add(anc);
						//flexData.setWidget(flexData.getRowCount(), 0, hp);
						
						//add in a list
						if(isUpdate) {
							
							for(int i=0;i<listItem.size();i++) {
								
								ItemBean cityBean = listItem.get(i);
								
								if(cityBean.getItemid()==bean.getItemid()) {
							
									listItem.remove(i);
									listItem.add(bean);
									
									flexData.removeAllRows();
									isUpdate = true;
									
									for (Iterator<ItemBean> iterator = listItem.iterator(); iterator
											.hasNext();) {
										ItemBean cityBean2 = (ItemBean) iterator.next();
										
										
										HorizontalPanel hp2 = new HorizontalPanel();
										hp2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
										hp2.setStyleName("back");
										
										Anchor anc2 = new Anchor(cityBean2.getItemname());
										anc2.addClickHandler(new ClickHandler() {
											
											@Override
											public void onClick(ClickEvent event) {
												
												Anchor anc = (Anchor) event.getSource();
												createAnchor(anc);
												
																					
											}
										});
										
										int count = flexData.getRowCount();
										anc2.setTitle(""+cityBean2.getItemid());
										
										hp2.add(anc2);
										flexData.setWidget(count, 0, hp2);
										
									}
									
									break;
								}
							}
						
						}else {
							
							listItem.add(bean);
							
							hp.add(anc);
							flexData.setWidget(flexData.getRowCount(), 0, hp);
						
						}
						
						
					}else {
						
						ErrorBox box = new ErrorBox(result[1]);
						box.show();
						l_status.setText(":");
					}
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
					box.show();
					
				}
			});
			
		}
		
		else { //save not if record unchanged
			
			l_status.setText("unchanged cannot be saved");
		}
		
		
			
			
		
			
		}else {

			
		}
		
	}
	
	private void listCompany() {
		
		masterService.listCompanyServer(0,
				
				new AsyncCallback<List<CompanyBean>>() {
	
					@Override
					public void onSuccess(List<CompanyBean> result) {
						// TODO Auto-generated method stub
						listCompany = result;
										
	
						for (Iterator<CompanyBean> iterator = result
								.iterator(); iterator.hasNext();) {
	
							CompanyBean custBean = (CompanyBean) iterator
									.next();
	
							listBoxCompany.addItem(custBean.getCompanyname());
							
							
							
	
						}
	
						
					}
	
					@Override
					public void onFailure(Throwable caught) {
	
						Window.alert("Error");
	
					}
				});
	}
	
	private void delete() {
		
		if(tb_id.getText().isEmpty()) {
			
			Window.alert("Cant Delete");
			
		}else {
			
			masterService.delItemServer(tb_id.getText(), new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						
						Window.alert("Delete Successfull");
						box.setVisible(false);
						
						
					}else {
						
						ErrorBox box = new ErrorBox(result[1]);
						box.show();
						
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			
			});
	}
	}
	
	private void listItemtype() {
		
		masterService.listItemTypeServer(0,
				
				new AsyncCallback<List<ItemtypeBean>>() {
	
					@Override
					public void onSuccess(List<ItemtypeBean> result) {
						// TODO Auto-generated method stub
						listItemtype = result;
										
	
						for (Iterator<ItemtypeBean> iterator = result
								.iterator(); iterator.hasNext();) {
	
							ItemtypeBean custBean = (ItemtypeBean) iterator
									.next();
	
							listBoxItemType.addItem(custBean.getItemtypename());
							
							
							
	
						}
	
						
					}
	
					@Override
					public void onFailure(Throwable caught) {
	
						Window.alert("Error");
	
					}
				});
	}
	

}
