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
package com.gwthotel.hotel.jpa.rooms;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.gwthotel.hotel.HotelObjects;
import com.gwthotel.hotel.jpa.HotelAbstractJpaCrud;
import com.gwthotel.hotel.jpa.JUtils;
import com.gwthotel.hotel.jpa.entities.EHotelRoom;
import com.gwthotel.hotel.jpa.entities.EHotelRoomServices;
import com.gwthotel.hotel.jpa.entities.EHotelServices;
import com.gwthotel.hotel.rooms.HotelRoom;
import com.gwthotel.hotel.rooms.IHotelRooms;
import com.gwthotel.hotel.services.HotelServices;
import com.jython.jpautil.JpaUtils;
import com.jython.jpautil.crudimpl.gensym.IJpaObjectGenSymFactory;
import com.jython.serversecurity.cache.OObjectId;
import com.jython.ui.server.jpatrans.ITransactionContextFactory;

public class HotelJpaRooms extends HotelAbstractJpaCrud<HotelRoom, EHotelRoom> implements
        IHotelRooms {

    @Inject
    public HotelJpaRooms(ITransactionContextFactory eFactory,
            IJpaObjectGenSymFactory iGen) {
        super(new String[] { "findAllRooms", "findOneRoom" }, eFactory,
                HotelObjects.ROOM, iGen, EHotelRoom.class);
    }

    @Override
    protected HotelRoom toT(EHotelRoom sou, EntityManager em, OObjectId hotel) {
        HotelRoom dest = new HotelRoom();
        dest.setNoPersons(sou.getNoPersons());
        dest.setNoChildren(sou.getNoChildren());
        dest.setNoExtraBeds(sou.getNoExtraBeds());
        return dest;
    }

    @Override
    protected void toE(EHotelRoom dest, HotelRoom sou, EntityManager em,
            OObjectId hotel) {
        dest.setNoPersons(sou.getNoPersons());
        dest.setNoChildren(sou.getNoChildren());
        dest.setNoExtraBeds(sou.getNoExtraBeds());
    }

    @Override
    protected void beforedeleteElem(EntityManager em, OObjectId hotel,
            EHotelRoom elem) {
        String[] queryS = { "deleteAllReservationDetailsForRoom",
                "deleteServicesForRoom", "deleteGuestForRoom" };
        JUtils.runQueryForObject(em, elem, queryS);
    }

    private class SetRoomServices extends doTransaction {

        private final String roomName;
        private final List<String> services;

        SetRoomServices(OObjectId hotel, String roomName, List<String> services) {
            super(hotel);
            this.roomName = roomName;
            this.services = services;
        }

        @Override
        protected void dosth(EntityManager em) {
            EHotelRoom r = getElem(em, roomName);
            // TODO: more descriptive log in null
            Query q = em.createNamedQuery("deleteServicesForRoom");
            q.setParameter(1, r);
            q.executeUpdate();
            for (String s : services) {
                EHotelServices se = JUtils.findService(em, hotel, s);
                EHotelRoomServices eServ = new EHotelRoomServices();
                eServ.setHotel(hotel.getId());
                eServ.setRoom(r);
                eServ.setService(se);
                em.persist(eServ);
            }
        }

    }

    @Override
    public void setRoomServices(OObjectId hotel, String roomName,
            List<String> services) {
        SetRoomServices comma = new SetRoomServices(hotel, roomName, services);
        comma.executeTran();

    }

    private class GetRoomServices extends doTransaction {
        private final String roomName;
        private final List<HotelServices> eList = new ArrayList<HotelServices>();

        GetRoomServices(OObjectId hotel, String roomName) {
            super(hotel);
            this.roomName = roomName;
        }

        @Override
        protected void dosth(EntityManager em) {
            Query q = JpaUtils.createObjectIdQuery(em, hotel, "findServicesForRoom");
            q.setParameter(2, roomName);
            @SuppressWarnings("unchecked")
            List<EHotelRoomServices> rList = q.getResultList();
            for (EHotelRoomServices r : rList) {
                eList.add(JUtils.toT(r.getService()));
            }
        }
    }

    @Override
    public List<HotelServices> getRoomServices(OObjectId hotel, String roomName) {
        GetRoomServices comm = new GetRoomServices(hotel, roomName);
        comm.executeTran();
        return comm.eList;
    }

    @Override
    protected void afterAddChange(EntityManager em, OObjectId hotel,
            HotelRoom prop, EHotelRoom elem, boolean add) {

    }
}
