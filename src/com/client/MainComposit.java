package com.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

public class MainComposit extends Composite{
	
	Image slides[] = new Image[3];
		
	
	public MainComposit() {
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("maina");
		flexTable.setCellPadding(0);
		flexTable.setCellSpacing(0);
		initWidget(flexTable);
		//flexTable.setWidth("708px");
		
		slides[0] = new Image("images/1.jpg");
		slides[1] = new Image("images/2.jpg");
		slides[2] = new Image("images/3.jpg");
		
		flexTable.setWidget(0, 0, slides[0]);
		
		flexTable.setWidget(1, 0, new Image("images/11.jpg"));
	}

}
