/*
 * Copyright 2017 stanislawbartkowski@gmail.com  
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
package com.gwtmodel.table.view.controlpanel;

import com.gwtmodel.table.buttoncontrolmodel.ListOfControlDesc;

/**
 *
 * @author stanislawbartkowski@gmail.com
 */
public class ContrButtonViewFactory implements IContrButtonViewFactory {

	public ContrButtonViewFactory() {
	}

	@Override
	public IContrButtonView getView(final ListOfControlDesc model, final IControlClick co, boolean hori,
			boolean polymer) {
		return new ContrButtonView(model, co, hori, polymer);
	}
}
