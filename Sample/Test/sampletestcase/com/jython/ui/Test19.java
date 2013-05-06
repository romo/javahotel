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
package com.jython.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jythonui.server.holder.Holder;
import com.jythonui.shared.DialogFormat;
import com.jythonui.shared.FieldItem;
import com.jythonui.shared.ListFormat;

public class Test19 extends TestHelper {

    @Test
    public void test1() {
        String mess = appMess.getMessN("MESS1");
        System.out.println(mess);
        assertEquals("app message", mess);
        Holder.SetLocale("xx");
        mess = appMess.getMessN("MESS1");
        System.out.println(mess);
        assertEquals("app message", mess);
        Holder.SetLocale("pl");
        mess = appMess.getMessN("MESS1");
        System.out.println(mess);
        assertEquals("app message", mess);
        mess = appMess.getMessN("MESSPL");
        System.out.println(mess);
        assertEquals("pl message", mess);
        System.out.println(Holder.getLocale());
        assertEquals("pl",Holder.getLocale());
    }
    
    
    @Test
    public void test2() {
        DialogFormat d = findDialog("test46.xml");
        assertNotNull(d);
        ListFormat li = d.findList("lista");
        assertNotNull(li);
        FieldItem i = DialogFormat.findE(li.getColumns(), "id");
        assertNotNull(i);
        assertEquals("changepassword",i.getDefValue());
        i = DialogFormat.findE(li.getColumns(), "name");
        assertNotNull(i);
        assertNull(i.getDefValue());
    }

}
