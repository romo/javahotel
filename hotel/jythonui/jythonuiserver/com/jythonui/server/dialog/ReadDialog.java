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
package com.jythonui.server.dialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.gwtmodel.table.binder.BinderWidget;
import com.gwtmodel.table.binder.WidgetTypes;
import com.gwtmodel.table.common.CUtil;
import com.jythonui.server.BUtil;
import com.jythonui.server.IBinderParser;
import com.jythonui.server.IConsts;
import com.jythonui.server.IGetResourceFile;
import com.jythonui.server.SaxUtil;
import com.jythonui.server.Util;
import com.jythonui.server.UtilHelper;
import com.jythonui.server.IBinderParser.IBinderHandler;
import com.jythonui.server.holder.SHolder;
import com.jythonui.server.logmess.IErrorCode;
import com.jythonui.server.logmess.ILogMess;
import com.jythonui.server.security.ISecurity;
import com.jythonui.shared.ButtonItem;
import com.jythonui.shared.ChartFormat;
import com.jythonui.shared.CheckList;
import com.jythonui.shared.DateLine;
import com.jythonui.shared.DialogFormat;
import com.jythonui.shared.DisclosureElemPanel;
import com.jythonui.shared.ElemDescription;
import com.jythonui.shared.FieldItem;
import com.jythonui.shared.FormDef;
import com.jythonui.shared.ICommonConsts;
import com.jythonui.shared.ListFormat;
import com.jythonui.shared.SUtil;
import com.jythonui.shared.TabPanel;
import com.jythonui.shared.TabPanelElem;
import com.jythonui.shared.ValidateRule;

/**
 * @author hotel
 * 
 *         Reads XML with dialog description and return DialogFormat class
 */
class ReadDialog extends UtilHelper {

	/**
	 * @author hotel
	 * 
	 *         SAX handler
	 */
	private static class MyHandler extends DefaultHandler {

		private final SaxUtil.ITransformVal iT;
		private final IGetResourceFile iGetResource;

		/** DialogFormat class being built. */
		private DialogFormat dFormat = null;

		private static boolean isBinderNameSpace(String uri) {
			return IConsts.BINDERNAMESPACE.equals(uri);
		}

		private final IBinderHandler iBind;
		private final SAXParser saxParser;

