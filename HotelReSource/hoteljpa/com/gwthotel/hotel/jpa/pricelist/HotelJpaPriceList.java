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
package com.gwthotel.hotel.jpa.pricelist;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.gwthotel.hotel.HotelObjects;
import com.gwthotel.hotel.jpa.HotelAbstractJpaCrud;
import com.gwthotel.hotel.jpa.JUtils;
import com.gwthotel.hotel.jpa.entities.EHotelPriceList;
import com.gwthotel.hotel.pricelist.HotelPriceList;
import com.gwthotel.hotel.pricelist.IHotelPriceList;
import com.jython.jpautil.crudimpl.gensym.IJpaObjectGenSymFactory;
import com.jython.serversecurity.cache.OObjectId;
import com.jython.ui.server.jpatrans.ITransactionContextFactory;

public class HotelJpaPriceList extends
        HotelAbstractJpaCrud<HotelPriceList, EHotelPriceList> implements
        IHotelPriceList {

    @Inject
    public HotelJpaPriceList(ITransactionContextFactory eFactory,
            IJpaObjectGenSymFactory iGen) {
        super(new String[] { "findAllPriceLists", "findOnePriceList" },
                eFactory, HotelObjects.PRICELIST, iGen, EHotelPriceList.class);
    }

    @Override
    protected HotelPriceList toT(EHotelPriceList sou, EntityManager em,
            OObjectId hotel) {
        HotelPriceList ho = new HotelPriceList();
        ho.setFromDate(sou.getPriceFrom());
        ho.setToDate(sou.getPriceTo());
        return ho;
    }

    @Override
    protected void toE(EHotelPriceList dest, HotelPriceList sou,
            EntityManager em, OObjectId hotel) {
        dest.setPriceFrom(sou.getFromDate());
        dest.setPriceTo(sou.getToDate());
    }

    @Override
    protected void beforedeleteElem(EntityManager em, OObjectId hotel,
            EHotelPriceList elem) {
        String[] queryL = { "deletePricesForHotelAndPriceList" };
        JUtils.runQueryForObject(em, elem, queryL);
    }

    @Override
    protected void afterAddChange(EntityManager em, OObjectId hotel,
            HotelPriceList prop, EHotelPriceList elem, boolean add) {

    }

}
