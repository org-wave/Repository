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

/**
 * Atributos obrigatorios em uma entidade.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 *
 */
public enum FieldEnum {

	ID("id"),
	VERSION("version"),
	ACTIVE("active");

	private String value;

	private FieldEnum(String value) {
		this.value = value;
	}

	public static boolean contains(String value) {
		FieldEnum[] enumerations = FieldEnum.values();
		for (FieldEnum enumeration : enumerations) {
			if (enumeration.getValue().equals(value)) {
				return true;
			}
		}

		return false;
	}

	public String getValue() {
		return value;
	}

}