		/** Tags recognized for a particular element. */
		/*
		 * It duplicated to some extend xsd schema which also forces XML format.
		 */
		private final String[] dialogTag = { ICommonConsts.HTMLPANEL, ICommonConsts.UIBINDER,
				ICommonConsts.HTMLLEFTMENU, ICommonConsts.JSCODE, ICommonConsts.BEFORE, ICommonConsts.DISPLAYNAME,
				ICommonConsts.IMPORT, ICommonConsts.METHOD, ICommonConsts.PARENT, ICommonConsts.TYPES,
				ICommonConsts.ASXML, ICommonConsts.CLEARCENTRE, ICommonConsts.CLEARLEFT, ICommonConsts.FORMPANEL,
				ICommonConsts.AUTOHIDE, ICommonConsts.MODELESS, ICommonConsts.CSSCODE, ICommonConsts.TOP,
				ICommonConsts.LEFT, ICommonConsts.MAXLEFT, ICommonConsts.MAXTOP, ICommonConsts.SIGNALCLOSE };
		private final String[] buttonTag = { ICommonConsts.ID, ICommonConsts.DISPLAYNAME, ICommonConsts.ACTIONTYPE,
				ICommonConsts.ACTIONPARAM, ICommonConsts.ACTIONPARAM1, ICommonConsts.ACTIONPARAM2,
				ICommonConsts.ACTIONPARAM3, ICommonConsts.IMPORT, ICommonConsts.HIDDEN, ICommonConsts.READONLY,
				ICommonConsts.METHOD, ICommonConsts.VALIDATE, ICommonConsts.BUTTONHEADER, ICommonConsts.IMAGENAME,
				ICommonConsts.JSACTION };
		private final String[] fieldTag = { ICommonConsts.ID, ICommonConsts.TYPE, ICommonConsts.AFTERDOT,
				ICommonConsts.ACTIONID, ICommonConsts.DISPLAYNAME, ICommonConsts.NOTEMPTY, ICommonConsts.READONLY,
				ICommonConsts.HIDDEN, ICommonConsts.READONLYADD, ICommonConsts.READONLYCHANGE,
				ICommonConsts.SIGNALCHANGE, ICommonConsts.HELPER, ICommonConsts.HELPERREFRESH, ICommonConsts.FROM,
				ICommonConsts.WIDTH, ICommonConsts.ALIGN, ICommonConsts.HTMLID, ICommonConsts.DEFVALUE,
				ICommonConsts.FOOTER, ICommonConsts.EDITCOL, ICommonConsts.SIGNALBEFORE, ICommonConsts.FOOTERTYPE,
				ICommonConsts.FOOTERALIGN, ICommonConsts.FOOTERAFTERDOT, ICommonConsts.IMAGECOLUMN,
				ICommonConsts.IMAGELIST, ICommonConsts.EDITCLASS, ICommonConsts.EDITCSS, ICommonConsts.LABEL,
				ICommonConsts.COLUMNCLASS, ICommonConsts.SPINNERMIN, ICommonConsts.HEADERCLASS,
				ICommonConsts.SPINNERMAX, ICommonConsts.VISLINES, ICommonConsts.CELLTITLE, ICommonConsts.SUGGEST,
				ICommonConsts.REMEMBER, ICommonConsts.REMEMBERKEY, ICommonConsts.SUGGESTKEY, ICommonConsts.SUGGESTSIZE,
				ICommonConsts.MULTI, ICommonConsts.MENU, ICommonConsts.BINDER, ICommonConsts.JSSIGNALCHANGE };
		private final String[] listTag = { ICommonConsts.ID, ICommonConsts.DISPLAYNAME, ICommonConsts.ELEMFORMAT,
				ICommonConsts.STANDBUTT, ICommonConsts.PAGESIZE, ICommonConsts.WIDTH, ICommonConsts.CHUNKED,
				ICommonConsts.SIGNALAFTERROW, ICommonConsts.SIGNALBEFOREROW, ICommonConsts.LISTBUTTONSLIST,
				ICommonConsts.LISTBUTTONSVALIDATE, ICommonConsts.JSMODIFROW, ICommonConsts.LISTSELECTEDMESS,
				ICommonConsts.LISTBUTTONSSELECTED, ICommonConsts.TOOLBARTYPE, ICommonConsts.NOWRAPLIST,
				ICommonConsts.NOPROPERTYCOLUMN };
		private final String[] valTag = { ICommonConsts.ID, ICommonConsts.DISPLAYNAME, ICommonConsts.VALIDATEOP,
				ICommonConsts.VALIDATEID1 };
		private final String[] checklistTag = { ICommonConsts.ID, ICommonConsts.DISPLAYNAME, ICommonConsts.READONLY,
				ICommonConsts.TYPE, ICommonConsts.AFTERDOT };
		private final String[] datelineTag = { ICommonConsts.ID, ICommonConsts.DISPLAYNAME, ICommonConsts.PAGESIZE,
				ICommonConsts.COLNO, ICommonConsts.DATELINEID, ICommonConsts.DATELINEDEFAFILE,
				ICommonConsts.DATALINEFILE, ICommonConsts.DATELINEDATEID, ICommonConsts.CLASSNAME,
				ICommonConsts.STANDBUTT, ICommonConsts.CURRENTPOS };
		private final String[] formTag = { ICommonConsts.ID, ICommonConsts.DATALINEFILE };
		private final String[] elemchecklistTag = checklistTag;
		private final String[] tabpanelTag = { ICommonConsts.ID };
		private final String[] tabelemTag = { ICommonConsts.ID, ICommonConsts.DISPLAYNAME,
				ICommonConsts.BEFORECHANGETAB };
		private final String[] disclosureelemTag = { ICommonConsts.ID, ICommonConsts.DISPLAYNAME,
				ICommonConsts.HTMLPANEL, ICommonConsts.SIGNALCHANGE };
		private final String[] chartTag = { ICommonConsts.ID, ICommonConsts.DISPLAYNAME, ICommonConsts.CHARTHEIGHT,
				ICommonConsts.WIDTH, ICommonConsts.CHARTPIENOT3D, ICommonConsts.CHARTTYPE };

		/** Currently recognized set of tags. */
		/*
		 * Important: it is assumed that tags describing element goes first
		 * before subelements.
		 */
		/* This invariant is enforced by xsd schema. */
		private final String[] allowedActions = { ICommonConsts.JMAINDIALOG, ICommonConsts.JUPDIALOG,
				ICommonConsts.JCLOSEDIALOG, ICommonConsts.JOKMESSAGE, ICommonConsts.JERRORMESSAGE,
				ICommonConsts.JYESNOMESSAGE, ICommonConsts.JLOGOUTACTION, ICommonConsts.JSUBMIT,
				ICommonConsts.JURL_OPEN };
		private String[] currentT;
		private StringBuffer buf;
		private ElemDescription bDescr = null;
		private ElemDescription beforeCol = null;
		private List<ButtonItem> bList = null;
		private List<FieldItem> fList = null;
		private List<ValidateRule> valList = null;
		private CheckList checkList = null;
		private DateLine dL = null;
		private List<ValidateRule> formRules = null;
		private List<TabPanelElem> tabList = null;
		private List<DisclosureElemPanel> discList = null;

