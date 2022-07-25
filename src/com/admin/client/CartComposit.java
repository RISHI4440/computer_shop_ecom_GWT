package com.admin.client;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Button;

public class CartComposit extends DialogBox{
	public TextBox textBox_user;
	public PasswordTextBox passwordTextBox;
	public Button btnLogin;
	public CartComposit() {
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(5);
		setWidget(flexTable);
		flexTable.setSize("400px", "100%");
		
		Label lblNewLabel = new Label("Email:");
		flexTable.setWidget(0, 0, lblNewLabel);
		
		textBox_user = new TextBox();
		flexTable.setWidget(0, 1, textBox_user);
		textBox_user.setWidth("250px");
		
		Label lblPassword = new Label("Password");
		flexTable.setWidget(1, 0, lblPassword);
		
		passwordTextBox = new PasswordTextBox();
		flexTable.setWidget(1, 1, passwordTextBox);
		passwordTextBox.setWidth("250px");
		
		btnLogin = new Button("Login");
		flexTable.setWidget(2, 1, btnLogin);
	}

}
