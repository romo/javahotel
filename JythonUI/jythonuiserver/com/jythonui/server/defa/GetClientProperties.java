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
package com.jythonui.server.defa;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jythonui.server.IJythonClientRes;
import com.jythonui.server.logmess.IErrorCode;
import com.jythonui.server.logmess.ILogMess;
import com.jythonui.server.logmess.LogMess;
import com.jythonui.shared.ICommonConsts;

public class GetClientProperties implements IJythonClientRes {

    static final private Logger log = Logger
            .getLogger(GetClientProperties.class.getName());

    @Override
    public Map<String, String> getClientRes() {
        InputStream i = GetClientProperties.class.getClassLoader()
                .getResourceAsStream(ICommonConsts.APP_FILENAME);
        if (i == null) {
            log.log(Level.SEVERE,LogMess.getMess(IErrorCode.ERRORCODE1, ILogMess.CANNOTFINDRESOURCEFILE,ICommonConsts.APP_FILENAME));
            return null;
        }
        Properties prop = new Properties();
        try {
            prop.load(i);
        } catch (IOException e) {
            log.log(Level.SEVERE, LogMess.getMess(IErrorCode.ERRORCODE2, ILogMess.ERRORWHILEREADINGRESOURCEFILE, ICommonConsts.APP_FILENAME), e);
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        Enumeration e = prop.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            map.put(key, prop.getProperty(key));
        }
        return map;
    }
}