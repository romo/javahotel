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
package com.gwtmodel.table.view.ewidget.gwt;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtmodel.table.IVField;
import com.gwtmodel.table.Utils;
import com.gwtmodel.table.editw.IFormChangeListener;
import com.gwtmodel.table.editw.IFormFieldProperties;

/**
 * DabeBox field, wrapper around DateBox
 * 
 * @author perseus
 */
class DateBoxCalendar extends AbstractField {

	protected final DateBox db;

	@Override
	public void setReadOnly(boolean readOnly) {
		db.setEnabled(!readOnly);
	}

	@Override
	public Object getValObj() {
		return db.getValue();
	}

	@Override
	public void setValObj(Object o) {
		db.setValue((Date) o);
		// 2013/07/29
		runOnChange(this, false);
	}

	private class DFormat implements DateBox.Format {

		@Override
		public String format(DateBox dateBox, Date date) {
			if (date == null) {
				return "";
			}
			return Utils.toS(date);
		}

		@Override
		public Date parse(DateBox dateBox, String text, boolean reportError) {
			return Utils.toD(text);
		}

		@Override
		public void reset(DateBox dateBox, boolean abandon) {
		}
	}

	@SuppressWarnings("rawtypes")
	private class TouchValueChange implements ValueChangeHandler {

		@Override
		public void onValueChange(ValueChangeEvent event) {
			onTouch();
		}
	}

	@SuppressWarnings({ "unchecked" })
	DateBoxCalendar(IVField v, IFormFieldProperties pr) {
		super(v, pr);
		db = new DateBox();
		db.setFormat(new DFormat());
		db.getTextBox().setName(getHtmlName());
		db.addValueChangeHandler(new TouchValueChange());
		db.addValueChangeHandler(new ValueChange());
		initWidget(db);
	}

	@Override
	public void addChangeListener(final IFormChangeListener l) {
		super.addChangeListener(l);
	}

	@Override
	public void setFocus(boolean focus) {
		db.setFocus(focus);

	}
}
