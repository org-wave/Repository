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
package org.wave.repository.producers;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.apache.log4j.Logger;

/**
 * Fornece um objeto da classe Logger para registro de acoes da aplicacao.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see Logger
 */
public class LoggerProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private InjectionPoint point;

	/**
	 * Retorna o objeto da classe Logger.
	 * 
	 * @return Objeto da classe Logger
	 */
	@Produces
	public Logger getLogger() {
		return Logger.getLogger(this.point.getMember().getDeclaringClass());
	}

}
