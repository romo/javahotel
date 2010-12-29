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
package com.gwtmodel.table.view.button;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtmodel.table.GFocusWidgetFactory;
import com.gwtmodel.table.IGFocusWidget;
import com.gwtmodel.table.Utils;
import com.gwtmodel.table.view.util.PopupTip;

/**
 *
 * @author stanislawbartkowski@gmail.com
 */
public class ImgButtonFactory {

    private ImgButtonFactory() {
    }

    private static class BImage extends PopupTip implements IGFocusWidget {

        BImage(Button b, String mess) {
            initWidget(b);
            setMessage(mess);
//            setMouse();
        }

        @Override
        public void addClickHandler(ClickHandler h) {
            Button b = (Button) this.getWidget();
            b.addClickHandler(h);
        }

        @Override
        public Widget getGWidget() {
            return this;
        }

        @Override
        public void setEnabled(boolean enabled) {
            Button b = (Button) this.getWidget();
            b.setEnabled(enabled);
        }

        @Override
        public boolean isEnabled() {
            Button b = (Button) this.getWidget();
            return b.isEnabled();
        }
    }

    public static IGFocusWidget getButton(String bId, String bName, String img) {
        Button but;
        IGFocusWidget w;
        if (img != null) {
            String imageFile;
            if (img.indexOf('.') == -1) {
                imageFile = img + ".gif";
            }
            else {
                imageFile = img;
            }
            String h = Utils.getImageHTML(imageFile);
            but = new Button();
            but.setHTML(h);
            w = new BImage(but, bName);
        } else {
            but = new Button(bName);
            w = GFocusWidgetFactory.construct(but);
        }
        if (bId != null) {
          but.getElement().setId(bId);
        }
        return w;
    }

    public static IGFocusWidget getButtonTextImage(String bId, String bName, String img) {
        String ht = "<table><tr>";
        String h = Utils.getImageHTML(img + ".gif");
        ht += h;
        Label la = new Label(bName);
        ht += "<td>" + la.getElement().getInnerHTML() + "</td>";
        ht += "</tr></table>";
        Button b = new Button();
        b.setHTML(ht);
        b.getElement().setId(bId);
        return GFocusWidgetFactory.construct(b);
    }
}