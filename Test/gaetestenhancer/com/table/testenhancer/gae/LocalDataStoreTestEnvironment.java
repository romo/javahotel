/*
 * Copyright 2013 stanislawbartkowski@gmail.com 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.table.testenhancer.gae;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocal;
import com.google.apphosting.api.ApiProxy;
import com.gwtmodel.testenhancer.ITestEnhancer;

public class LocalDataStoreTestEnvironment extends LocalServiceTestEnvironment implements ITestEnhancer {


    @Override
    public void beforeTest() {
        super.beforeTest();
        ApiProxyLocal proxy = (ApiProxyLocal) ApiProxy.getDelegate();
        proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY,
                Boolean.TRUE.toString());
    }

    @Override
    public void afterTest() {
        ApiProxyLocal proxy = (ApiProxyLocal) ApiProxy.getDelegate();
        LocalDatastoreService datastoreService = (LocalDatastoreService) proxy
                .getService("datastore_v3");
        datastoreService.clearProfiles();
        super.afterTest();
    }

}