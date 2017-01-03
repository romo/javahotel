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
package com.jythonui.server.binder;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.gwtmodel.table.binder.BinderWidget;
import com.gwtmodel.table.common.CUtil;
import com.jythonui.server.IParseRegString;
import com.jythonui.server.UtilHelper;
import com.jythonui.server.holder.SHolder;
import com.jythonui.server.logmess.IErrorCode;
import com.jythonui.server.logmess.ILogMess;

class ExtractStyle extends UtilHelper {

	private ExtractStyle() {

	}

	private final static String ENDSTYLE = "</style>";
	private final static String PASTYLE = "(<style.*>)";

	static private void old_getStyle(BinderWidget w) throws ParserConfigurationException, SAXException, IOException {
		String html = w.getContentHtml();
		if (CUtil.EmptyS(html))
			return;
		int start = 0;
		Pattern patt = Pattern.compile("(<style.*>)");
		String nextH = html;
		while (true) {
			nextH = nextH.substring(start);
			Matcher ma = patt.matcher(nextH);
			if (!ma.find())
				break;
			int i1 = ma.start();
			int i2 = ma.end();
			int end = nextH.indexOf("</style>", i2);
			if (end == -1)
				break;
			end += ENDSTYLE.length();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// important, namespace
			factory.setNamespaceAware(true);
			SAXParser saxParser = factory.newSAXParser();
			CssExtractorHandler ha = new CssExtractorHandler();
			String csshtml = nextH.substring(i1, end);
			saxParser.parse(new InputSource(new StringReader(csshtml)), ha);
			w.getStyleList().add(ha.sTyle);
			start = end;
		}

	}

	static void getStyle(final BinderWidget w) throws ParserConfigurationException, SAXException, IOException {
		String html = w.getContentHtml();
		if (CUtil.EmptyS(html))
			return;
		IParseRegString gPa = SHolder.getiParse();
		gPa.run(html, PASTYLE, ENDSTYLE, new IParseRegString.IVisitor() {

			@Override
			public void visit(String foundS, String matchedS, String content) {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				// important, namespace
				factory.setNamespaceAware(true);
				SAXParser saxParser;
				try {
					saxParser = factory.newSAXParser();
					CssExtractorHandler ha = new CssExtractorHandler();
					saxParser.parse(new InputSource(new StringReader(content)), ha);
					w.getStyleList().add(ha.sTyle);
				} catch (ParserConfigurationException | SAXException | IOException e) {
					errorLog(L().getMess(IErrorCode.ERRORCODE135, ILogMess.ERRORWHILEPARSINGCSSELEMENT, content));
				}
			}

		}, null);

	}

}
