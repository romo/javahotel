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
package com.jythonui.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.gwtmodel.table.common.CUtil;

/**
 * @author hotel
 * 
 */
public abstract class ElemDescription implements Serializable {

    // important: not final !
    private Map<String, String> attr = new HashMap<String, String>();

    public void setAttr(String key, String value) {
        attr.put(key, value);
    }

    public String getAttr(String key) {
        return attr.get(key);
    }

    public String getId() {
        return getAttr(ICommonConsts.ID);
    }

    public void setId(String id) {
        setAttr(ICommonConsts.ID, id);
    }

    public String getDisplayName() {
        return getAttr(ICommonConsts.DISPLAYNAME);
    }

    public boolean eqId(String id) {
        return CUtil.EqNS(getId(), id);
    }

    protected boolean isAttr(String attr) {
        return getAttr(attr) != null;
    }

    public String getWidth() {
        return getAttr(ICommonConsts.WIDTH);
    }

    public String getHtmlId() {
        return getAttr(ICommonConsts.HTMLID);
    }

    public boolean isHtmlId() {
        return isAttr(ICommonConsts.HTMLID);
    }

    public boolean isSecAccess() {
        return isAttr(ICommonConsts.SECACCESS);
    }

    public String getSecAccess() {
        return getAttr(ICommonConsts.SECACCESS);
    }

    public boolean isSecReadOnly() {
        return isAttr(ICommonConsts.SECREADONLY);
    }

    public String getSecReadOnly() {
        return getAttr(ICommonConsts.SECREADONLY);
    }

}
