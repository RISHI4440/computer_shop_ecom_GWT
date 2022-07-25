package com.admin.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class LoginPanel extends Composite {

	Anchor anc_signin, anc_register, anc_forgot;
	public Button button_login;
	public TextBox box_user;
	public PasswordTextBox box_password;
	VerticalPanel vpMain;
	
	public LoginPanel() {
		
		vpMain = new VerticalPanel();
		vpMain.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vpMain.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vpMain.setSize("100%", "100%");
		initWidget(vpMain);
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("topLine");
		vpMain.add(hp);
		vpMain.setCellHeight(hp, "4px");
		
		//form Panel
		VerticalPanel vp = new VerticalPanel();
		vp.setStyleName("login");
		
		FlexTable flexTable = new FlexTable();
		
		//flexTable.setStyleName("login");
		flexTable.setCellPadding(10);
		//flexTable.setSize("155px", "70px");
		FlexCellFormatter formatter = flexTable.getFlexCellFormatter();
		
		SimplePanel sp = new SimplePanel();
		sp.setStyleName("user");
		flexTable.setWidget(0, 1, sp);
		
		Label label = new Label("User ID");
		flexTable.setWidget(1, 0, label);
		formatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);

		box_user = new TextBox();
		box_user.setSize("200px", "20px");
		box_user.setStyleName("c-TextBox");
		flexTable.setWidget(1, 1, box_user);

		label = new Label("Password");
		flexTable.setWidget(2, 0, label);
		formatter.setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);

		box_password = new PasswordTextBox();
		box_password.setSize("200px", "20px");
		box_password.setStyleName("c-TextBox");
		flexTable.setWidget(2, 1, box_password);
		
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setSize("100%", "100%");
		
		
//		anc_forgot = new Anchor("Forgot Password?");
//		//flexTable.setWidget(3, 0, anc_forgot);
//		//formatter.setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_MIDDLE);
//		hp2.add(anc_forgot);
//		hp2.setCellVerticalAlignment(anc_forgot, HasVerticalAlignment.ALIGN_MIDDLE);
		
		button_login = new Button("Login");
		button_login.setWidth("60px");
		button_login.setStyleName("c-Button");
		button_login.addStyleName("round");
		hp2.add(button_login);
		hp2.setCellHorizontalAlignment(button_login, HasHorizontalAlignment.ALIGN_RIGHT);
		
		flexTable.setWidget(3, 1, hp2);
		//formatter.setAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		
		vp.add(flexTable);
		
		vpMain.add(vp);
		
		
	}

}
