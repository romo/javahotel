/*
 * Copyright 2009 stanislawbartkowski@gmail.com 
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
package com.javahotel.client.widgets.stable;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author stanislawbartkowski@gmail.com
 */
public interface IDrawPartSeason {

    /**
     * Draw lines.
     *
     * @param fromL
     *            - number of first row to draw
     * @param toL
     *            - number of last row to draw
     */
    void draw(final int fromL, final int toL);

    void drawagain(final int fromL, final int toL, final int actL,
            final boolean setC);

    void setSWidget(Widget w);
}