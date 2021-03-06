/*
 * Copyright 2017 stanislawbartkowski@gmail.com  
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
package com.jythonui.server.dict.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.gwtmodel.table.common.CUtil;
import com.jythonui.server.Util;
import com.jythonui.server.dict.DictEntry;
import com.jythonui.server.dict.IGetLocalizedDict;

public class ListOfCountries implements IGetLocalizedDict {

    @Override
    public List<DictEntry> getList() {
        Locale inL;
        String lang = Util.getLocale();
        if (lang == null)
            inL = Locale.ENGLISH;
        else
            inL = new Locale(lang);
        List<DictEntry> cList = new ArrayList<DictEntry>();
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale l : locales) {
            String code = l.getCountry();
            String name = l.getDisplayCountry(inL);
            if (CUtil.EmptyS(code) || CUtil.EmptyS(name))
                continue;
            DictEntry c = new DictEntry();
            c.setName(code);
            c.setDescription(name);
            cList.add(c);
        }
        Collections.sort(cList, new Comparator<DictEntry>() {

            @Override
            public int compare(DictEntry o1, DictEntry o2) {
                return o1.getName().compareTo(o2.getName());
            }

        });
        return cList;
    }

}
