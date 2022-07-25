package com.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;

public class AboutComposit extends Composite{
	public AboutComposit() {
		
		FlexTable flexTable = new FlexTable();
		initWidget(flexTable);
		flexTable.setSize("600px", "");
		
		Label lblNewLabel = new Label("ABOUT US");
		lblNewLabel.setStyleName("heading");
		flexTable.setWidget(0, 0, lblNewLabel);
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		
		HTML htmlOurPhilosophyIs = new HTML("Our philosophy is all about professional entrepreneurship and perfectly serving our clients. Since the early days our team extends its passion to sell premium quality goods for your comfortable living. Through the years we've managed to create one of the most famous brands in our country and now we are glad to share our products online with you. Our mission is not about gaining huge profits, we care about the people because by choosing our store you are giving us a chance to change a small part of your world and we are happy to do that professionally and passionately.<br><br>Our business is growing day by day and we are glad to announce another milestone which is signified by the launch of our web store - another wonderful way to browse and enjoy the best commodities from our brand. Being an international store is a great challenge for our firm but we will take it with honor and dignity. We can promise you that you will feel all advantages of online shopping because this is way better and more comfortable than the traditional one.", true);
		htmlOurPhilosophyIs.setStyleName("content");
		flexTable.setWidget(1, 0, htmlOurPhilosophyIs);
		
		HTML html = new HTML("", true);
		flexTable.setWidget(11, 0, html);
		html.setHeight("300px");
		FlexTableHelper.fixRowSpan(flexTable);
	}

}
