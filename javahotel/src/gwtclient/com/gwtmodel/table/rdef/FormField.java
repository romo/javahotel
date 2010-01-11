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
package com.gwtmodel.table.rdef;

import com.gwtmodel.table.IVField;

public class FormField {

    private final String pLabel;
    private final IFormLineView eLine;
    private final IVField fie;
    private final boolean readOnlyIfModif;

    public FormField(final String p, final IFormLineView e, final IVField fie,
            boolean readOnlyIfModif) {
        this.pLabel = p;
        this.eLine = e;
        this.fie = fie;
        this.readOnlyIfModif = readOnlyIfModif;
    }

    public boolean isReadOnlyIfModif() {
        return readOnlyIfModif;
    }

    /**
     * @return the pLabel
     */
    public String getPLabel() {
        return pLabel;
    }

    /**
     * @return the eLine
     */
    public IFormLineView getELine() {
        return eLine;
    }

    /**
     * @return the fie
     */
    public IVField getFie() {
        return fie;
    }

}