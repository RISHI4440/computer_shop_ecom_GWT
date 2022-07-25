package com.client;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.shared.CuserBean;

public class RegisterDialog extends DialogBox{
	private TextBox textBox_email;
	private PasswordTextBox passwordTextBox;
	private TextBox textBox_name;
	private TextBox textBox_add;
	private TextBox textBox_city;
	private TextBox textBox_phone;
	private TextBox textBox_mobile;
	private Button btnNewButton_reg;
	private Button btnCancel;
	
	CuserBean bean;
	
	
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	public RegisterDialog() {
		setHTML("Register");
		
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		
		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(5);
		setWidget(flexTable);
		flexTable.setSize("400px", "100%");
		
		Label lblEmail = new Label("Email");
		flexTable.setWidget(0, 0, lblEmail);
		
		textBox_email = new TextBox();
		flexTable.setWidget(0, 1, textBox_email);
		textBox_email.setWidth("250px");
		
		Label lblPassword = new Label("Password");
		flexTable.setWidget(1, 0, lblPassword);
		
		passwordTextBox = new PasswordTextBox();
		flexTable.setWidget(1, 1, passwordTextBox);
		passwordTextBox.setWidth("250px");
		
		Label lblName = new Label("Name");
		flexTable.setWidget(2, 0, lblName);
		
		textBox_name = new TextBox();
		flexTable.setWidget(2, 1, textBox_name);
		textBox_name.setWidth("250px");
		
		Label lblAddress = new Label("Address");
		flexTable.setWidget(3, 0, lblAddress);
		
		textBox_add = new TextBox();
		flexTable.setWidget(3, 1, textBox_add);
		textBox_add.setWidth("250px");
		
		Label lblCity = new Label("City");
		flexTable.setWidget(4, 0, lblCity);
		
		textBox_city = new TextBox();
		flexTable.setWidget(4, 1, textBox_city);
		textBox_city.setWidth("250px");
		
		Label lblPhone = new Label("Phone");
		flexTable.setWidget(5, 0, lblPhone);
		
		textBox_phone = new TextBox();
		flexTable.setWidget(5, 1, textBox_phone);
		textBox_phone.setWidth("250px");
		
		Label lblMobile = new Label("Mobile");
		flexTable.setWidget(6, 0, lblMobile);
		
		textBox_mobile = new TextBox();
		flexTable.setWidget(6, 1, textBox_mobile);
		textBox_mobile.setWidth("250px");
		
		Grid grid = new Grid(1, 2);
		flexTable.setWidget(7, 1, grid);
		
		btnNewButton_reg = new Button("Register");
		btnNewButton_reg.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				register();
			}
		});
		grid.setWidget(0, 0, btnNewButton_reg);
		
		btnCancel = new Button("Cancel");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				setVisible(false);
			}
		});
		grid.setWidget(0, 1, btnCancel);
	}
	
	private void register() {
		
		if(textBox_email.getText().isEmpty()||textBox_name.getText().isEmpty()||passwordTextBox.getText().isEmpty()) {
			
			Window.alert("Required field cannot be empty");
			
		}else {
			
			bean = new CuserBean();
			bean.setAddress(textBox_add.getText());
			bean.setCity(textBox_city.getText());
			bean.setEmailid(textBox_email.getText());
			bean.setMobile(textBox_mobile.getText());
			bean.setPassword(passwordTextBox.getText());
			bean.setPhone(textBox_phone.getText());
			bean.setUsername(textBox_name.getText());
			
			
			greetingService.saveLoginServer(bean, new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						
						Window.alert("Register Successfull");
						
						
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
