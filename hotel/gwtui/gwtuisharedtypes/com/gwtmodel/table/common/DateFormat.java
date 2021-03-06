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
package com.gwtmodel.table.common;

import java.util.Date;

public class DateFormat {

	private DateFormat() {

	}

	public static int getY(Date d) {
		int year = d.getYear() + 1900;
		return year;
	}

	public static int getM(Date d) {
		int mo = d.getMonth() + 1;
		return mo;
	}

	public static int getD(Date d) {
		int da = d.getDate();
		return da;
	}

	private static int toI(String s, int len) throws NumberFormatException {
		if (s.length() != len) {
			throw new NumberFormatException();
		}
		try {
			Integer i = new Integer(s);
			return i.intValue();
		} catch (NumberFormatException e) {
			throw e;
		}
	}

	private static String toNS(int nu, int ma) {
		String s = new Integer(nu).toString();
		while (s.length() < ma) {
			s = "0" + s;
		}
		return s;
	}

	public static String toS(Date d, boolean withtime) {
		if (d == null) {
			return "";
		}
		int year = getY(d);
		int mo = getM(d);
		int da = getD(d);
		String s = toNS(year, 4) + "/" + toNS(mo, 2) + "/" + toNS(da, 2);
		if (withtime) {
			int hh = d.getHours();
			int mm = d.getMinutes();
			int ss = d.getSeconds();
			s = s + " " + toNS(hh, 2) + ":" + toNS(mm, 2) + ":" + toNS(ss, 2);
		}
		return s;
	}

	public static Date toD(int y, int m, int d) {
		Date dd = new Date();
		DateFormat.toD(dd, y, m, d);
		return dd;
	}

	public static String getDateFormat() {
		return "YYYY/MM/DD";
	}

	public static void toD(Date dd, int y, int m, int d) {
		dd.setYear(y - 1900);
		dd.setMonth(m - 1);
		dd.setDate(d);

		dd.setHours(0);
		dd.setMinutes(0);
		dd.setSeconds(0);
	}

	private static boolean setD(Date dd, String s) {
		String a[] = s.split("/");
		if (a.length != 3) {
			return false;
		}
		String yS = a[0];
		String mS = a[1];
		String dS = a[2];
		try {
			int y = toI(yS, 4);
			if (y <= 1900) {
				return false;
			}
			int d = toI(dS, 2);
			int m = toI(mS, 2);
			if ((m < 1) || (m > 12)) {
				return false;
			}
			toD(dd, y, m, d);

			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	public static Date toD(String s, boolean timestamp) {
		Date d = new Date();
		String dS = null;
		String tS = null;
		if (timestamp) {
			String[] a = s.split(" ");
			dS = a[0];
			tS = a[1];
		} else
			dS = s;
		if (!setD(d, dS)) {
			return null;
		}
		if (timestamp) {
			String a[] = tS.split(":");
			int hh = toI(a[0], 2);
			int mm = toI(a[1], 2);
			int ss = toI(a[2], 2);
			d.setHours(hh);
			d.setMinutes(mm);
			d.setSeconds(ss);
		}
		return d;
	}

}
