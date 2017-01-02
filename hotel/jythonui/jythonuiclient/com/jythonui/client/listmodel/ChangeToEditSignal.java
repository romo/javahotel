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
package com.jythonui.client.listmodel;

import com.gwtmodel.table.IDataType;
import com.gwtmodel.table.slotmodel.CustomObjectValue;
import com.gwtmodel.table.slotmodel.CustomStringDataTypeSlot;
import com.gwtmodel.table.slotmodel.CustomStringSlot;
import com.jythonui.client.util.PerformVariableAction.VisitList;
import com.jythonui.client.util.PerformVariableAction.VisitList.EditListMode;

class ChangeToEditSignal extends CustomObjectValue<VisitList.EditListMode> {

    ChangeToEditSignal(EditListMode value) {
        super(value);
    }

    private static final String CHANGE_EDIT_SIGNAL = ChangeToEditSignal.class
            .getName() + "CHANGE_EDIT_SIGNAL";

    static CustomStringSlot constructSignal(IDataType dType) {
        return new CustomStringDataTypeSlot(dType, CHANGE_EDIT_SIGNAL);
    }

}
