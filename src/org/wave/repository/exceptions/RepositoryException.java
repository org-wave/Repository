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
package org.wave.repository.exceptions;

import org.wave.repository.enums.ErrorEnum;

/**
 * Excecao que apresenta as mensagens de erro da aplicacao.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see ErrorEnum
 * 
 */
public class RepositoryException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorEnum errorEnum;

	private Object[] params;

	public RepositoryException(String message) {
		super(message);
	}

	public RepositoryException(ErrorEnum errorEnum, Object... params) {
		this.errorEnum = errorEnum;
		this.params = params;
	}

	/**
	 * Retorna a mensagem de erro.
	 */
	@Override
	public String getMessage() {
		if (this.errorEnum == null) {
			return super.getMessage();
		}

		return this.errorEnum.getMessage(this.params);
	}

}
