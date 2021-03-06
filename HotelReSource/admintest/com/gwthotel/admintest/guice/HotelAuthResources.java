
/*
 * Copyright 2015 stanislawbartkowski@gmail.com 
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
package com.gwthotel.admintest.guice;

import com.jython.serversecurity.IOObjectAdmin;
import com.jythonui.server.getmess.IGetLogMess;
import com.jythonui.server.objectauth.IRealmResources;

public class HotelAuthResources implements IRealmResources {

    @Override
    public IOObjectAdmin getAdmin() {
        return ServiceInjector.constructHotelAdmin();
    }

    @Override
    public IGetLogMess getLogMess() {
        return ServiceInjector.getLogMess();
    }


}
