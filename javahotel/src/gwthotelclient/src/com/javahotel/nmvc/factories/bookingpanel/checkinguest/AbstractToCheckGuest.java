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
package com.javahotel.nmvc.factories.bookingpanel.checkinguest;

import com.gwtmodel.table.IVField;
import com.javahotel.client.abstractto.Compose3AbstractTo;
import com.javahotel.common.toobject.BookElemP;
import com.javahotel.common.toobject.CustomerP;
import com.javahotel.common.toobject.GuestP;
import com.javahotel.common.toobject.IField;
import com.javahotel.common.toobject.ResObjectP;

/**
 * @author hotel
 * 
 */
@SuppressWarnings("serial")
class AbstractToCheckGuest extends
        Compose3AbstractTo<ResObjectP, CustomerP, GuestP> {

    @SuppressWarnings("unused")
    private final BookElemP bElem;

    private boolean editable = false;

    /**
     * @return the editable
     */
    boolean isEditable() {
        return editable;
    }

    /**
     * @param editable
     *            the editable to set
     */
    void setEditable(boolean editable) {
        this.editable = editable;
    }

    enum F implements IField {
        DrawC, ChooseC
    };

    static String buttonString = "CUSTOMER-SHOW-DETAIL";
    static String chooseCust = "CUSTOMER-CHOOSE-BUTTON";

    @Override
    public Object getF(IField fie) {
        if (fie instanceof F) {
            F f = (F) fie;
            switch (f) {
            case DrawC:
                return "Pokaż";
            case ChooseC:
                return "Wybierz";
            }
        }
        return super.getF(fie);
    }

    AbstractToCheckGuest(BookElemP bElem, ResObjectP o1, CustomerP o2, GuestP o3) {
        super(o1, o2, o3);
        this.bElem = bElem;
    }

}