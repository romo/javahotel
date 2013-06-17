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
package com.gwthotel.hotel.jpa.customers;

import javax.inject.Provider;

import com.gwthotel.hotel.customer.IHotelCustomers;
import com.gwthotel.hotel.jpa.AbstractCrudProvider;

public class HotelCustomersProvider extends AbstractCrudProvider implements Provider<IHotelCustomers> {

    @Override
    public IHotelCustomers get() {
        return new HotelJpaCustomers(eFactory, iGen);
    }

}
