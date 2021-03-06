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
package com.gwthotel.hotel.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gwthotel.hotel.HUtils;
import com.gwthotel.hotel.HotelObjects;
import com.gwthotel.hotel.bill.CustomerBill;
import com.gwthotel.hotel.jpa.entities.EBillPayment;
import com.gwthotel.hotel.jpa.entities.ECustomerBill;
import com.gwthotel.hotel.jpa.entities.EHotelCustomer;
import com.gwthotel.hotel.jpa.entities.EHotelMail;
import com.gwthotel.hotel.jpa.entities.EHotelPriceList;
import com.gwthotel.hotel.jpa.entities.EHotelReservation;
import com.gwthotel.hotel.jpa.entities.EHotelReservationDetail;
import com.gwthotel.hotel.jpa.entities.EHotelRoom;
import com.gwthotel.hotel.jpa.entities.EHotelServices;
import com.gwthotel.hotel.reservation.ReservationPaymentDetail;
import com.gwthotel.hotel.rooms.HotelRoom;
import com.gwthotel.hotel.services.HotelServices;
import com.gwthotel.shared.IHotelConsts;
import com.gwtmodel.table.common.CUtil;
import com.jython.jpautil.JpaUtils;
import com.jython.serversecurity.cache.OObjectId;
import com.jython.serversecurity.jpa.PropUtils;
import com.jythonui.server.BUtil;
import com.jythonui.server.RUtils;
import com.jythonui.server.UtilHelper;
import com.jythonui.server.logmess.IErrorCode;
import com.jythonui.server.logmess.ILogMess;

public class JUtils extends UtilHelper {

    private JUtils() {
    }

    public static HotelServices toT(EHotelServices sou) {
        HotelServices ho = new HotelServices();
        PropUtils.copyToProp(ho, sou);
        ho.setNoPersons(sou.getNoPersons());
        ho.setAttr(IHotelConsts.VATPROP, sou.getVat());
        ho.setServiceType(sou.getServiceType());
        ho.setNoChildren(sou.getNoChildren());
        ho.setNoExtraBeds(sou.getNoExtraBeds());
        ho.setPerperson(sou.isPerperson());

        return ho;
    }

    public static void copyE(EHotelRoom dest, HotelRoom sou) {
        dest.setNoPersons(sou.getNoPersons());
    }

    public static void removeList(EntityManager em, Object o, String query) {
        Query q = JpaUtils.createObjectQuery(em, o, query);

        @SuppressWarnings({ "rawtypes" })
        List list = q.getResultList();
        for (Object e : list)
            em.remove(e);
        em.flush();
        // Hibernate:
        // Important: otherwise consecutive deletes do not see the change
    }

    public static EHotelServices findService(EntityManager em, OObjectId hotel,
            String s) {
        return JpaUtils.getElemE(em, hotel, "findOneService", s);
    }

    public static EHotelPriceList findPriceList(EntityManager em,
            OObjectId hotel, String s) {
        return JpaUtils.getElemE(em, hotel, "findOnePriceList", s);
    }

    public static EHotelCustomer findCustomer(EntityManager em,
            OObjectId hotel, String s) {
        EHotelCustomer cu = JpaUtils.getElemE(em, hotel, "findOneCustomer", s);
        return cu;
    }

    public static EHotelRoom findRoom(EntityManager em, OObjectId hotel,
            String s) {
        EHotelRoom room = JpaUtils.getElemE(em, hotel, "findOneRoom", s);
        return room;
    }

    public static EHotelReservation findReservation(EntityManager em,
            OObjectId hotel, String s) {
        EHotelReservation res = JpaUtils.getElemE(em, hotel,
                "findOneReservation", s);
        return res;
    }

    public static void runQueryForObject(EntityManager em, Object o,
            String... remQuery) {
        for (String r : remQuery) {
            Query q = JpaUtils.createObjectQuery(em, o, r);
            // q.setParameter(1, o);
            q.executeUpdate();
        }
    }

    public static void runQueryForHotels(EntityManager em, OObjectId hotel,
            String... remQuery) {
        runQueryForObject(em, hotel.getId(), remQuery);
    }

