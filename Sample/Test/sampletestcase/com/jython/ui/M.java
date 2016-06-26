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
package com.jython.ui;

public class M {

    private static boolean isJythonSerialized = false;
    private static String addPath = null;

    public static boolean isJythonSerialized() {
        return isJythonSerialized;
    }

    public static void setJythonSerialized(boolean isJythonSerialized) {
        M.isJythonSerialized = isJythonSerialized;
    }

    public static String getAddPath() {
        return addPath;
    }

    public static void setAddPath(String addPath) {
        M.addPath = addPath;
    }

}