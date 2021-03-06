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
package com.gwthotel.admintest.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.gwthotel.hotel.IClearHotel;
import com.gwthotel.hotel.IHotelObjectsFactory;
import com.gwthotel.hotel.bill.ICustomerBills;
import com.gwthotel.hotel.customer.IHotelCustomers;
import com.gwthotel.hotel.mailing.IHotelMailList;
import com.gwthotel.hotel.pricelist.IHotelPriceList;
import com.gwthotel.hotel.prices.IHotelPriceElem;
import com.gwthotel.hotel.reservation.IReservationForm;
import com.gwthotel.hotel.reservationop.IReservationOp;
import com.gwthotel.hotel.rooms.IHotelRooms;
import com.gwthotel.hotel.services.IHotelServices;
import com.gwthotel.shared.IHotelConsts;
import com.gwtmodel.testenhancer.ITestEnhancer;
import com.jython.serversecurity.IOObjectAdmin;
import com.jython.serversecurity.cache.IGetInstanceOObjectIdCache;
import com.jythonui.server.IJythonUIServer;
import com.jythonui.server.IXMLToMap;
import com.jythonui.server.getmess.IGetLogMess;
import com.jythonui.server.security.ISecurity;
import com.jythonui.server.storage.seq.ISequenceRealmGen;

/**
 * @author hotel
 * 
 */
public class ServiceInjector {

    private static final Injector injector;

    static {
        injector = Guice.createInjector(new ServerService.ServiceModule());
    }

    public static IOObjectAdmin constructHotelAdmin() {
        return injector.getInstance(IOObjectAdmin.class);
    }

    public static ITestEnhancer constructITestEnhancer() {
        return injector.getInstance(ITestEnhancer.class);
    }

    public static ISecurity constructSecurity() {
        return injector.getInstance(ISecurity.class);
    }

    public static IJythonUIServer contructJythonUiServer() {
        return injector.getInstance(IJythonUIServer.class);
    }

    public static IHotelServices getHotelServices() {
        return injector.getInstance(IHotelServices.class);
    }

    public static IGetLogMess getLogMess() {
        return injector.getInstance(Key.get(IGetLogMess.class,
                Names.named(IHotelConsts.MESSNAMED)));
    }

    public static IHotelPriceList getHotelPriceList() {
        return injector.getInstance(IHotelPriceList.class);
    }

    public static IHotelPriceElem getHotelPriceElem() {
        return injector.getInstance(IHotelPriceElem.class);
    }

    public static IHotelRooms getHotelRooms() {
        return injector.getInstance(IHotelRooms.class);
    }

    public static IHotelCustomers getHotelCustomers() {
        return injector.getInstance(IHotelCustomers.class);
    }

    public static IGetInstanceOObjectIdCache getInstanceObject() {
        return injector.getInstance(IGetInstanceOObjectIdCache.class);
    }

    public static ISequenceRealmGen getSequenceRealmGen() {
        return injector.getInstance(ISequenceRealmGen.class);
    }

    public static IHotelObjectsFactory getHotelObjects() {
        return injector.getInstance(IHotelObjectsFactory.class);
    }

    public static IReservationForm getReservationForm() {
        return injector.getInstance(IReservationForm.class);
    }

    public static IReservationOp getReservationOp() {
        return injector.getInstance(IReservationOp.class);
    }

    public static IClearHotel getClearHotel() {
        return injector.getInstance(IClearHotel.class);
    }

    public static IXMLToMap getXMLToMap() {
        return injector.getInstance(IXMLToMap.class);
    }

    public static ICustomerBills getCustomerBills() {
        return injector.getInstance(ICustomerBills.class);
    }

    public static IHotelMailList getHotelMail() {
        return injector.getInstance(IHotelMailList.class);
    }

}
