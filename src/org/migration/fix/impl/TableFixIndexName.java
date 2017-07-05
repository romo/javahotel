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
package org.migration.fix.impl;

import org.migration.extractor.ObjectExtractor;
import org.migration.fix.FixHelper;
import org.migration.fix.ObjectExtracted;
import org.migration.tokenizer.ITokenize;

public class TableFixIndexName extends FixHelper {

	private void modifyPrimaryName(ObjectExtractor.IObjectExtracted i) {
		ITokenize tot = this.getT(i);
		String w;

		String prevw = tot.readNextWord();
		boolean keyname = false;
		String reKeyName = null;
		int lineno = -1;
		while ((w = tot.readNextWord()) != null) {
			if (keyname) {
				// keyname
				reKeyName = w;
				lineno = tot.getLines().size() - 1;
				keyname = false;
			}
			if (U.found(prevw, w, "ADD", "CONSTRAINT"))
				keyname = true;
			prevw = w;
		}
		if (reKeyName != null) {
			String line = tot.getLines().get(lineno);
			String[] ss = reKeyName.split("\\.");
			if (ss.length == 2) {
				line = line.replaceAll(reKeyName, ss[1]);
				tot.getLines().set(lineno, line);
				replaceLines(i, tot);
			}
		}
	}

	@Override
	public void fix(ObjectExtracted o) {
		// primary keys
		o.getAdded().stream().filter(i -> i.getType() == ObjectExtractor.OBJECT.ALTERTABLE)
				.forEach(this::modifyPrimaryName);

	}

}