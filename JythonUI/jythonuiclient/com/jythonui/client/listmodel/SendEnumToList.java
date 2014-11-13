/*
 * Copyright 2014 stanislawbartkowski@gmail.com 
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
package com.jythonui.client.listmodel;

import com.gwtmodel.table.IDataListType;
import com.gwtmodel.table.IDataType;
import com.gwtmodel.table.slotmodel.CustomObjectValue;
import com.gwtmodel.table.slotmodel.CustomStringDataTypeSlot;
import com.gwtmodel.table.slotmodel.CustomStringSlot;

class SendEnumToList extends CustomObjectValue<IDataListType> {

    private final String customT;

    SendEnumToList(String customT, IDataListType i) {
        super(i);
        this.customT = customT;
    }

    private static final String SIGNAL_ID = SendEnumToList.class.getName()
            + "SEND_ENUM_LIST";

    static CustomStringSlot constructSignal(IDataType dType) {
        return new CustomStringDataTypeSlot(dType, SIGNAL_ID);
    }

    String getCustomT() {
        return customT;
    }

}
