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
import org.transform.jpk.UPLOAD;

public class GetUPO {

	private static void P(String s) {
		System.out.println(s);
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			P("Odczytanie UPO (2016/08/24 r:0)");
			P("Może być wywołany dopiero po pomyślnej transmisji, REFERENCENUMBER powinien zawierać numer wysyłki");
			P("");
			P("Wywolanie:");
			P("GetUPO <configuration file>");
			System.exit(4);
		}
		UPLOAD.getUPO(args[0]);
	}

}
