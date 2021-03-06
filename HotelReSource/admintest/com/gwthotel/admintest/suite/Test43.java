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
package com.gwthotel.admintest.suite;

import org.junit.Test;

public class Test43 extends TestHelper {

    @Test
    public void test1() {
        scriptTest("dialog43.xml", "test1");
    }

    @Test
    public void test2() {
        scriptTest("dialog43.xml", "test2");
    }

    @Test
    public void test3() {
        scriptTest("dialog43.xml", "test3");
    }

    @Test
    public void test4() {
        scriptTest("dialog43.xml", "test4");
    }

}
