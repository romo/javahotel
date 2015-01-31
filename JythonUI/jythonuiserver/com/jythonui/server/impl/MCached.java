/*
 * Copyright 2015 stanislawbartkowski@gmail.com 
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
package com.jythonui.server.impl;

import com.gwtmodel.commoncache.ICommonCache;
import com.jythonui.server.IJythonUIServerProperties;

/**
 * @author hotel
 * 
 */
public class MCached {

    private final ICommonCache mCache;
    private final IJythonUIServerProperties p;

    MCached(IJythonUIServerProperties p, ICommonCache mCache) {
        this.mCache = mCache;
        this.p = p;
    }

    public boolean isCached() {
        return p.isCached();
    }

    public ICommonCache getC() {
        return mCache;
    }

}
