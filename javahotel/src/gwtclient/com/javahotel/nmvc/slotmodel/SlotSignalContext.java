/*
 * Copyright 2008 stanislawbartkowski@gmail.com 
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
package com.javahotel.nmvc.slotmodel;

import com.javahotel.client.dialog.IGwtWidget;
import com.javahotel.nmvc.common.DataListType;
import com.javahotel.nmvc.common.DataType;

public class SlotSignalContext {

    private SlotSignalContext() {
    }

    public static void signal(SlotPublisherType slPublisher, DataType dType,
            DataListType dataList) {
        ISlotSignalContext slContext = SlotSignalContextFactory.construct(
                slPublisher.getSlType(), dType, dataList);
        slPublisher.getSlRegisterSubscriber().signal(slContext);
    }

    public static void signal(SlotPublisherType slPublisher,
            IGwtWidget gwtWidget) {
        ISlotSignalContext slContext = SlotSignalContextFactory.construct(
                slPublisher.getSlType(), gwtWidget);
        slPublisher.getSlRegisterSubscriber().signal(slContext);
    }

    public static void signal(SlotPublisherType slPublisher, DataType dType) {
        signal(slPublisher, dType, null);
    }

    public static void signal(SlotSubscriberType slPublisher, DataType dType) {
        ISlotSignalContext slContext = SlotSignalContextFactory.construct(
                slPublisher.getSlType(), dType);
        slPublisher.getSlSignaller().signal(slContext);
    }
}