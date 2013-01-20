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
package com.gwtmodel.table.view.util;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.gwtmodel.table.WSize;

/**
 * @author hotel
 * 
 */
public class EventPopUpHint extends PopUpHint {

    public void onBrowser(Element elem, NativeEvent event) {
        String na = event.getType();
        if (na.equals(IEventName.MOUSEOVER)) {
            actionOver(new WSize(elem));
        } else if (na.equals(IEventName.MOUSEOUT)) {
            actionOut();
        }
    }
}
