package com.admin.client;


import com.client.ErrorBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Admin implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	DockLayoutPanel p;

	// header
	Anchor anc_signin, anc_register, anc_forgot;
	HorizontalPanel hp_setting, hp_logo, hp_settingl, hp_settingr;
	VerticalPanel vp;

	// footer
	Anchor anc_about, anc_terms, anc_privacy, anc_home;
	HorizontalPanel hp;
	PopupPanel popupLogin;
	boolean isLoggenIn;
	
	Anchor anc_company, anc_type, anc_reg, anc_item, anc_holder, anc_permit, anc_order, anc_regist, anc_reciept;
	MenuItem mi_cduer, mi_permr;

	// Navigation
	VerticalPanel vp_west, vp_east;
	LoginPanel loginPanel;

	public void onModuleLoad() {

			p = new DockLayoutPanel(Unit.PX);
			p.setStyleName("dock");

			loginPanel = new LoginPanel();
			loginPanel.button_login.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					
					
					if(loginPanel.box_user.getText().isEmpty()||loginPanel.box_password.getText().isEmpty()) {
						
						Window.alert("required field is not empty");
						
					}else {
						
						if(loginPanel.box_user.getText().equals("admin")&&loginPanel.box_password.getText().equals("admin")) {
							
							//remove logo
							RootPanel.get("logo").getElement().setInnerHTML("");
							
							p.remove(0);
							p.setStyleName("dock2");
							
							p.addNorth(createHeader(), 100);
							p.addSouth(createFooter(), 34);
							p.addWest(createWest(), 180);
							//p.addEast(createEast(), 150);

							ContentPanel cPanel = new ContentPanel();
							p.add(cPanel.createContent());
							
						}else {
							
							Window.alert("incorrect user or password");
						}
						
					}
					
					
					
				}
			});
			
			/* this code should be added */
			p.add(loginPanel);
			
			/* the code s hould be removed */
//			RootPanel.get("logo").getElement().setInnerHTML("");
//			p.setStyleName("dock2");
//			
//			p.addNorth(createHeader(), 100);
//			p.addSouth(createFooter(), 34);
//			p.addWest(createWest(), 180);
//			ContentPanel cPanel = new ContentPanel();
//			p.add(cPanel.createContent());

			RootLayoutPanel.get().add(p);
			

						 			
		}

	public Widget createHeader() {

		vp = new VerticalPanel();
		// vp.setWidth("100%");
		// vp.setHeight("70px");
		vp.setStyleName("header");
		// vp.setBorderWidth(1);

		

		// Logo Panel
		hp_logo = new HorizontalPanel();
		//hp_logo.setBorderWidth(1);
		hp_logo.setStyleName("logo");
		Image logo = new Image("images/logo.jpg");
		logo.setStyleName("mylogo");
		hp_logo.add(logo);

		// login
		anc_signin = new Anchor("Logout&nbsp;", true);
		anc_signin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// logout
				p.remove(0);
				p.remove(0);
				p.remove(0);
				p.remove(0);

				p.setStyleName("dock");
				loginPanel.vpMain.setSize("100%", "100%");
				loginPanel.vpMain
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				loginPanel.vpMain
						.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				p.add(loginPanel);

				RootPanel
						.get("logo")
						.getElement()
						.setInnerHTML(
								"<div id='bsaHolder'>S M A R T</div>");

			}
		});

		hp_logo.add(anc_signin);
		hp_logo.setCellHorizontalAlignment(anc_signin,
				HasHorizontalAlignment.ALIGN_RIGHT);
		// hp_logo.add(new HTML("&nbsp;"));

		vp.add(hp_logo);

		return vp;
	}

	// footer
	public Widget createFooter() {

		hp = new HorizontalPanel();
		hp.setWidth("100%");
		hp.setStyleName("footer");
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		// left
		HorizontalPanel hp1 = new HorizontalPanel();

		anc_about = new Anchor("Developed by xyz");
		anc_about.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				

				Window.open("#", "_blank", "");

			}
		});
		hp1.add(anc_about);

		hp1.add(new HTML("|"));

		

		hp.add(hp1);

		// right
		return hp;
	}

	public Widget createWest() {

		vp_west = new VerticalPanel();
		vp_west.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
		
		vp_west.setStyleName("navigation");

		
		Label label = new Label(" ADMIN MENU");
		label.setStyleName("heading2");
		vp_west.add(label);
		vp_west.setCellHeight(label, "35px");
		
		VerticalPanel vp = new VerticalPanel();
		vp_west.add(vp);
		
		//Customer Short Cut
		anc_company = new Anchor("[ C ] COMPANY");
		anc_company.setStyleName("myshort");
		anc_company.setAccessKey('C');
		
		anc_company.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				CompanyPanel panel = new CompanyPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		vp.add(anc_company);
		
		//Broker shrtcut
		anc_type = new Anchor("[ I ] ITEM TYPE");
		anc_type.setStyleName("myshort");
		anc_type.setAccessKey('T');
		
		anc_type.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				ItemTypePanel panel = new ItemTypePanel();
				p.add(panel.createWidget());
				
			}
		});
		
		vp.add(anc_type);
		
		//City
		anc_item = new Anchor("[ I ] ITEM");
		anc_item.setStyleName("myshort");
		anc_item.setAccessKey('I');
		
		anc_item.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				ItemPanel panel = new ItemPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		vp.add(anc_item);
		
		//city Short Ct
		anc_order = new Anchor("[ O ] ORDER");
		anc_order.setStyleName("myshort");
		anc_order.setAccessKey('Y');
		
		anc_order.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				OrderPanel panel = new OrderPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		vp.add(anc_order);
		
			
		

		return vp_west;
	}

	public Widget createEast() {

		vp_east = new VerticalPanel();
		// vp_east.setBorderWidth(1);
		// vp_east.setWidth("250px");
		// vp_east.setHeight("500px");

		vp_east.setStyleName("navigation");

		vp_east.add(new HTML("east"));

		return vp_east;
	}
}	

