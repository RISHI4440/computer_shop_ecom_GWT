package com.admin.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContentPanel{
	

	//GreetingServiceAsync adminService = GWT.create(GreetingService.class);
	VerticalPanel vp;
	ScrollPanel sp;
	
	public Widget createContent() {
		
		sp = new ScrollPanel();
		sp.setStyleName("scroll");
		
		vp = new VerticalPanel();
		
		vp.setStyleName("map");
		vp.add(new HTML(""));
				
		sp.setWidget(vp);
		
		return sp; 
	}
}
