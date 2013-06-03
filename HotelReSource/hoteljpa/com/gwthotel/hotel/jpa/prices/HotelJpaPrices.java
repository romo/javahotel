/*
 * Copyright 2013 stanislawbartkowski@gmail.com 
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
package com.gwthotel.hotel.jpa.prices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.gwthotel.admin.jpa.JpaTransaction;
import com.gwthotel.hotel.jpa.entities.EHotelPriceElem;
import com.gwthotel.hotel.jpa.entities.EHotelPriceList;
import com.gwthotel.hotel.jpa.entities.EHotelServices;
import com.gwthotel.hotel.prices.HotelPriceElem;
import com.gwthotel.hotel.prices.IHotelPriceElem;
import com.jythonui.server.getmess.IGetLogMess;

class HotelJpaPrices implements IHotelPriceElem {

    private final EntityManagerFactory eFactory;
    private final IGetLogMess lMess;

    protected HotelJpaPrices(EntityManagerFactory eFactory, IGetLogMess lMess) {
        this.eFactory = eFactory;
        this.lMess = lMess;
    }

    private abstract class doTransaction extends JpaTransaction {

        protected final String hotel;

        doTransaction(String hotel) {
            super(eFactory,lMess);
            this.hotel = hotel;
        }
    }

    private class GetPrices extends doTransaction {

        private final String priceList;
        private final List<HotelPriceElem> pList = new ArrayList<HotelPriceElem>();

        GetPrices(String hotel, String priceList) {
            super(hotel);
            this.priceList = priceList;
        }

        @Override
        protected void dosth(EntityManager em) {
            Query q = em.createNamedQuery("findPricesForPriceList");
            q.setParameter(1, hotel);
            q.setParameter(2, priceList);
            // do not catch exception,
            List<EHotelPriceElem> list = q.getResultList();
            for (EHotelPriceElem p : list) {
                HotelPriceElem e = new HotelPriceElem();
                e.setPriceList(p.getPricelist().getName());
                e.setService(p.getService().getName());
                e.setWeekendPrice(p.getWeekendprice());
                e.setWorkingPrice(p.getWorkingprice());
                pList.add(e);
            }
        }

    }

    @Override
    public List<HotelPriceElem> getPricesForPriceList(String hotel,
            String pricelist) {
        GetPrices comm = new GetPrices(hotel, pricelist);
        comm.executeTran();
        return comm.pList;
    }

    private class SavePrices extends doTransaction {
        private final String priceList;
        private final List<HotelPriceElem> pList;

        SavePrices(String hotel, String pricelist, List<HotelPriceElem> pList) {
            super(hotel);
            this.priceList = pricelist;
            this.pList = pList;
        }

        @Override
        protected void dosth(EntityManager em) {
            Map<String, EHotelServices> mService = new HashMap<String, EHotelServices>();
            List<EHotelPriceElem> eList = new ArrayList<EHotelPriceElem>();

            Query q = em.createNamedQuery("findOnePriceList");
            q.setParameter(1, hotel);
            q.setParameter(2, priceList);
            EHotelPriceList ePriceList = (EHotelPriceList) q.getSingleResult();
            // do not catch exception, one element is expected

            q = em.createNamedQuery("findPricesForPriceList");
            q.setParameter(1, hotel);
            q.setParameter(2, priceList);
            List<EHotelPriceElem> list = q.getResultList();
            for (EHotelPriceElem p : list) {
                EHotelServices e = p.getService();
                mService.put(e.getName(), e);
                eList.add(p);
            }
            // now analize elems
            for (HotelPriceElem e : pList) {
                String service = e.getService();
                EHotelServices eS = mService.get(service);
                if (eS == null) {
                    q = em.createNamedQuery("findOneService");
                    q.setParameter(1, hotel);
                    q.setParameter(2, service);
                    eS = (EHotelServices) q.getSingleResult();
                    // do not catch exception, single result is expected
                }
                EHotelPriceElem eElem = null;
                for (EHotelPriceElem eP : eList) {
                    if (!eP.getPricelist().getName().equals(priceList))
                        continue;
                    if (!eP.getService().getName().equals(e.getService()))
                        continue;
                    eElem = eP;
                    break;
                }
                if (eElem == null) {
                    eElem = new EHotelPriceElem();
                    eElem.setHotel(hotel);
                    eElem.setPricelist(ePriceList);
                    eElem.setService(eS);
                }
                eElem.setWeekendprice(e.getWeekendPrice());
                eElem.setWorkingprice(e.getWorkingPrice());
                em.persist(eElem);
            }
        }

    }

    @Override
    public void savePricesForPriceList(String hotel, String pricelist,
            List<HotelPriceElem> pList) {
        SavePrices comma = new SavePrices(hotel, pricelist, pList);
        comma.executeTran();
    }

    private class DeleteAllPrices extends doTransaction {

        DeleteAllPrices(String hotel) {
            super(hotel);
        }

        @Override
        protected void dosth(EntityManager em) {
            Query q = em.createNamedQuery("deletePricesForHotel");
            q.setParameter(1, hotel);
            q.executeUpdate();

        }

    }

    @Override
    public void deleteAll(String hotel) {
        DeleteAllPrices comm = new DeleteAllPrices(hotel);
        comm.executeTran();

    }

}