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
package com.gwthotel.hotel.service.gae;

import static com.googlecode.objectify.ObjectifyService.ofy;

import javax.inject.Inject;

import com.googlecode.objectify.ObjectifyService;
import com.gwthotel.hotel.HotelObjects;
import com.gwthotel.hotel.pricelist.HotelPriceList;
import com.gwthotel.hotel.pricelist.IHotelPriceList;
import com.gwthotel.hotel.service.gae.crud.HotelCrudGaeAbstract;
import com.gwthotel.hotel.service.gae.entities.EHotelPriceElem;
import com.gwthotel.hotel.service.gae.entities.EHotelPriceList;
import com.jython.ui.server.gae.security.entities.EObject;
import com.jythonui.server.crud.ICrudObjectGenSym;

public class HotelPriceListImpl extends
        HotelCrudGaeAbstract<HotelPriceList, EHotelPriceList> implements
        IHotelPriceList {

    static {
        ObjectifyService.register(EHotelPriceList.class);
    }

    @Inject
    public HotelPriceListImpl(ICrudObjectGenSym iGen) {
        super(EHotelPriceList.class, HotelObjects.PRICELIST, iGen);
    }

    @Override
    protected HotelPriceList constructProp(EObject ho, EHotelPriceList e) {
        HotelPriceList pr = new HotelPriceList();
        pr.setFromDate(e.getPriceFrom());
        pr.setToDate(e.getPriceTo());
        return pr;
    }

//    @Override
//    protected EHotelPriceList constructE() {
//        return new EHotelPriceList();
//    }

    @Override
    protected void toE(EObject ho, EHotelPriceList e, HotelPriceList t) {
        e.setPriceFrom(t.getFromDate());
        e.setPriceTo(t.getToDate());
    }

    @Override
    protected void beforeDelete(DeleteItem i, EObject ho, EHotelPriceList elem) {
        if (elem != null) {
            i.pList = ofy().load().type(EHotelPriceElem.class).ancestor(ho)
                    .filter("pricelistName == ", elem.getName()).list();
        } else {
            i.readAllPriceElems(ho);
        }

    }

}
