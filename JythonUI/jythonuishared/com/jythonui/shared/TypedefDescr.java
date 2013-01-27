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

import java.util.ArrayList;
import java.util.List;

public class TypedefDescr extends ElemDescription {

    public List<FieldItem> construct() {
        String id = getAttr(ICommonConsts.COMBOID);
        String dName = getDisplayName();
        List<FieldItem> fList = new ArrayList<FieldItem>();
        FieldItem f = new FieldItem();
        f.setId(id);
        fList.add(f);
        f = new FieldItem();
        f.setId(dName);
        fList.add(f);
        return fList;
    }

}