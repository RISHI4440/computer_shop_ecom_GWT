/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.admin.client;

import java.util.List;

import com.admin.shared.CompanyBean;
import com.admin.shared.ItemBean;
import com.admin.shared.ItemtypeBean;
import com.admin.shared.SorderBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MasterService")
public interface MasterService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static MasterServiceAsync instance;
		public static MasterServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(MasterService.class);
			}
			return instance;
		}
	}

	String[] saveCompanyServer(CompanyBean bean, boolean isUpdate);

	List<CompanyBean> listCompanyServer(int id);


	String[] saveItemTypeServer(ItemtypeBean bean, boolean isUpdate);

	List<ItemtypeBean> listItemTypeServer(int id);

	List<ItemBean> listItemServer(int id);

	String[] saveItemServer(ItemBean bean, boolean isUpdate);

	List<SorderBean> listOrderServer(int id);

	String[] delCompanyServer(String text);

	String[] delItemTypeServer(String text);

	String[] delItemServer(String text);
	


	
}
