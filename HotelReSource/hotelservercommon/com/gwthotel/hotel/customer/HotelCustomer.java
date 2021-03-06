/*
 * Copyright 2015 stanislawbartkowski@gmail.com 
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
package com.gwthotel.hotel.customer;

import com.jythonui.server.ISharedConsts;
import com.jythonui.shared.PropDescription;

/**
 * @author hotel Customer description class Additional attributes:
 * 
 *         com.gwthotel.shared.IHotelConsts String CUSTOMERFIRSTNAMEPROP =
 *         "firstname"; String CUSTOMERSURNAMEPROP = "surname"; String
 *         CUSTOMERDOCNUMBPROP="docnumb"; String CUSTOMEREMAILPROP = "email";
 *         String CUSTOMERPHONE1PROP = "phone1"; String CUSTOMERPHONE2PROP =
 *         "phone2"; String CUSTOMERFAXPROP="fax"; String
 *         CUSTOMERCOUNTRYPROP="country"; String CUSTOMERSTREETPROP = "street";
 *         String CUSTOMERPOSTALCODEPROP = "postalcode"; String
 *         CUSTOMERCITYPROP="city"; String CUSTOMERREGIONPROP="region";
 *
 **/

public class HotelCustomer extends PropDescription {

    private static final long serialVersionUID = 1L;

    private char sex = ISharedConsts.MALEDICT;
    // important: should be initialized to not zero value
    // important: not space !!! different then space
    // breaks in case of hibernate
    private char doctype = ISharedConsts.DEFAULTID;

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public char getDoctype() {
        return doctype;
    }

    public void setDoctype(char doctype) {
        this.doctype = doctype;
    }

}
