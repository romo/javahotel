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

import com.google.gwt.user.client.ui.FileUpload;
import com.gwtmodel.table.IVField;
import com.gwtmodel.table.editw.IFormFieldProperties;

/**
 * 
 * @author perseus
 */
class FileChooser extends AbstractField {

    private final FileUpload fEdit;

    FileChooser(final IVField v, IFormFieldProperties pr) {
        super(v, pr, true);
        this.fEdit = new FileUpload();
        this.fEdit.setName(getHtmlName());
        initWidget(this.fEdit);
    }

    public void setReadOnly(boolean readOnly) {
        fEdit.setEnabled(!readOnly);
    }

    public Object getValObj() {
        return fEdit.getFilename();
    }

    public void setValObj(Object o) {
        // do nothing
    }

	@Override
	public void setFocus(boolean focus) {
		fEdit.setFocus(focus);
		
	}
}
