/*
   Copyright 2011 Benedito Barbosa Ribeiro Neto/Christian Linhares Peixoto/Mauricio da Silva Marinho

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.wave.repository.enums;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Mensagens de informacao da aplicacao.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 *
 */
public enum InfoEnum {

	PERSIST("info.message.persist"),
	REMOVE("info.message.remove"),
	
	OPEN("info.message.open"),
	CLOSE("info.message.close"),
	
	BEGIN("info.message.begin"),
	COMMIT("info.message.commit"),
	ROLLBACK("info.message.rollback");

	private String key;

	private InfoEnum(String message) {
		this.key = message;
	}

	public String getMessage(Object... params) {
		ResourceBundle bundle = ResourceBundle.getBundle("org.wave.repository.messages.messages", Locale.getDefault());

		String value = bundle.getString(this.key);
		
		return new MessageFormat(value).format(params);
	}

}
