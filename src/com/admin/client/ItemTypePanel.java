package com.admin.client;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.admin.client.MasterService.Util;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;


public class ItemTypePanel extends LazyPanel{
	
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_open, anc_list;
	Label label;
	
	TextBox tb_search;
	DialogBox dialog;
	FlexTable flexData;
	
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_itemtype;
	Button b_new, b_save, b_close, b_open, b_del;
	public boolean isUpdate, isSave;
	Label lab;
	DialogBox box;
	MasterServiceAsync masterService = Util.getInstance();
	Label l_status, l_title;
	int id=0;
	List<ItemtypeBean> list = new ArrayList<ItemtypeBean>();
	ItemtypeBean bean;
		
	public ItemTypePanel() {
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
		lab.setText("Manage Item Type");
		
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
		
		l_title = new Label("Item Type");
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
				lab.setText("Manage Item Type");
				tb_itemtype.setFocus(true);
				
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
				
				masterService.listItemTypeServer(id, new AsyncCallback<List<ItemtypeBean>>() {
					
					@Override
					public void onSuccess(List<ItemtypeBean> result) {
						
						flexData.removeAllRows();
						list.clear();
						
						for (Iterator<ItemtypeBean> iterator = result.iterator(); iterator
								.hasNext();) {
							ItemtypeBean cityBean = (ItemtypeBean) iterator.next();
							list.add(cityBean);
							
							HorizontalPanel hp = new HorizontalPanel();
							hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
							hp.setStyleName("back");
							
							Anchor anc = new Anchor(cityBean.getItemtypename());
							anc.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									
									
									Anchor anc = (Anchor) event.getSource();
									createAnchor(anc);
									
																		
								}
							});
							
							int count = flexData.getRowCount();
							anc.setTitle(""+cityBean.getItemtypeid());
							
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
				
		lab = new Label("Manage Item Type");
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
		
		Label label = new Label("Item Type ID");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_id = new TextBox();
		tb_id.setStyleName("textbox");
		tb_id.setEnabled(false);
		flex.setWidget(1, 1, tb_id);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//Company name
		 label = new Label("Item Type Name");
		 label.setStyleName("label");
		 flex.setWidget(2, 0, label);
			
		tb_itemtype = new TextBox();
		tb_itemtype.setStyleName("textbox");
		tb_itemtype.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				
				int code = event.getNativeKeyCode();
				
				if(code==13) {
				
					save();
					
				}
								
			}
		});
		flex.setWidget(2, 1, tb_itemtype);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		

		
		
				
