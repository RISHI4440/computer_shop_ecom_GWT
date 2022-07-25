package com.client;

import java.util.List;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.CuserBean;
import com.shared.ItemBean;
import com.shared.ItemtypeBean;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void listItemtypeServer(AsyncCallback<List<ItemtypeBean>> asyncCallback);

	void saveLoginServer(CuserBean bean, AsyncCallback<String[]> asyncCallback);

	void loginServer(CuserBean cuserBean,
			AsyncCallback<String[]> asyncCallback);

	void saveOrderServer(List<ItemBean> listCart, CuserBean cuserBean,
			AsyncCallback<String[]> asyncCallback);
}
