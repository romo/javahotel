/*
 * Copyright 2009 stanislawbartkowski@gmail.com 
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
package com.javahotel.client.panelcommand;

import com.javahotel.client.IResLocator;
import com.javahotel.common.command.DictType;

/**
 *
 * @author stanislawbartkowski@gmail.com
 */
class PanelCommandBeforeCheckFactory {

    private PanelCommandBeforeCheckFactory() {
    }

    static IPanelCommandBeforeCheck getPanelCheck(IResLocator sI,
            EPanelCommand command) {
        IPanelCommandBeforeCheck i = null;
        switch (command) {
            case BOOKINGPANEL:
                i = new VerifyNumberOfDict(sI,
                        new DictType[]{DictType.RoomObjects,
                            DictType.OffSeasonDict, DictType.PriceListDict},
                        "cannotdisplaypanel.jsp");
                break;
            case ROOMS:
                i = new VerifyNumberOfDict(sI,
                        new DictType[]{DictType.RoomFacility,
                            DictType.ServiceDict, DictType.RoomStandard},
                        "cannotdisplayrooms.jsp");
                break;
            case STANDARD:
                i = new VerifyNumberOfDict(sI, new DictType[]{
                            DictType.ServiceDict},
                        "cannotdisplaystandard.jsp");
                break;
            case PRICES:
                i = new VerifyNumberOfDict(sI, new DictType[]{
                            DictType.ServiceDict, DictType.OffSeasonDict},
                        "cannotdisplayprice.jsp");
                break;
            case BOOKING:
                i = new VerifyNumberOfDict(sI, new DictType[]{
                            DictType.ServiceDict, DictType.OffSeasonDict, DictType.RoomObjects,
                            DictType.PriceListDict},
                        "cannotdisplaybooking.jsp");
                break;

        }
        return i;
    }
}