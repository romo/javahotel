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
package com.gwtmodel.table.view.grid;

import java.math.BigDecimal;

import com.google.inject.Inject;
import com.gwtmodel.table.view.ewidget.EditWidgetFactory;

public class GridViewFactory {

    private final EditWidgetFactory eFactory;

    @Inject
    public GridViewFactory(EditWidgetFactory eFactory) {
        this.eFactory = eFactory;
    }

    public IGridView construct(GridViewType gType) {
        return new GridView(eFactory, gType);
    }

    private class Decimal extends GridView implements IGridViewDecimal {

        Decimal(GridViewType gType) {
            super(eFactory, gType);
        }

        public BigDecimal getCellDecimal(int row, int c) {
            return (BigDecimal) getCell(row, c);
        }

        public void setRowDecimal(int row, int c, BigDecimal b) {
            setRowVal(row, c, b);
        }

    }

    public IGridViewDecimal constructDecimal(GridViewType gType) {
        return new Decimal(gType);
    }

}