    public static void ToReservationDetails(ReservationPaymentDetail det,
            EHotelReservationDetail r) {
        det.setNoP(r.getNoP());
        det.setPrice(r.getPrice());
        det.setPriceList(r.getPriceList());
        det.setNoChildren(r.getNoChildren());
        det.setPriceChildren(r.getPriceChildren());
        det.setPriceListChildren(r.getPriceListChildren());
        det.setNoExtraBeds(r.getNoExtraBeds());
        det.setPriceExtraBeds(r.getPriceExtraBeds());
        det.setPriceListExtraBeds(r.getPriceListExtraBeds());
        det.setPriceTotal(r.getTotal());
        det.setResDate(r.getResDate());
        det.setServiceType(r.getServiceType());
        det.setId(r.getId());
        det.setVat(r.getServicevat());
        det.setDescription(r.getDescription());
        det.setPerperson(r.isPerperson());

        if (r.getRoom() != null)
            det.setRoomName(r.getRoom().getName());
        if (r.getService() != null)
            det.setService(r.getService().getName());
        if (r.getCustomer() != null)
            det.setGuestName(r.getCustomer().getName());
        if (r.getPriceListName() != null)
            det.setPriceListName(r.getPriceListName().getName());
    }

    public static void ToEReservationDetails(EntityManager em, OObjectId hotel,
            EHotelReservationDetail dest, ReservationPaymentDetail sou) {
        String roomName = sou.getRoomName();
        if (!CUtil.EmptyS(roomName)) {
            EHotelRoom room = findRoom(em, hotel, roomName);
            dest.setRoom(room);
        }
        String serviceName = sou.getService();
        if (!CUtil.EmptyS(serviceName)) {
            EHotelServices serv = JUtils.findService(em, hotel, serviceName);
            dest.setService(serv);
        }
        String priceListName = sou.getPriceListName();
        if (!CUtil.EmptyS(priceListName)) {
            EHotelPriceList ePrice = JUtils.findPriceList(em, hotel,
                    priceListName);
            dest.setPriceListName(ePrice);
        }
        String custName = sou.getGuestName();
        if (!CUtil.EmptyS(custName)) {
            EHotelCustomer cust = findCustomer(em, hotel, custName);
            dest.setCustomer(cust);

        }
        dest.setNoP(sou.getNoP());
        dest.setPrice(HUtils.roundB(sou.getPrice()));
        dest.setPriceList(HUtils.roundB(sou.getPriceList()));
        dest.setNoChildren(sou.getNoChildren());
        dest.setPriceChildren(HUtils.roundB(sou.getPriceChildren()));
        dest.setPriceListChildren(HUtils.roundB(sou.getPriceListChildren()));
        dest.setNoExtraBeds(sou.getNoExtraBeds());
        dest.setPriceExtraBeds(HUtils.roundB(sou.getPriceExtraBeds()));
        dest.setPriceListExtraBeds(HUtils.roundB(sou.getPriceListExtraBeds()));
        dest.setResDate(sou.getResDate());
        dest.setTotal(HUtils.roundB(sou.getPriceTotal()));
        dest.setServiceType(sou.getServiceType());
        dest.setServicevat(sou.getVat());
        dest.setDescription(sou.getDescription());
        dest.setPerperson(sou.isPerperson());
        if (CUtil.EmptyS(sou.getVat()) && CUtil.EmptyS(serviceName))
            errorMess(L(), IErrorCode.ERRORCODE123,
                    ILogMess.ERRORRESERERVATIONPAYMENTDETAIL);

    }

    public static void toCustomerBill(EntityManager em, OObjectId hotel,
            CustomerBill dest, ECustomerBill sou) {
        dest.setPayer(sou.getCustomer().getName());
        dest.setReseName(sou.getReservation().getName());
        dest.getPayList().addAll(sou.getResDetails());
        PropUtils.copyToProp(dest, sou);
        dest.setIssueDate(sou.getIssueDate());
        dest.setDateOfPayment(sou.getDateOfPayment());
        RUtils.retrieveCreateModif(dest, sou);
    }

    public static Class getClass(HotelObjects o) {
        switch (o) {
        case RESERVATION:
            return EHotelReservation.class;
        case CUSTOMER:
            return EHotelCustomer.class;
        case PRICELIST:
            return EHotelPriceList.class;
        case ROOM:
            return EHotelRoom.class;
        case SERVICE:
            return EHotelServices.class;
        case BILL:
            return ECustomerBill.class;
        case PAYMENTS:
            return EBillPayment.class;
        case HOTELMAIL:
            return EHotelMail.class;
        }
        return null;
    }

    public static Object create(HotelObjects o) {
        Class cl = getClass(o);
        return BUtil.construct(cl);
    }

}
