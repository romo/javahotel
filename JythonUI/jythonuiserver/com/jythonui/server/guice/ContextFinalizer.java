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
package com.jythonui.server.guice;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jythonui.server.UtilHelper;
import com.jythonui.server.holder.Holder;
import com.jythonui.server.logmess.ILogMess;

public class ContextFinalizer extends UtilHelper implements
        ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
    }

    public void contextDestroyed(ServletContextEvent sce) {
        infoMess(L(), ILogMess.FINALIZECONTEXT);
        Holder.releaseThredData();
    }
}