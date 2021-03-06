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
package com.gwthotel.hotel.jpa.reservation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.gwthotel.hotel.HotelObjects;
import com.gwthotel.hotel.ServiceType;
import com.gwthotel.hotel.jpa.HotelAbstractJpaCrud;
import com.gwthotel.hotel.jpa.JUtils;
import com.gwthotel.hotel.jpa.entities.EHotelCustomer;
import com.gwthotel.hotel.jpa.entities.EHotelReservation;
import com.gwthotel.hotel.jpa.entities.EHotelReservationDetail;
import com.gwthotel.hotel.reservation.IReservationForm;
import com.gwthotel.hotel.reservation.ReservationForm;
import com.gwthotel.hotel.reservation.ReservationPaymentDetail;
import com.jython.jpautil.JpaUtils;
import com.jython.jpautil.crudimpl.gensym.IJpaObjectGenSymFactory;
import com.jython.serversecurity.cache.OObjectId;
import com.jython.ui.server.jpatrans.ITransactionContextFactory;

public class HotelReservations extends
        HotelAbstractJpaCrud<ReservationForm, EHotelReservation> implements
        IReservationForm {

    @Inject
    public HotelReservations(ITransactionContextFactory eFactory,
            IJpaObjectGenSymFactory iGen) {
        super(new String[] { "findAllReservations", "findOneReservation" },
                eFactory, HotelObjects.RESERVATION, iGen,
                EHotelReservation.class);
    }

    @Override
    protected ReservationForm toT(EHotelReservation sou, EntityManager em,
            OObjectId hotel) {
        ReservationForm ho = new ReservationForm();
        ho.setCustomerName(sou.getCustomer().getName());
        ho.setStatus(sou.getStatus());
        ho.setAdvanceDeposit(sou.getAdvanceDeposit());
        ho.setTermOfAdvanceDeposit(sou.getTermOfAdvanceDeposit());
        ho.setAdvancePayment(sou.getAdvancePayment());
        ho.setDateofadvancePayment(sou.getDateofadvancePayment());
        // reservation details
        Query q = em.createNamedQuery("findReservationForReservation");
        q.setParameter(1, sou);
        q.setParameter(2, ServiceType.HOTEL);
        @SuppressWarnings("unchecked")
        List<EHotelReservationDetail> resList = q.getResultList();

        for (EHotelReservationDetail r : resList) {
            ReservationPaymentDetail det = new ReservationPaymentDetail();
            JUtils.ToReservationDetails(det, r);
            ho.getResDetail().add(det);
        }
        return ho;
    }

    @Override
    protected void afterAddChange(EntityManager em, OObjectId hotel,
            ReservationForm prop, EHotelReservation elem, boolean add) {
        Query q = em.createNamedQuery("deleteAllReservationsForReservation");
        q.setParameter(1, elem);
        q.setParameter(2, ServiceType.HOTEL);
        q.executeUpdate();
        for (ReservationPaymentDetail r : prop.getResDetail()) {
            EHotelReservationDetail d = new EHotelReservationDetail();
            JUtils.ToEReservationDetails(em, hotel, d, r);
            d.setReservation(elem);
            d.setServiceType(ServiceType.HOTEL);
            // d.setTotal(HUtils.roundB(r.getPrice()));
            em.persist(d);
        }
    }

    @Override
    protected void toE(EHotelReservation dest, ReservationForm sou,
            EntityManager em, OObjectId hotel) {
        String custName = sou.getCustomerName();
        EHotelCustomer cust = JpaUtils.getElemE(em, hotel, "findOneCustomer",
                custName);
        dest.setCustomer(cust);
        dest.setStatus(sou.getStatus());
        dest.setAdvanceDeposit(sou.getAdvanceDeposit());
        dest.setTermOfAdvanceDeposit(sou.getTermOfAdvanceDeposit());
        dest.setAdvancePayment(sou.getAdvancePayment());
        dest.setDateofadvancePayment(sou.getDateofadvancePayment());
    }

    @Override
    protected void beforedeleteElem(EntityManager em, OObjectId hotel,
            EHotelReservation elem) {
        JUtils.runQueryForObject(em, elem, "removeAllPaymentsforReservation");
        JUtils.removeList(em, elem, "findBillsForReservation");
        JUtils.runQueryForObject(em, elem,
                "deleteAllReservationDetailsForReservation",
                "deleteGuestsFromReservation");
    }

}
