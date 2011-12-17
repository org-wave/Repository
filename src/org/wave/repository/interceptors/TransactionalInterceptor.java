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
package org.wave.repository.interceptors;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.wave.repository.annotations.Transactional;
import org.wave.repository.enums.InfoEnum;
import org.wave.repository.exceptions.RepositoryException;


/**
 * Interceptador que inicia e finaliza uma transacao durante acesso ao repositorio.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see InfoEnum
 */
@Interceptor
@Transactional
public class TransactionalInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@Inject
	private EntityManager manager;

	/**
	 * Inicia uma transacao antes do acesso ao repositorio e a finaliza apos o acesso.
	 * 
	 * @param context
	 * @throws RepositoryException
	 */
	@AroundInvoke
	public Object intercept(InvocationContext context) throws RepositoryException {
		EntityTransaction transaction = this.manager.getTransaction();

		Object proceed = null;
		try {
			this.logger.info(InfoEnum.BEGIN.getMessage());
			transaction.begin();

			proceed = context.proceed();

			this.logger.info(InfoEnum.COMMIT.getMessage());
			transaction.commit();
		} catch (RepositoryException e) {
			this.logger.info(InfoEnum.ROLLBACK.getMessage());
			transaction.rollback();

			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			this.logger.info(InfoEnum.ROLLBACK.getMessage());
			transaction.rollback();

			e.printStackTrace();
			throw new RepositoryException(e.getMessage());
		}

		return proceed;
	}

}
