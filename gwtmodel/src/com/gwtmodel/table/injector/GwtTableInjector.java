/*
 * Copyright 2010 stanislawbartkowski@gmail.com 
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
package com.gwtmodel.table.injector;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtmodel.table.buttoncontrolmodel.ControlButtonFactory;
import com.gwtmodel.table.composecontroller.ComposeControllerFactory;
import com.gwtmodel.table.controlbuttonview.ControlButtonViewFactory;
import com.gwtmodel.table.controler.TableDataControlerFactory;
import com.gwtmodel.table.datamodelview.DataViewModelFactory;
import com.gwtmodel.table.datelist.DatePeriodListFactory;
import com.gwtmodel.table.factories.ITableAbstractFactories;
import com.gwtmodel.table.factories.ITableCustomFactories;
import com.gwtmodel.table.htmlview.HtmlPanelFactory;
import com.gwtmodel.table.panelview.PanelViewFactory;
import com.gwtmodel.table.readres.ReadResFactory;
import com.gwtmodel.table.slotmediator.SlotMediatorFactory;
import com.gwtmodel.table.slotmodel.SlotListContainer;
import com.gwtmodel.table.slotmodel.SlotSignalContextFactory;
import com.gwtmodel.table.slotmodel.SlotTypeFactory;
import com.gwtmodel.table.stringlist.MemoryStringTableFactory;
import com.gwtmodel.table.view.checkstring.CheckDictModelFactory;
import com.gwtmodel.table.view.controlpanel.ContrButtonViewFactory;
import com.gwtmodel.table.view.ewidget.EditWidgetFactory;
import com.gwtmodel.table.view.grid.GridViewFactory;
import com.gwtmodel.table.view.table.GwtTableFactory;
import com.gwtmodel.table.view.webpanel.IWebPanel;
import com.gwtmodel.table.view.webpanel.WebPanelFactory;

@GinModules(GwtTableInjectModule.class)
public interface GwtTableInjector extends Ginjector {

    TablesFactories getTablesFactories();

    SlotTypeFactory getSlotTypeFactory();

    SlotListContainer getSlotListContainer();

    TableDataControlerFactory getTableDataControlerFactory();

    SlotSignalContextFactory getSlotSignalContextFactory();

    ITableAbstractFactories getITableAbstractFactories();

    ComposeControllerFactory getComposeControllerFactory();

    DataViewModelFactory getDataViewModelFactory();

    CheckDictModelFactory getCheckDictModelFactory();

    ReadResFactory getReadResFactory();

    GridViewFactory getGridViewFactory();

    ITableCustomFactories getTableFactoriesContainer();

    GwtTableFactory getGwtTableFactory();

    EditWidgetFactory getEditWidgetFactory();

    SlotMediatorFactory getSlotMediatorFactory();

    PanelViewFactory getPanelViewFactory();

    MemoryStringTableFactory getMemoryStringTableFactory();

    DatePeriodListFactory getDatePeriodListFactory();

    HtmlPanelFactory getHtmlPanelFactory();

    ControlButtonFactory getControlButtonFactory();

    ContrButtonViewFactory getContrButtonViewFactory();

    WebPanelFactory getWebPanelFactory();

    IWebPanel getWebPanel();

    ControlButtonViewFactory getControlButtonViewFactory();

    ICallContext getCallContext();
}