//		anc_open = new Anchor("Open");
//		anc_open.setStyleName("myopen");
//		anc_open.setAccessKey('O');
//		anc_open.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				
//				l_status.setText(":");
//				lab.setText("Open Company");
//				isUpdate = true;
//				flexData.removeAllRows();
//				
//				String nakaName = tb_city.getText();
//				
//				if(!nakaName.isEmpty()) {
//					
//					masterService.openCompanyServer(nakaName.toUpperCase(),new AsyncCallback<ItemtypeBean>() {
//						
//						@Override
//						public void onSuccess(ItemtypeBean result) {
//							
//							if(result!=null) {
//								
//								bean = result;
//								tb_id.setText(""+result.getItemtypeid());
//								tb_city.setText(result.getItemtypename());
//								isUpdate = true;
//								l_status.setText(":");
//								
//							}else {
//								
//								l_status.setText("not found");
//							}
//							
//						}
//						
//						@Override
//						public void onFailure(Throwable caught) {
//							
//							ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
//							box.show();
//							
//						}
//					});
//					
//				}else {
//					
//					l_status.setText("Please Enter Company Name");
//				}
//				
//			}
//		});
//		flex.setWidget(2, 2, anc_open);
//		formatter.setVerticalAlignment(2, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		
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
				tb_itemtype.setText("");
				lab.setText("New Item Type");
				tb_itemtype.setFocus(true);
				
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
		
		flex.setWidget(3, 0, grid);
		formatter.setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setColSpan(3, 0, 3);
			
		vp.add(flex);
		
		
				
		box.setWidget(vp);
		box.center();
	}
	
	private void delete() {
		
		if(tb_id.getText().isEmpty()) {
			
			Window.alert("Cant Delete");
			
		}else {
			
			masterService.delItemTypeServer(tb_id.getText(), new AsyncCallback<String[]>() {
				
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
	
	private void search(String q) {
	
//		masterService.searchCompanyServer(	q.toUpperCase(), new AsyncCallback<List<ItemtypeBean>>() {
//			
//			@Override
//			public void onSuccess(List<ItemtypeBean> result) {
//				
//				flexData.removeAllRows();
//				list.clear();
//				
//				for (Iterator<ItemtypeBean> iterator = result.iterator(); iterator
//						.hasNext();) {
//					ItemtypeBean cityBean = (ItemtypeBean) iterator.next();
//					list.add(cityBean);
//					
//					HorizontalPanel hp = new HorizontalPanel();
//					hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//					hp.setStyleName("back");
//					
//					Anchor anc = new Anchor(cityBean.getItemtypename());
//					anc.addClickHandler(new ClickHandler() {
//						
//						@Override
//						public void onClick(ClickEvent event) {
//							
//							
//							Anchor anc = (Anchor) event.getSource();
//							createAnchor(anc);
//							
//																
//						}
//					});
//					
//					int count = flexData.getRowCount();
//					anc.setTitle(""+cityBean.getItemtypeid());
//					
//					hp.add(anc);
//					flexData.setWidget(count, 0, hp);
//					
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				
//				
//			}
//		});
		
	}
	
	private void createAnchor(Anchor anc) {
		
		createDialog();
		isUpdate = true;
		lab.setText("Open Item Type");
		
		Integer id = Integer.parseInt(anc.getTitle());
		
		
		for(int i=0;i<list.size();i++) {
			
			bean = list.get(i);
			
			if(bean.getItemtypeid()==id) {
		
				
				tb_id.setText(""+bean.getItemtypeid());
				tb_itemtype.setText(bean.getItemtypename());
				tb_itemtype.setFocus(true);
				
				break;
			}
		}
	}
	
	private void save() {
		
		String city = tb_itemtype.getText();
		
		if(city.trim().length()>0) {
			
		
		if(isUpdate) {//update start
			
			if(bean.getItemtypename().equalsIgnoreCase(tb_itemtype.getText())){
				
				isSave = false;
				
			}else {
				
				isSave = true;
			}
			
			if(isSave) {//save only if the record changed
				
				bean.setItemtypeid(Integer.parseInt(tb_id.getText()));
				
			}
			
		}else { //fresh save
			
				isSave = true;
				bean = new ItemtypeBean();
			
		}
		
		
		if(isSave) {//Save
			
			bean.setItemtypename(tb_itemtype.getText().toUpperCase());
			
			masterService.saveItemTypeServer(bean, isUpdate, new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						
						bean.setItemtypeid(Integer.parseInt(result[1]));
						tb_id.setText(result[1]);
						tb_itemtype.setText(tb_itemtype.getText().toUpperCase());
						l_status.setText("Saved");
						
						HorizontalPanel hp = new HorizontalPanel();
						hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
						hp.setStyleName("back");
						
						Anchor anc = new Anchor(tb_itemtype.getText());
						anc.setTitle(""+bean.getItemtypeid());
						anc.setTitle(tb_id.getText());
						
						anc.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								
								Anchor anc = (Anchor) event.getSource();
								createDialog();
								isUpdate = true;
								lab.setText("Open Item Type");
								tb_id.setText(anc.getTitle());
								tb_itemtype.setText(anc.getText());
								tb_itemtype.setFocus(true);
																
							}
						});
						
						
						
						//hp.add(anc);
						//flexData.setWidget(flexData.getRowCount(), 0, hp);
						
						//add in a list
						if(isUpdate) {
							
							for(int i=0;i<list.size();i++) {
								
								ItemtypeBean cityBean = list.get(i);
								
								if(cityBean.getItemtypeid()==bean.getItemtypeid()) {
							
									list.remove(i);
									list.add(bean);
									
									flexData.removeAllRows();
									isUpdate = true;
									
									for (Iterator<ItemtypeBean> iterator = list.iterator(); iterator
											.hasNext();) {
										ItemtypeBean cityBean2 = (ItemtypeBean) iterator.next();
										
										
										HorizontalPanel hp2 = new HorizontalPanel();
										hp2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
										hp2.setStyleName("back");
										
										Anchor anc2 = new Anchor(cityBean2.getItemtypename());
										anc2.addClickHandler(new ClickHandler() {
											
											@Override
											public void onClick(ClickEvent event) {
												
												Anchor anc = (Anchor) event.getSource();
												createAnchor(anc);
												
																					
											}
										});
										
										int count = flexData.getRowCount();
										anc2.setTitle(""+cityBean2.getItemtypeid());
										
										hp2.add(anc2);
										flexData.setWidget(count, 0, hp2);
										
									}
									
									break;
								}
							}
						
						}else {
							
							list.add(bean);
							
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

}
