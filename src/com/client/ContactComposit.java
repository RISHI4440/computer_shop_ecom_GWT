package com.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;

public class ContactComposit extends Composite{
	public ContactComposit() {
		
		FlexTable flexTable = new FlexTable();
		initWidget(flexTable);
		flexTable.setSize("600px", "");
		
		Label lblNewLabel = new Label("CUSTOMER SERVICE - CONTACT US");
		lblNewLabel.setStyleName("heading");
		flexTable.setWidget(0, 0, lblNewLabel);
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		
		Label lblForQuestionsAbout = new Label("For questions about an order or for more information about our products.");
		lblForQuestionsAbout.setStyleName("sheading");
		flexTable.setWidget(1, 0, lblForQuestionsAbout);
		flexTable.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		
		Label lbladdress = new Label("Address");
		lbladdress.setStyleName("content");
		
		flexTable.setWidget(2, 0, lbladdress);
		
		Image image = new Image("images/contact.jpg");
		flexTable.setWidget(2, 1, image);
		
		HTML htmlNewHtml = new HTML("<b>Smart Computers Online Store</b>", true);
		htmlNewHtml.setStyleName("content");
		flexTable.setWidget(3, 0, htmlNewHtml);
		
		HTML htmlmalegaonCamp = new HTML("<b>Malegaon Camp Nashik - 423203</b>", true);
		htmlmalegaonCamp.setStyleName("content");
		flexTable.setWidget(4, 0, htmlmalegaonCamp);
		
		HTML html_1 = new HTML("<br>", true);
		flexTable.setWidget(6, 0, html_1);
		
		Label lblDevelopedBy = new Label("Developed By");
		lblDevelopedBy.setStyleName("content");
		flexTable.setWidget(7, 0, lblDevelopedBy);
		
		HTML htmlRushikeshRShirsat = new HTML("<b>Rushikesh R. Shirsat</b>", true);
		htmlRushikeshRShirsat.setStyleName("content");
		flexTable.setWidget(8, 0, htmlRushikeshRShirsat);
		
		HTML htmljitendraPPawar = new HTML("<b>Jitendra P. Pawar</b>", true);
		htmljitendraPPawar.setStyleName("content");
		flexTable.setWidget(9, 0, htmljitendraPPawar);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(8, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(10, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getFlexCellFormatter().setRowSpan(2, 1, 9);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		
		HTML htmlrajnandiniKhotharde = new HTML("<b>Rajnandini Khotharde</b>", true);
		flexTable.setWidget(10, 0, htmlrajnandiniKhotharde);
		
		HTML html = new HTML("", true);
		flexTable.setWidget(11, 0, html);
		html.setHeight("300px");
		FlexTableHelper.fixRowSpan(flexTable);
	}

}
