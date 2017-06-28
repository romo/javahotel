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
package org.migration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

abstract class TestHelper {

	protected String getFileName(String fname) {
		return TestHelper.class.getClassLoader().getResource("resources/" + fname).getPath();
	}

	protected String getOutputDir() {
		return "/tmp/testoutdir";
	}

	protected List<String> lastL;

	protected BufferedReader openFile(String fname) throws URISyntaxException, FileNotFoundException {

		URI u = TestHelper.class.getClassLoader().getResource("resources/" + fname).toURI();
		FileReader f = new FileReader(new File(u));
		return new BufferedReader(f);
	}

	protected void verifyFile(String fName, int nolines) throws IOException {
		File f = new File(getOutputDir(), fName);
		assertTrue(f.exists());
		lastL = Files.readAllLines(Paths.get(f.toURI()));
		assertEquals(nolines, lastL.size());
	}

}