		MyHandler(IGetResourceFile iGetResource, ISecurity iSec, IBinderHandler iBind, SAXParser saxParser) {
			this.iGetResource = iGetResource;
			iT = new EvaluateJexlValue(Util.getToken(), iSec);
			this.iBind = iBind;
			this.saxParser = saxParser;
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			buf = new StringBuffer();
			boolean getAttribute = false;
			if (qName.equals(ICommonConsts.DIALOG)) {
				dFormat = new DialogFormat();
				bDescr = dFormat;
				currentT = dialogTag;
				getAttribute = true;
			}
			if (dFormat == null) {
				return;
			}
			if (qName.equals(ICommonConsts.LIST)) {
				currentT = listTag;
				ListFormat li = new ListFormat();
				bDescr = li;
				formRules = li.getValList();
				fList = new ArrayList<FieldItem>();
				getAttribute = true;
			}
			if (qName.equals(ICommonConsts.CHARTLIST)) {
				currentT = chartTag;
				ChartFormat li = new ChartFormat();
				bDescr = li;
				fList = new ArrayList<FieldItem>();
				getAttribute = true;
			}
			if (qName.equals(ICommonConsts.LEFTMENU) || qName.equals(ICommonConsts.UPMENU)
					|| qName.equals(ICommonConsts.BUTTONS) || qName.equals(ICommonConsts.LEFTSTACK)
					|| qName.equals(ICommonConsts.ACTIONS)) {
				bList = new ArrayList<ButtonItem>();
				return;
			}
			if (qName.equals(ICommonConsts.FORM)) {
				fList = new ArrayList<FieldItem>();
				return;
			}
			if (qName.equals(ICommonConsts.BUTTON) || qName.equals(ICommonConsts.ACTION)) {
				bDescr = new ButtonItem();
				currentT = buttonTag;
				getAttribute = true;
				// pass to getting attributes (no return)
			}

			if (qName.equals(ICommonConsts.FIELD) || qName.equals(ICommonConsts.COLUMN)) {
				bDescr = new FieldItem();
				currentT = fieldTag;
				getAttribute = true;
				// pass to getting attributes (no return)
			}
			if (qName.equals(ICommonConsts.VALIDATERULES)) {
				if (formRules != null)
					valList = formRules;
				else
					valList = dFormat.getValList();
				return;
			}
			if (qName.equals(ICommonConsts.VALIDATE)) {
				bDescr = new ValidateRule();
				currentT = valTag;
				getAttribute = true;
			}
			if (qName.equals(ICommonConsts.COLUMNS)) {
				beforeCol = bDescr;
				return;
			}
			if (qName.equals(ICommonConsts.DATELINEFORM)) {
				bDescr = new FormDef();
				currentT = formTag;
				getAttribute = true;
			}
			if (qName.equals(ICommonConsts.CHECKLIST)) {
				checkList = new CheckList();
				bDescr = checkList;
				currentT = checklistTag;
				getAttribute = true;
			}
			if (qName.equals(ICommonConsts.CHECKLISTLINES) && checkList != null) {
				bDescr = checkList.getLines();
				currentT = elemchecklistTag;
				getAttribute = true;
			}
			if (qName.equals(ICommonConsts.CHECKLISTCOLUMNS) && checkList != null) {
				bDescr = checkList.getColumns();
				currentT = elemchecklistTag;
				getAttribute = true;
			}
			if (qName.equals(ICommonConsts.DATELINE)) {
				currentT = datelineTag;
				dL = new DateLine();
				bDescr = dL;
				fList = new ArrayList<FieldItem>();
				getAttribute = true;
			}
			if (qName.equals(ICommonConsts.TABPANEL)) {
				currentT = tabpanelTag;
				TabPanel t = new TabPanel();
				bDescr = t;
				tabList = t.gettList();
				dFormat.getTabList().add(t);
				getAttribute = true;
			}

			if (qName.equals(ICommonConsts.TABPANELELEM)) {
				currentT = tabelemTag;
				TabPanelElem e = new TabPanelElem();
				bDescr = e;
				tabList.add(e);
				getAttribute = true;
			}

			if (qName.equals(ICommonConsts.DISCLOSUREPANEL))
				discList = dFormat.getDiscList();

			if (qName.equals(ICommonConsts.DISCLOUSREPANELELEM)) {
				currentT = disclosureelemTag;
				DisclosureElemPanel e = new DisclosureElemPanel();
				bDescr = e;
				discList.add(e);
				getAttribute = true;
			}

			if (isBinderNameSpace(uri)) {
				if (dFormat.isAttr(ICommonConsts.UIBINDER)) {
					String mess = SHolder.getM().getMess(IErrorCode.ERRORCODE146, ILogMess.CANNOTUIBINDERFILEANDCONTENT,
							dFormat.getId(), ICommonConsts.UIBINDER);
					errorLog(mess);
				}
				iBind.startDocument();
				iBind.startElement(uri, localName, qName, attributes);
				saxParser.getXMLReader().setContentHandler(iBind);
				iBind.setOfBinder(() -> {
					try {
						saxParser.getXMLReader().setContentHandler(this);
						dFormat.setBinderW(iBind.getB());
					} catch (SAXException e) {
						errorLog(e.getMessage());
					}
				});
				return;
			}

			if (getAttribute && bDescr != null)

			{
				SaxUtil.readAttr(bDescr.getMap(), attributes, currentT, iT);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (dFormat == null)
				return;

			if (qName.equals(ICommonConsts.BUTTON) || qName.equals(ICommonConsts.ACTION)) {
				ButtonItem bI = (ButtonItem) bDescr;
				if (bI.isAction()) {
					boolean found = false;
					for (int i = 0; i < allowedActions.length; i++) {
						if (bI.getAction().equals(allowedActions[i])) {
							found = true;
							break;
						}
					}
					if (!found) {
						// prepared error message
						String list = null;
						for (int i = 0; i < allowedActions.length; i++) {
							if (list == null)
								list = allowedActions[i];
							else
								list = list + " , " + allowedActions[i];
						} // for
						String mess = SHolder.getM().getMess(IErrorCode.ERRORCODE47, ILogMess.UNRECOGNIZEDACTION,
								bI.getAction(), list);
						errorLog(mess);
					}
				}
				if (bList != null) {
					bList.add(bI);
				}
				return;
			}
			if (qName.equals(ICommonConsts.FIELD) || qName.equals(ICommonConsts.COLUMN)) {
				FieldItem aI = (FieldItem) bDescr;
				if (aI.getId() == null) {
					String errmess = SHolder.getM().getMess(IErrorCode.ERRORCODE48, ILogMess.EMPTYIDVALUE,
							ICommonConsts.FIELD, ICommonConsts.ID);
					errorLog(errmess);
				}
				if (fList != null) {
					fList.add(aI);
				}
				return;
			}
			if (qName.equals(ICommonConsts.LEFTMENU)) {
				dFormat.getLeftButtonList().addAll(bList);
				bList = null;
				return;
			}
			if (qName.equals(ICommonConsts.LEFTSTACK)) {
				dFormat.getLeftStackList().addAll(bList);
				bList = null;
				return;
			}
			if (qName.equals(ICommonConsts.BUTTONS)) {
				dFormat.getButtonList().addAll(bList);
				bList = null;
				return;
			}
			if (qName.equals(ICommonConsts.UPMENU)) {
				dFormat.getUpMenuList().addAll(bList);
				bList = null;
				return;
			}
			if (qName.equals(ICommonConsts.DATELINE)) {
				dFormat.getDatelineList().add(dL);
				fList = null;
				dL = null;
				return;
			}
			if (qName.equals(ICommonConsts.DATELINEFORM)) {
				FormDef d = (FormDef) bDescr;
				String fileName = d.getFormDef();
				// replace with content of real file
				InputStream sou = iGetResource.getDialogFile(fileName);
				String te = BUtil.readFromFileInput(sou);
				d.setFormDef(te);
				dL.getFormList().add(d);
				return;
			}

			if (qName.equals(ICommonConsts.COLUMNS) && dL != null) {
				dL.getColList().addAll(fList);
				fList = null;
				return;
			}
			if (qName.equals(ICommonConsts.ACTIONS)) {
				dFormat.getActionList().addAll(bList);
				bList = null;
				return;
			}

			if (qName.equals(ICommonConsts.CHARTLIST)) {
				ChartFormat ch = (ChartFormat) beforeCol;
				ch.getColList().addAll(fList);
				dFormat.getChartList().add(ch);
				fList = null;
			}

			if (qName.equals(ICommonConsts.LIST)) {
				ListFormat li = (ListFormat) beforeCol;
				li.getColumns().addAll(fList);
				dFormat.getListList().add(li);
				fList = null;
				formRules = null;
				return;
			}

			if (qName.equals(ICommonConsts.FORM)) {
				dFormat.getFieldList().addAll(fList);
				fList = null;
				return;
			}
			if (qName.equals(ICommonConsts.VALIDATE)) {
				valList.add((ValidateRule) bDescr);
				return;
			}
			if (qName.equals(ICommonConsts.VALIDATERULES)) {
				return;
			}
			if (qName.equals(ICommonConsts.CHECKLIST) && checkList != null) {
				dFormat.getCheckList().add(checkList);
				return;
			}

			SaxUtil.readVal(bDescr.getMap(), qName, currentT, buf, iT);
		}

		@Override
		public void characters(char ch[], int start, int length) throws SAXException {
			buf.append(ch, start, length);
		}

	}

