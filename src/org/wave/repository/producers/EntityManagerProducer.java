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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.wave.repository.enums.InfoEnum;


/**
 * Permite e encerra o acesso ao repositorio.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see InfoEnum
 */
public class EntityManagerProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String PERSISTENCE_UNIT_NAME = "persistenceUnit";

	private static EntityManagerFactory factory;

	@Inject
	private Logger logger;

	static {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	/**
	 * Retorna um gerenciador de entidades para acesso ao repositorio.
	 * 
	 * @return Gerenciador de entidades
	 */
	@Produces
	@ApplicationScoped
	public EntityManager getEntityManager() {
		this.logger.info(InfoEnum.OPEN.getMessage());

		return factory.createEntityManager();
	}

	/**
	 * Encerra o acesso ao repositorio.
	 * 
	 * @param entityManager
	 */
	public void close(@Disposes EntityManager entityManager) {
		this.logger.info(InfoEnum.CLOSE.getMessage());

		entityManager.close();
	}

}
