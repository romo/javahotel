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
package com.gwtmodel.table;

import com.gwtmodel.table.factories.IDataPersistAction;
import com.gwtmodel.table.factories.IPersistFactoryAction;
import com.gwtmodel.table.injector.GwtGiniInjector;
import com.gwtmodel.table.slotmodel.DataActionEnum;
import com.gwtmodel.table.slotmodel.ISlotSignalContext;
import com.gwtmodel.table.slotmodel.ISlotSignaller;

public class ReadDictList {

    public interface IListCallBack {
        void setList(IDataListType dList);
    }

    private static class R implements ISlotSignaller {

        private final IListCallBack iList;

        R(IListCallBack iList) {
            this.iList = iList;
        }

        public void signal(ISlotSignalContext slContext) {
            IDataListType dataList = slContext.getDataList();
            iList.setList(dataList);
        }
    }

    public static void readList(IDataType dType, IListCallBack iList) {
        IPersistFactoryAction persistFactoryA = GwtGiniInjector.getI()
                .getTableFactoriesContainer().getPersistFactoryAction();
        IDataPersistAction persistA = persistFactoryA.contruct(dType);
        persistA.getSlContainer().registerSubscriber(
                DataActionEnum.ListReadSuccessSignal, dType, new R(iList));
        persistA.getSlContainer().publish(DataActionEnum.ReadListAction, dType);
    }

}