package com.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




import com.shared.CuserBean;
import com.shared.ItemBean;
import com.shared.ItemtypeBean;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import entity.Item;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ComputerShop implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	DockLayoutPanel dp;
	ScrollPanel sp;
	Button blogin, bhome, bAbout, bcontact, bacart, bregister, bemail;
	FlexTable flex;
	List<ItemtypeBean> list;
	List<ItemBean> listItem;
	
	VerticalPanel vpCategory;
	
	ItemtypeBean bean;
	ItemBean itemBean;
	DialogBox dialog;
	
	FlexTable flex3;
	List<ItemBean> listCart = new ArrayList<ItemBean>();
	
	LoginComposit composit;
	CuserBean cuserBean;
	boolean isLoggedIn = false;
	
	Label login;
	DecoratedPopupPanel panel;
	
	
	
	
	public void onModuleLoad() {
		
		dp = new DockLayoutPanel(Unit.PX);
		dp.setSize("100%", "100%");
		dp.setStyleName("cwt-DockPanel");

		dp.addNorth(createEmpty(), 10);
		dp.addNorth(createNorth(), 80);
		dp.addNorth(createMenu(), 43);
		dp.addSouth(createFooter(), 42);
		dp.add(createMain());
		
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(dp);
		
	}
	
	private Widget createEmpty() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSize("100%", "30px");
		
		return hp;
	}
	
