/*
 * Copyright 2016 stanislawbartkowski@gmail.com 
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
package com.gwtmodel.table.panelview;

import javax.inject.Inject;

import com.gwtmodel.table.IDataType;
import com.gwtmodel.table.slotmodel.CellId;
import com.gwtmodel.table.slotmodel.SlotSignalContextFactory;
import com.gwtmodel.table.view.binder.ICreateBinderWidget;

public class PanelViewFactory {

	@Inject
	private static SlotSignalContextFactory slFactory;

	@Inject
	private static ICreateBinderWidget iBinder;

	private PanelViewFactory() {
	}

	public static IPanelView construct(IDataType dType, CellId pId) {
		return new PanelView(slFactory, iBinder, dType, pId);
	}
}