package com.admin.client;

import java.util.List;


import com.admin.shared.CompanyBean;
import com.admin.shared.ItemBean;
import com.admin.shared.ItemtypeBean;
import com.admin.shared.SorderBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MasterServiceAsync {

	void saveCompanyServer(CompanyBean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void listCompanyServer(int id, AsyncCallback<List<CompanyBean>> asyncCallback);
//
//	void openCompanyServer(String cityName, AsyncCallback<CompanyBean> callback);
//
//	void searchCompanyServer(String q,
//			AsyncCallback<List<CompanyBean>> asyncCallback);
//
//	void listItemServer(int id, AsyncCallback<List<ItemBean>> asyncCallback);
//
//	void openItemServer(String q, AsyncCallback<ItemBean> asyncCallback);
//
//	void searchItemServer(String q,
//			AsyncCallback<List<ItemBean>> asyncCallback);
//
//	void saveItemServer(ItemBean bean, boolean isUpdate,
//			AsyncCallback<String[]> asyncCallback);
//
//	void listCustomerServer(int id,
//			AsyncCallback<List<CustomerBean>> asyncCallback);
//
//	void openCustomerServer(String q,
//			AsyncCallback<CustomerBean> asyncCallback);
//
//	void searchCustomerServer(String q,
//			AsyncCallback<List<CustomerBean>> asyncCallback);
//
//	void saveCustomerServer(CustomerBean bean, boolean isUpdate,
//			AsyncCallback<String[]> asyncCallback);
//
//	void listBrokerServer(int id, AsyncCallback<List<BrokerBean>> asyncCallback);
//
//	void openBrokerServer(String q,
//			AsyncCallback<BrokerBean> asyncCallback);
//
//	void searchBrokerServer(String q,
//			AsyncCallback<List<BrokerBean>> asyncCallback);
//
//	void saveBrokerServer(BrokerBean bean, boolean isUpdate,
//			AsyncCallback<String[]> asyncCallback);
//
//	void listPaperTypeServer(int id,
//			AsyncCallback<List<PaperBean>> asyncCallback);
//
//	void openPaperTypeServer(String q,
//			AsyncCallback<PaperBean> asyncCallback);
//
//	void searchPaperTypeServer(String q,
//			AsyncCallback<List<PaperBean>> asyncCallback);
//
//	void savePaperTypeServer(PaperBean bean, boolean isUpdate,
//			AsyncCallback<String[]> asyncCallback);
//
//	void openPermitServer(String upperCase,
//			AsyncCallback<PermitBean> asyncCallback);
//
//	void listPermitServer(int id, AsyncCallback<List<PermitBean>> asyncCallback);
//
//	void searchPermitServer(String upperCase,
//			AsyncCallback<List<PermitBean>> asyncCallback);
//
//	void savePermitServer(PermitBean bean, boolean isUpdate,
//			AsyncCallback<String[]> asyncCallback);

	void saveItemTypeServer(ItemtypeBean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void listItemTypeServer(int id,
			AsyncCallback<List<ItemtypeBean>> asyncCallback);

	void listItemServer(int id, AsyncCallback<List<ItemBean>> asyncCallback);

	void saveItemServer(ItemBean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void listOrderServer(int id, AsyncCallback<List<SorderBean>> asyncCallback);

	void delCompanyServer(String text, AsyncCallback<String[]> asyncCallback);

	void delItemTypeServer(String text, AsyncCallback<String[]> asyncCallback);

	void delItemServer(String text, AsyncCallback<String[]> asyncCallback);


}