	private static String getFileName(IGetResourceFile iGetResource, String parentName, ElemDescription fo,
			String attr) {
		if (!fo.isAttr(attr))
			return null;
		String fileName = fo.getAttr(attr);
		return SUtil.getFileName(parentName, fileName);
	}

	private static InputStream getInput(IGetResourceFile iGetResource, String parentName, ElemDescription fo,
			String attr) {
		String eName = getFileName(iGetResource, parentName, fo, attr);
		if (CUtil.EmptyS(eName))
			return null;
		return iGetResource.getDialogFile(eName);
	}

	private static void replaceFile(IGetResourceFile iGetResource, String parentName, ElemDescription fo, String attr) {
		InputStream souI = getInput(iGetResource, parentName, fo, attr);
		if (souI == null)
			return;
		String te = BUtil.readFromFileInput(souI);
		fo.setAttr(attr, te);
	}

	private static void logB(String errCode, String attr, String val) {
		String mess = L().getMess(errCode, ILogMess.BINDERWIDGETSHOULDCONTAINCHILD,
				WidgetTypes.PaperDropdownMenu.name(), attr, val);
		errorLog(mess);
	}

	private static void verifyBinder(BinderWidget b) {
		if (b.getType() == WidgetTypes.PaperDropdownMenu) {
			if (!b.isIdDropId())
				logB(IErrorCode.ERRORCODE128, ICommonConsts.ID, ICommonConsts.DROPMENUID);
			if (!b.isClassDropDownContent())
				logB(IErrorCode.ERRORCODE129, IConsts.HTMLCLASS, ICommonConsts.DROPDOWNCONTENT);
		}
		b.getwList().forEach(bb -> verifyBinder(bb));
	}

