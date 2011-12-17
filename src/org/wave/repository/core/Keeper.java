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
package org.wave.repository.core;

import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.wave.repository.enums.ErrorEnum;
import org.wave.repository.enums.FieldEnum;
import org.wave.repository.enums.InfoEnum;
import org.wave.repository.enums.RemoveEnum;
import org.wave.repository.exceptions.RepositoryException;
import org.wave.repository.validators.ClassValidator;
import org.wave.utils.reflection.ReflectionUtil;


/**
 * Mantem os dados das instancias armazenadas em um repositorio. Para isso, o repositorio deve ser definido em um arquivo /META-INF/persistence.xml e
 * as entidades devem seguir um conjunto de regras.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see ClassValidator
 * 
 */
public class Keeper {

	@Inject
	private ClassValidator validator;

	@Inject
	private Logger logger;

	@Inject
	private EntityManager manager;

	/**
	 * Armazena os dados de uma determinada instancia no repositorio. Se a instancia ja estiver presente no repositorio, seus dados serao apenas
	 * atualizados.
	 * 
	 * @param instance
	 * @throws RepositoryException
	 *             se a instancia for nula.
	 */
	public <T> void persist(T instance) throws RepositoryException {
		if (instance == null) {
			throw new RepositoryException(ErrorEnum.NULL_INSTANCE);
		}

		Class<?> klass = instance.getClass();
		this.validator.validate(klass);

		this.logger.info(InfoEnum.PERSIST.getMessage(instance));

		Field active = ReflectionUtil.getField(FieldEnum.ACTIVE.getValue(), klass);
		ReflectionUtil.set(Boolean.TRUE, active, instance);

		Field field = ReflectionUtil.getField(FieldEnum.ID.getValue(), klass);
		try {
			if (ReflectionUtil.get(field, instance) == null) {
				this.manager.persist(instance);
			} else {
				this.manager.merge(instance);
			}
		} catch (Exception e) {
			throw new RepositoryException(ErrorEnum.UNEXPECTED_EXCEPTION.getMessage(e.getMessage()));
		}
	}

	/**
	 * Retira os dados de uma determinada instancia do repositorio. Isso acontece de forma logica, ou seja, o atributo active da instancia e alterado
	 * para false.
	 * 
	 * @param instance
	 * @throws RepositoryException
	 *             se a instancia for nula.
	 */
	public <T> void remove(T instance) throws RepositoryException {
		this.remove(instance, RemoveEnum.LOGICAL);
	}

	/**
	 * Retira os dados de uma determinada instancia do repositorio. Isso pode acontecer de duas formas:
	 * <ul>
	 * Logica: o atributo active da instancia e alterado para false.
	 * </ul>
	 * <ul>
	 * Fisica: os dados da instancia sao removidos definitivamente do repositorio.
	 * </ul>
	 * 
	 * @param instance
	 * @param removeEnum
	 * @throws RepositoryException
	 *             se a instancia for nula.
	 * @see RemoveEnum
	 */
	public <T> void remove(T instance, RemoveEnum removeEnum) throws RepositoryException {
		if (instance == null) {
			throw new RepositoryException(ErrorEnum.NULL_INSTANCE);
		}

		this.validator.validate(instance.getClass());

		this.logger.info(InfoEnum.REMOVE.getMessage(instance));

		try {
			removeEnum.remove(instance, this.manager);
		} catch (Exception e) {
			throw new RepositoryException(ErrorEnum.UNEXPECTED_EXCEPTION.getMessage(e.getMessage()));
		}
	}

}
