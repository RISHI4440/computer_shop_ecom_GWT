package com.admin.client;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.admin.client.MasterService.Util;
import com.admin.shared.OrderdetailBean;
import com.admin.shared.SorderBean;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;

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



public class OrderPanel extends LazyPanel{
	
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
	Button b_new, b_save, b_close, b_open;
	public boolean isUpdate, isSave;
	Label lab;
	DialogBox box;
	MasterServiceAsync masterService = Util.getInstance();
	Label l_status, l_title;
	int id=0;
	List<SorderBean> list = new ArrayList<SorderBean>();
	SorderBean bean;
		
	public OrderPanel() {
	}
	
	@Override
	protected Widget createWidget() {
				
		
		dp = new DockLayoutPanel(Unit.PX);
		dp.setStyleName("dock3");
		
		dp.addNorth(createHeader(), 35);
		dp.add(createData());
		
		//scrollPanel.setWidget(dp);
		
		//lab.setText("Manage Order");
		
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
		
		l_title = new Label("Order");
		l_title.setStyleName("title");
		hp1.add(l_title);
		
		
		
		
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
				
				flexData.removeAllRows();
				masterService.listOrderServer(id, new AsyncCallback<List<SorderBean>>() {
					
					@Override
					public void onSuccess(List<SorderBean> result) {
						
						flexData.removeAllRows();
						list.clear();
						flexData.setCellPadding(5);
						
						list = result;
						
						
						int i=0;
						
						for (Iterator<SorderBean> iterator = result.iterator(); iterator
								.hasNext();) {
							SorderBean orderBean = (SorderBean) iterator.next();
							//list.add(orderBean);
							
							flexData.setWidget(i, 0, new HTML(""+orderBean.getOrderid()));
							flexData.setWidget(i, 1, new HTML(""+orderBean.getEmailid().getEmailid()));
							flexData.setWidget(i, 2, new HTML(""+orderBean.getOrderdate()));
							flexData.setWidget(i, 3, new HTML(""+orderBean.getEmailid().getUsername()));
							flexData.setWidget(i, 4, new HTML(""+orderBean.getEmailid().getAddress()));
							flexData.setWidget(i, 5, new HTML(""+orderBean.getEmailid().getMobile()));
							flexData.setWidget(i, 6, new HTML(""+orderBean.getTotal()));
							
							Button button = new Button("View");
							button.setTitle(""+i);
							flexData.setWidget(i, 7, button);
							button.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									
									
									Button b = (Button) event.getSource();
									int i = Integer.parseInt(b.getTitle());
									
									SorderBean sbean = list.get(i);
									
									DialogBox box2 = new DialogBox(true);
									ScrollPanel panel = new ScrollPanel();
									panel.setSize("400px", "500px");
									box2.setWidget(panel);
									
									FlexTable table = new FlexTable();
									table.setWidth("400px");
									panel.setWidget(table);
									
									List<OrderdetailBean> list2 = (List<OrderdetailBean>) sbean.getOrderdetailCollection();
									double total = 0;
									
									for(int j=0;j<list2.size();j++) {
										
										
										OrderdetailBean bean2 = list2.get(j);
										table.setHTML(j, 0, ""+(j+1));
										table.setHTML(j, 1,bean2.getItemid().getItemname());
										table.setHTML(j, 2,""+bean2.getItemid().getPrice());
										
										total = total + bean2.getItemid().getPrice();
									}
									
									table.setHTML(table.getRowCount()+1, 0, "<b>Total:"+total+"</b>");
									
									
									box2.center();
									box2.show();
									
									
									
								}
							});
							
							i++;
							
						}
						
//						for (int j=0;j<10;j++) {
//					
//					
//					flexData.setWidget(j, 0, new HTML("121"));
//					flexData.setWidget(j, 1, new HTML("er"));
//					flexData.setWidget(j, 2, new HTML("121"));
//					flexData.setWidget(j, 3, new HTML("sds"));
//					
//					Button button = new Button("View");
//					button.setTitle(""+j);
//					flexData.setWidget(j, 4, button);
//					j++;
//					
//				}

						
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
				
				
			}
		});
		hp2.add(anc_search);
		
		
		
		return hp;
		
	}
	
	
	
	
	
	
	
	private void save() {
		
		
	}

}
