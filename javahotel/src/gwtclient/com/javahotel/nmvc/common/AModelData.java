/*
 * Copyright 2011 stanislawbartkowski@gmail.com 
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
package com.javahotel.nmvc.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gwtmodel.table.AVModelData;
import com.gwtmodel.table.FieldDataType;
import com.gwtmodel.table.IVField;
import com.javahotel.common.toobject.AbstractTo;
import com.javahotel.common.toobject.IField;

class AModelData extends AVModelData implements VModelData {

    private final AbstractTo a;

    AModelData(AbstractTo a) {
        this.a = a;
    }

    private IField toF(IVField fie) {
        VField f = (VField) fie;
        IField fi = f.getFie();
        return fi;
    }

    @Override
    public Object getF(IVField fie) {
        return a.getF(toF(fie));
    }

    @Override
    public void setF(IVField fie, Object val) {
        a.setF(toF(fie), val);
    }

    public AbstractTo getA() {
        return a;
    }

    @Override
    public List<IVField> getF() {
        List<IVField> eList = new ArrayList<IVField>();
        for (int i = 0; i < a.getT().length; i++) {
            IField fie = a.getT()[i];
            Class c = a.getT(fie);
            FieldDataType t;
            if (c == Date.class) {
                t = FieldDataType.constructDate();
            } else {
                t = FieldDataType.constructString();
            }
            eList.add(new VField(fie, t));
        }
        return eList;
    }

    @Override
    public boolean isValid(IVField fie) {
        if (!(fie instanceof VField)) {
            return false;
        }
        VField v = (VField) fie;
        IField[] aa = a.getT();
        for (int i = 0; i < aa.length; i++) {
            if (v.getFie() == aa[i]) {
                return true;
            }
        }
        return false;
    }
}