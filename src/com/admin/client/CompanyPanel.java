package com.admin.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.admin.client.MasterService.Util;
import com.admin.shared.CompanyBean;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;


public class CompanyPanel extends LazyPanel{
	
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_open, anc_list;
	Label label;
	
	TextBox tb_search;
	DialogBox dialog;
	FlexTable flexData;
	
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_city;
	Button b_new, b_save, b_close, b_open, b_del;
	public boolean isUpdate, isSave;
	Label lab;
	DialogBox box;
	MasterServiceAsync masterService = Util.getInstance();
	Label l_status, l_title;
	int id=0;
	List<CompanyBean> list = new ArrayList<CompanyBean>();
	CompanyBean bean;
		
	public CompanyPanel() {
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
		lab.setText("Manage Company");
		
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
		
		l_title = new Label("Company");
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
				lab.setText("Manage Company");
				tb_city.setFocus(true);
				
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
				
				masterService.listCompanyServer(id, new AsyncCallback<List<CompanyBean>>() {
					
					@Override
					public void onSuccess(List<CompanyBean> result) {
						
						flexData.removeAllRows();
						list.clear();
						
						for (Iterator<CompanyBean> iterator = result.iterator(); iterator
								.hasNext();) {
							CompanyBean cityBean = (CompanyBean) iterator.next();
							list.add(cityBean);
							
							HorizontalPanel hp = new HorizontalPanel();
							hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
							hp.setStyleName("back");
							
							Anchor anc = new Anchor(cityBean.getCompanyname());
							anc.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									
									
									Anchor anc = (Anchor) event.getSource();
									createAnchor(anc);
									
																		
								}
							});
							
							int count = flexData.getRowCount();
							anc.setTitle(""+cityBean.getCompanyid());
							
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
				
		lab = new Label("Manage Company");
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
		
		Label label = new Label("Company ID");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_id = new TextBox();
		tb_id.setStyleName("textbox");
		tb_id.setEnabled(false);
		flex.setWidget(1, 1, tb_id);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//Company name
		 label = new Label("Company Name");
		 label.setStyleName("label");
		 flex.setWidget(2, 0, label);
			
		tb_city = new TextBox();
		tb_city.setStyleName("textbox");
		tb_city.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				
				int code = event.getNativeKeyCode();
				
				if(code==13) {
				
					save();
					
				}
								
			}
		});
		flex.setWidget(2, 1, tb_city);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
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
				tb_city.setText("");
				lab.setText("New Company");
				tb_city.setFocus(true);
				
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
	
	private void search(String q) {
	

		
	}
	
	private void createAnchor(Anchor anc) {
		
		createDialog();
		isUpdate = true;
		lab.setText("Open Company");
		
		Integer id = Integer.parseInt(anc.getTitle());
		
		
		for(int i=0;i<list.size();i++) {
			
			bean = list.get(i);
			
			if(bean.getCompanyid()==id) {
		
				
				tb_id.setText(""+bean.getCompanyid());
				tb_city.setText(bean.getCompanyname());
				tb_city.setFocus(true);
				
				break;
			}
		}
	}
	
	private void save() {
		
		String city = tb_city.getText();
		
		if(city.trim().length()>0) {
			
		
		if(isUpdate) {//update start
			
			if(bean.getCompanyname().equalsIgnoreCase(tb_city.getText())){
				
				isSave = false;
				
			}else {
				
				isSave = true;
			}
			
			if(isSave) {//save only if the record changed
				
				bean.setCompanyid(Integer.parseInt(tb_id.getText()));
				
			}
			
		}else { //fresh save
			
				isSave = true;
				bean = new CompanyBean();
			
		}
		
		
		if(isSave) {//Save
			
			bean.setCompanyname(tb_city.getText().toUpperCase());
			
			masterService.saveCompanyServer(bean, isUpdate, new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						
						bean.setCompanyid(Integer.parseInt(result[1]));
						tb_id.setText(result[1]);
						tb_city.setText(tb_city.getText().toUpperCase());
						l_status.setText("Saved");
						
						HorizontalPanel hp = new HorizontalPanel();
						hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
						hp.setStyleName("back");
						
						Anchor anc = new Anchor(tb_city.getText());
						anc.setTitle(""+bean.getCompanyid());
						anc.setTitle(tb_id.getText());
						
						anc.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								
								Anchor anc = (Anchor) event.getSource();
								createDialog();
								isUpdate = true;
								lab.setText("Open Company");
								tb_id.setText(anc.getTitle());
								tb_city.setText(anc.getText());
								tb_city.setFocus(true);
																
							}
						});
						
						
						
						//hp.add(anc);
						//flexData.setWidget(flexData.getRowCount(), 0, hp);
						
						//add in a list
						if(isUpdate) {
							
							for(int i=0;i<list.size();i++) {
								
								CompanyBean cityBean = list.get(i);
								
								if(cityBean.getCompanyid()==bean.getCompanyid()) {
							
									list.remove(i);
									list.add(bean);
									
									flexData.removeAllRows();
									isUpdate = true;
									
									for (Iterator<CompanyBean> iterator = list.iterator(); iterator
											.hasNext();) {
										CompanyBean cityBean2 = (CompanyBean) iterator.next();
										
										
										HorizontalPanel hp2 = new HorizontalPanel();
										hp2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
										hp2.setStyleName("back");
										
										Anchor anc2 = new Anchor(cityBean2.getCompanyname());
										anc2.addClickHandler(new ClickHandler() {
											
											@Override
											public void onClick(ClickEvent event) {
												
												Anchor anc = (Anchor) event.getSource();
												createAnchor(anc);
												
																					
											}
										});
										
										int count = flexData.getRowCount();
										anc2.setTitle(""+cityBean2.getCompanyid());
										
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
	
	private void delete() {
		
		if(tb_id.getText().isEmpty()) {
			
			Window.alert("Cant Delete");
			
		}else {
			
			masterService.delCompanyServer(tb_id.getText(), new AsyncCallback<String[]>() {
				
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
}