private Widget createMenu() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("menuc");
		
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setStyleName("menu");
		hp.add(hp2);
		hp.setCellHorizontalAlignment(hp2, HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel hpLeft = new HorizontalPanel();
		hp2.add(hpLeft);
		
		
		
		
		
		bhome = new Button("HOME");
		bhome.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				MainComposit composit = new MainComposit();
				flex.setWidget(0, 1, composit);
				
			}
		});
		
		
		bhome.setStyleName("menub");
		
		hpLeft.add(bhome);
		
		bAbout = new Button("ABOUT");
		bAbout.setStyleName("menub");
		hpLeft.add(bAbout);
		bAbout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				AboutComposit composit = new AboutComposit();
				flex.setWidget(0, 1, composit);
				
			}
		});
		
		bcontact = new Button("CONTACT");
		bcontact.setStyleName("menub");
		hpLeft.add(bcontact);
		bcontact.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				ContactComposit composit = new ContactComposit();
				flex.setWidget(0, 1, composit);
				
			}
		});
		
		
		//Right
		HorizontalPanel hpRight = new HorizontalPanel();
		hp2.add(hpRight);
		hp2.setCellHorizontalAlignment(hpRight, HasHorizontalAlignment.ALIGN_RIGHT);
		
		bemail = new Button("");
		
		bemail.setStyleName("menub");
		bemail.addStyleName("menub2");
		hpRight.add(bemail);
		
		
		bacart = new Button("CART");
		bacart.setStyleName("menub");
		bacart.addStyleName("menub2");
		hpRight.add(bacart);
		bacart.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(isLoggedIn) {
					
					panel = new DecoratedPopupPanel();
					panel.setAnimationEnabled(true);
					panel.setAutoHideEnabled(true);
					
					VerticalPanel vp = new VerticalPanel();
					vp.setWidth("200px");
					
					FlexTable table = new FlexTable();
					table.setWidth("100%");
					table.setBorderWidth(1);
					table.setCellPadding(5);
					
					double total = 0.0;
					
					for(int i=0;i<listCart.size();i++) {
						
						ItemBean ibean = listCart.get(i);
						
						table.setWidget(i, 0, new HTML(ibean.getItemname()));
						table.setWidget(i, 1, new HTML(""+ibean.getPrice()));
						
						total = total + ibean.getPrice();
					}
					
					vp.add(table);
					
					vp.add(new HTML("<b>Total Amount: "+total+"</b>"));
					
					vp.add(new HTML("<br>Payment Mode: <b>Cash On Delivery</b>"));
					
					Button button = new Button("Clear Cart");
					button.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							
							listCart.clear();
							panel.setVisible(false);
							
						}
					});
					vp.add(button);
					
					Button button2 = new Button("Order");
					button2.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							
							
							
							if(listCart.size()==0) {
								
								Window.alert("Cart is empty!!!");
								
							}else {
								
								boolean isConfirmed = Window.confirm("Do you want Confirm Order");
								
								if(isConfirmed) {
									
																			
										greetingService.saveOrderServer(listCart, cuserBean, new AsyncCallback<String[]>() {
											
											@Override
											public void onSuccess(String[] result) {
												
												if(result[0].equals("true")) {
													
													listCart.clear();
													panel.setVisible(false);
													Window.alert("Order Sent Successfull:\n\nPayment Mode: Cash On Delivery");
													
													
												}else {
													
													ErrorBox box = new ErrorBox(result[1]);
													box.show();
													
												}
												
											}
											
											@Override
											public void onFailure(Throwable caught) {
												
												ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
												box.show();
												
											}
										});
									}
																	
									
								
							}
							
							
							
							
						}
					});
					vp.add(button2);
									
					panel.setWidget(vp);
					
					panel.showRelativeTo(bacart);
				}else {
					
					Window.alert("Login Please");
				}
				
				
				
				
				
			}
		});
		
		
		blogin = new Button("LOGIN");
		blogin.setStyleName("menub");
		blogin.addStyleName("menub2");
		hpRight.add(blogin);
		blogin.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(isLoggedIn) {
					
					cuserBean=null;
					blogin.setText("Login");
					bemail.setText("");
					isLoggedIn = false;
					
					
				}else {
					
					composit = new LoginComposit();
					composit.btnLogin.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							
							String email = composit.textBox_user.getText();
							String pass = composit.passwordTextBox.getText();
							
							cuserBean = new CuserBean();
							cuserBean.setEmailid(email);
							cuserBean.setPassword(pass);
									
							if(email.isEmpty()||pass.isEmpty()) {
								
								Window.alert("required field is not empty");
								
							}else {
								
								greetingService.loginServer(cuserBean, new AsyncCallback<String[]>() {
									
									@Override
									public void onSuccess(String[] result) {
										
										if(result[0].equals("true")) {
											
											listCart.clear();
											isLoggedIn = true;
											bemail.setText(cuserBean.getEmailid());
											blogin.setText("Logout");
											
											Window.alert("Login Successfull");
											composit.setVisible(false);
											
																	
										}else {
											
											bemail.setText("");
											ErrorBox box = new ErrorBox(result[1]);
											box.show();
											
										}
										
									}
									
									@Override
									public void onFailure(Throwable caught) {
										
										ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
										box.show();
										bemail.setText("");
										
									}
								});
							}
							
							
							
							
						}
					});
					composit.center();
					composit.show();
				}
				
				
				
			}
		});
		
		bregister = new Button("REGISTER");
		bregister.setStyleName("menub");
		bregister.addStyleName("menub2");
		hpRight.add(bregister);
		bregister.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				RegisterDialog dialog = new RegisterDialog();
				dialog.center();
				dialog.show();
				
			}
		});
		
		
		
		
		return hp;
	}
	
	private Widget createMain() {
				
		sp = new ScrollPanel();
		sp.addStyleName("myScroll");
		sp.setSize("100%", "100%");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSize("100%", "100%");
		
		//Main Widget
		flex = new FlexTable();
		flex.setStyleName("main");
		//flex.setBorderWidth(1);
		flex.setWidth("950px");
		hp.add(flex);
		hp.setCellHorizontalAlignment(flex, HasHorizontalAlignment.ALIGN_CENTER);
		sp.setWidget(hp);
		
		//Category
		vpCategory = new VerticalPanel();
		vpCategory.setStyleName("categoryP");
		vpCategory.setWidth("230px");
		vpCategory.setHeight("100%");
		flex.setWidget(0, 0, vpCategory);
		flex.setCellPadding(0);
		flex.getFlexCellFormatter().setWidth(0, 0, "230px");
		flex.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		
		//Category
		Label label = new Label("CATEGORIES");
		label.setStyleName("category");
		vpCategory.add(label);
		vpCategory.setCellVerticalAlignment(label, HasVerticalAlignment.ALIGN_TOP);
		
		loadCategory();
		
		
		//Slide
		MainComposit composit = new MainComposit();
		flex.setWidget(0, 1, composit);
		
		
		
		return sp;
	}
	
	private Widget createNorth() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("header");
				
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setStyleName("logo");
		hp.add(hp2);
		hp.setCellHorizontalAlignment(hp2, HasHorizontalAlignment.ALIGN_CENTER);
		
		hp2.add(new HTML(""));
		
		
		return hp;
	}
	
	private Widget createFooter() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("footer");
		
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setSize("950px", "42px");
		hp.add(hp2);
		hp.setCellHorizontalAlignment(hp2, HasHorizontalAlignment.ALIGN_CENTER);
		
		//Left
		HTML html = new HTML("FREE SHIPPING");
		html.setStyleName("free");
		
		hp2.add(html);
		
		//Right
		html = new HTML("&copy; 2013 Powered by Smart. All rights reserved");
		html.setStyleName("copyright");
		hp2.add(html);
		hp2.setCellHorizontalAlignment(html, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		
		return hp;
	}
	
	private void loadCategory() {
		
					
			greetingService.listItemtypeServer(new AsyncCallback<List<ItemtypeBean>>() {
				
				@Override
				public void onSuccess(List<ItemtypeBean> result) {
					
					
					list = result;
					//Window.alert(""+list.size());
					
					int i=0;
					for (Iterator<ItemtypeBean> iterator = result.iterator(); iterator
							.hasNext();) {
						ItemtypeBean entityBean = (ItemtypeBean) iterator.next();
						
						
						//From Database
						HorizontalPanel hp = new HorizontalPanel();
						hp.setStyleName("categoryH");
						
						Anchor anc = new Anchor(entityBean.getItemtypename());
						anc.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								
								Anchor a = (Anchor) event.getSource();
								int id = Integer.parseInt(a.getTitle());
								
								showItems(id);
								
							}
						});
						anc.setStyleName("categoryAnc");
						anc.setTitle(""+i);
						
						hp.add(anc);
						
						vpCategory.add(hp);
						i++;
						
						
						
					}
					
					//Images
					Image image = new Image("images/slide_00.jpg");
					vpCategory.add(image);
					
					image = new Image("images/slide_01.jpg");
					vpCategory.add(image);
					
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
		
	private void showItems(int id) {
		
		flex.setWidget(0, 1, new HTML(""));
		
		FlexTable flex2 = new FlexTable();
		flex2.setWidth("100%");
		
		bean = list.get(id);
		
		listItem = (List<ItemBean>) bean.getItemCollection();
		
		for(int i=0;i<listItem.size();i++) {
			
			itemBean = listItem.get(i);
			
			flex2.setWidget(i, 0, new Image("itemimages/"+itemBean.getItemid()+".jpg"));
			
			EHTML html = new EHTML(itemBean.getItemname());
			html.setStyleName("moreLink");
			html.itemId = itemBean.getItemid();
			html.longDesc = itemBean.getLongdesc();
			html.itemName = itemBean.getItemname();
			
			html.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					EHTML html = (EHTML) event.getSource();
					showMore(html.itemId, html.longDesc, html.itemName);
				}
			});
			
			flex2.setWidget(i, 1, html);
			flex2.setWidget(i, 2, new HTML(itemBean.getModelno()));
			flex2.setWidget(i, 3, new HTML(itemBean.getShortdesc()));
			flex2.setWidget(i, 4, new HTML(""+itemBean.getPrice()));
			
			Button button = new Button("Add to Cart");
			button.setTitle(""+i);
			flex2.setWidget(i, 5, button);
			button.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					if(isLoggedIn) {
					
						Button b = (Button) event.getSource();
						int itemId = Integer.parseInt(b.getTitle());
						
						listCart.add(listItem.get(itemId));
						
						Window.alert("added");
						
					}else {
						
						Window.alert("You need to Login First");
					}
					
					
					
					
				}
			});
			
		}
		
		flex.setWidget(0, 1, flex2);
		flex.getFlexCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
	}
	
	private void showMore(int id, String longdesc, String itemName) {
		
		if(dialog==null) {
			
			dialog = new DialogBox(true);
			//dialog.setSize("800px", "400px");
			dialog.setAnimationEnabled(true);
			dialog.setVisible(true);
			
			flex3 = new FlexTable();
			flex3.setSize("800px", "400px");
						
			
			dialog.setWidget(flex3);
		}
		
		dialog.setHTML(itemName);
		flex3.setWidget(0, 0, new Image("itemimages/"+id+"_large.jpg"));
		flex3.setWidget(0, 1,new HTML(longdesc));
		
		dialog.center();
		dialog.show();
		
	}
}
