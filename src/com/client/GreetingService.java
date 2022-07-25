package com.client;

import java.util.List;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.CuserBean;
import com.shared.ItemBean;
import com.shared.ItemtypeBean;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	List<ItemtypeBean> listItemtypeServer();

	String[] saveLoginServer(CuserBean bean);

	String[] loginServer(CuserBean cuserBean);

	String[] saveOrderServer(List<ItemBean> listCart, CuserBean cuserBean);
}
