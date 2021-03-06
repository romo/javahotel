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
package com.gwthotel.admintest.suite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.gwthotel.hotel.HotelObjects;
import com.gwthotel.hotel.customer.HotelCustomer;
import com.gwthotel.hotel.reservation.ReservationForm;
import com.gwthotel.hotel.reservation.ReservationPaymentDetail;
import com.gwthotel.hotel.rooms.HotelRoom;
import com.gwthotel.hotel.services.HotelServices;
import com.gwtmodel.table.common.DateFormat;

public class Test12 extends TestHelper {


    @Test
    public void test1() {
        HotelCustomer p = (HotelCustomer) hObjects.construct(getH(HOTEL),
                HotelObjects.CUSTOMER);
        p = iCustomers.addElem(getH(HOTEL), p);
        ReservationForm r = (ReservationForm) hObjects.construct(getH(HOTEL),
                HotelObjects.RESERVATION);
        r.setCustomerName(p.getName());
        r = iRes.addElem(getH(HOTEL), r);
        System.out.println(r.getName());
        assertNotNull(r.getName());
        List<ReservationForm> rList = iRes.getList(getH(HOTEL));
        assertEquals(1, rList.size());
        r = rList.get(0);
        assertNotNull(r.getName());
        assertEquals(p.getName(), r.getCustomerName());
    }

    @Test
    public void test2() {
        HotelCustomer p = (HotelCustomer) hObjects.construct(getH(HOTEL),
                HotelObjects.CUSTOMER);
        p = iCustomers.addElem(getH(HOTEL), p);

        HotelServices se = new HotelServices();
        se.setName("1p1");
        se.setNoPersons(1);
        se.setVat("7%");
        iServices.addElem(getH(HOTEL), se);

        HotelRoom ro = new HotelRoom();
        ro.setName("99");
        ro.setNoPersons(1);
        iRooms.addElem(getH(HOTEL), ro);

        ReservationForm r = (ReservationForm) hObjects.construct(getH(HOTEL),
                HotelObjects.RESERVATION);
        Date res = DateFormat.toD(2013, 2, 1);
        r.setCustomerName(p.getName());

        ReservationPaymentDetail det = new ReservationPaymentDetail();
        det.setNoP(1);
        det.setRoomName("99");
        det.setResDate(res);
        det.setPrice(new BigDecimal(100));
        det.setService("1p1");
        det.setPriceTotal(new BigDecimal(100));
        r.getResDetail().add(det);

        r = iRes.addElem(getH(HOTEL), r);
        System.out.println(r.getName());
        assertNotNull(r.getName());

        List<ReservationForm> rList = iRes.getList(getH(HOTEL));
        assertEquals(1, rList.size());
        r = rList.get(0);
        assertNotNull(r.getName());
        assertEquals(p.getName(), r.getCustomerName());
        assertEquals(1, r.getResDetail().size());
        det = r.getResDetail().get(0);
        assertEqB(100.00, det.getPrice());
        assertEquals("1p1", det.getService());
        assertEquals("99", det.getRoomName());

        // now change
        ro = new HotelRoom();
        ro.setName("10");
        ro.setNoPersons(1);
        iRooms.addElem(getH(HOTEL), ro);

        det.setRoomName("10");
        det.setPrice(new BigDecimal(200));
        iRes.changeElem(getH(HOTEL), r);

        rList = iRes.getList(getH(HOTEL));
        assertEquals(1, rList.size());
        r = rList.get(0);
        det = r.getResDetail().get(0);
        // assertEquals(HUtils.roundB(new BigDecimal(200)), det.getPrice());
        assertEqB(200, det.getPrice());
        assertEquals("1p1", det.getService());
        assertEquals("10", det.getRoomName());
        assertEquals(1, det.getNoP());

        // now remove
        iRes.deleteElem(getH(HOTEL), r);
        rList = iRes.getList(getH(HOTEL));
        assertTrue(rList.isEmpty());
    }

    @Test
    public void test3() {
        for (int i = 0; i < 100; i++) {
            HotelCustomer p = (HotelCustomer) hObjects.construct(getH(HOTEL),
                    HotelObjects.CUSTOMER);
            p.setName("CUST" + i);
//            System.out.println(p.getName());
            p = iCustomers.addElem(getH(HOTEL), p);
        }

        for (int i = 0; i < 100; i++) {
            HotelRoom ro = new HotelRoom();
            ro.setName("NO" + i);
            ro.setNoPersons(i);
            iRooms.addElem(getH(HOTEL), ro);
        }

        for (int i = 0; i < 100; i++) {
            HotelServices se = new HotelServices();
            se.setName("SE" + i);
            se.setNoPersons(1);
            se.setVat("7%");
            iServices.addElem(getH(HOTEL), se);
        }

        Date res = DateFormat.toD(2013, 2, 1);
        for (int i = 0; i < 100; i++) {
            ReservationForm r = (ReservationForm) hObjects.construct(
                    getH(HOTEL), HotelObjects.RESERVATION);
            r.setCustomerName("CUST" + i);
            // Calendar c = Calendar.getInstance();
            // r.setName("RES"+i);
            for (int j = 0; j < 100; j++) {
                // c.setTime(res);
                // c.add(Calendar.DATE, 1);
                res = incDay(res);
                ReservationPaymentDetail det = new ReservationPaymentDetail();
                det.setNoP(1);
                det.setResDate(res);
                det.setPrice(new BigDecimal(100 * j * i));
                det.setPriceTotal(det.getPrice());
                det.setService("SE" + j);
                det.setRoomName("NO" + j);
                r.getResDetail().add(det);
            }
            iRes.addElem(getH(HOTEL), r);
        }
        List<ReservationForm> rList = iRes.getList(getH(HOTEL));
        assertEquals(100, rList.size());
        ReservationForm r = rList.get(0);
        assertEquals(100, r.getResDetail().size());
        String rName = r.getName();
        r.getResDetail().clear();
        ReservationPaymentDetail det = new ReservationPaymentDetail();
        det.setNoP(1);
        det.setResDate(res);
        det.setPrice(new BigDecimal(500));
        det.setPriceTotal(new BigDecimal(500));
        det.setService("SE0");
        det.setRoomName("NO0");
        r.getResDetail().add(det);
        iRes.changeElem(getH(HOTEL), r);

        rList = iRes.getList(getH(HOTEL));
        r = null;
        for (ReservationForm rr : rList) {
            if (rr.getName().equals(rName)) {
                r = rr;
                break;
            }
        }
        assertNotNull(r);
        assertEquals(1, r.getResDetail().size());
        iRes.deleteElem(getH(HOTEL), r);
        rList = iRes.getList(getH(HOTEL));
        assertEquals(99, rList.size());
    }

}
