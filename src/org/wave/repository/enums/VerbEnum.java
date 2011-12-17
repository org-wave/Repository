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
 * Agrupa regras de construcao para uma proposicao.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 *
 */
public enum VerbEnum {

	LESSER(" < "),
	GREATER(" > "),
	LESSER_EQUALS(" <= "),
	GREATER_EQUALS(" >= "),
	EQUALS(" = "),
	NOT_EQUALS(" <> "),
	IS_NULL(" is null") {
		@Override
		public boolean requiresPredicative() {
			return false;
		}
	},
	IS_NOT_NULL(" is not null") {
		@Override
		public boolean requiresPredicative() {
			return false;
		}
	},
	BETWEEN(" between "),
	LIKE(" like "),
	IN(" in "),
	NOT_IN(" not in "),
	ORDER_BY(" order by "),
	AND(" and ") {
		@Override
		public boolean isConnective() {
			return true;
		}
	},
	OR(" or ") {
		@Override
		public boolean isConnective() {
			return true;
		}
	},
	NOT("not ") {
		@Override
		public boolean requiresSubject() {
			return false;
		}
		
		@Override
		public boolean isConnective() {
			return true;
		}
	};

	private String value;

	private VerbEnum(String value) {
		this.value = value;
	}

	public boolean requiresSubject() {
		return true;
	}
	
	public boolean requiresPredicative() {
		return true;
	}
	
	public boolean isConnective() {
		return false;
	}

	public String getValue() {
		return value;
	}

}
