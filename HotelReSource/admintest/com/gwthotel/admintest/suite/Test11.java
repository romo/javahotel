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

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gwthotel.hotel.HotelObjects;
import com.gwthotel.hotel.customer.HotelCustomer;
import com.gwtmodel.table.common.DateFormat;

public class Test11 extends TestHelper {

    @Before
    public void before() {
        clearObjects();
        createHotels();
        setTestToday(DateFormat.toD(2013, 6, 13));
    }

    @Test
    public void test1() {        
        HotelCustomer p = (HotelCustomer) hObjects.construct(getH(HOTEL),
                HotelObjects.CUSTOMER);
        p.setDescription("Hejka");
        p.setDoctype('X');
        iGenSym.reset(getH(HOTEL), HotelObjects.CUSTOMER.name());
        p = iCustomers.addElem(getH(HOTEL), p);
        System.out.println(p.getName());
        assertEquals("2013 / 1 /C", p.getName());
        List<HotelCustomer> hList = iCustomers.getList(getH(HOTEL));
        assertEquals(1, hList.size());
        assertEquals("2013 / 1 /C", hList.get(0).getName());
        for (int i = 0; i < 100; i++) {
            p = (HotelCustomer) hObjects.construct(getH(HOTEL),
                    HotelObjects.CUSTOMER);
            p = iCustomers.addElem(getH(HOTEL), p);
        }
        System.out.println(p.getName());
    }
}
