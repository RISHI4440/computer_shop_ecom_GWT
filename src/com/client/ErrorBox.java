package com.client;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTML;

public class ErrorBox extends DialogBox{
	
	public HTML html;
	public ErrorBox(String s) {
		
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setStyleName("content");
		scrollPanel.setSize("400px", "329px");
				
		html = new HTML(s, true);
		scrollPanel.setWidget(html);
				
		setWidget(scrollPanel);
		center();
		
	}
	

	
}
