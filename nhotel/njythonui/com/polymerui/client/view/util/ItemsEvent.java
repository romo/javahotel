/*
 * Copyright 2017 stanislawbartkowski@gmail.com 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not ue this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.polymerui.client.view.util;

import com.google.gwt.event.shared.GwtEvent;

public class ItemsEvent extends GwtEvent<ItemsEventHandler> {

	public static Type<ItemsEventHandler> TYPE = new Type<ItemsEventHandler>();
	private final String val;

	public ItemsEvent(String val) {
		this.val = val;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ItemsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ItemsEventHandler handler) {
		handler.onItemsChange(val);
	}

}