	private static void readBinder(IGetResourceFile iGetResource, String parentName, DialogFormat d,
			IBinderParser iBinder) throws SAXException, IOException {
		String fileName = getFileName(iGetResource, parentName, d, ICommonConsts.UIBINDER);
		if (CUtil.EmptyS(fileName))
			return;
		BinderWidget b = iBinder.parse(fileName);
		if (b != null)
			d.setBinderW(b);
	}

	static DialogFormat parseDocument(String parentName, InputStream sou, ISecurity iSec, IGetResourceFile iGetResource,
			IBinderParser iBinder) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// important, namespace
		factory.setNamespaceAware(true);
		SAXParser saxParser = factory.newSAXParser();

		MyHandler ma = new MyHandler(iGetResource, iSec, iBinder.contructHandler(), saxParser);
		saxParser.parse(sou, ma);
		replaceFile(iGetResource, parentName, ma.dFormat, ICommonConsts.HTMLLEFTMENU);
		replaceFile(iGetResource, parentName, ma.dFormat, ICommonConsts.HTMLPANEL);
		replaceFile(iGetResource, parentName, ma.dFormat, ICommonConsts.JSCODE);
		replaceFile(iGetResource, parentName, ma.dFormat, ICommonConsts.CSSCODE);
		readBinder(iGetResource, parentName, ma.dFormat, iBinder);
		if (ma.dFormat.getBinderW() != null)
			verifyBinder(ma.dFormat.getBinderW());

		for (DisclosureElemPanel d : ma.dFormat.getDiscList())
			replaceFile(iGetResource, parentName, d, ICommonConsts.HTMLPANEL);
		return ma.dFormat;
	}

}
