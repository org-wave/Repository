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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.wave.repository.enums.ErrorEnum;
import org.wave.repository.enums.FieldEnum;
import org.wave.repository.enums.QLEnum;
import org.wave.repository.enums.VerbEnum;
import org.wave.repository.exceptions.RepositoryException;
import org.wave.repository.propositions.Equals;
import org.wave.repository.propositions.In;
import org.wave.repository.propositions.Like;
import org.wave.repository.propositions.Proposition;
import org.wave.repository.validators.ClassValidator;
import org.wave.repository.validators.PropositionValidator;
import org.wave.utils.collection.CollectionUtil;
import org.wave.utils.reflection.ReflectionUtil;


/**
 * Retorna as instancias armazenadas em um repositorio. Para isso, e necessario definir a classe e o perfil dessas instancias. Cada perfil deve ser
 * definido por proposicoes.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see Proposition
 * @see QLEnum
 * 
 */
public class Seeker {

	@Inject
	private EntityManager manager;

	@Inject
	private ClassValidator classValidator;

	@Inject
	private PropositionValidator propositionValidator;

	/**
	 * Retorna todas as instancias de uma determinada classe armazenadas no repositorio. Para definir o perfil dessas instancias, deve-se usar
	 * proposicoes.
	 * 
	 * @param klass
	 * @param propositions
	 * @return Lista de instancias
	 * @throws RepositoryException
	 * @see Proposition
	 */
	public <T> List<T> seekAll(Class<T> klass, Proposition... propositions) throws RepositoryException {
		return this.seekAll(klass, CollectionUtil.convert(propositions));
	}

	/**
	 * Retorna uma unica instancia de uma determinada classe armazenada no repositorio. Para definir o perfil dessa instancia, deve-se usar
	 * proposicoes.
	 * 
	 * @param klass
	 * @param propositions
	 * @return Uma unica instancia
	 * @throws RepositoryException
	 * @see Proposition
	 */
	public <T> T seekOne(Class<T> klass, Proposition... propositions) throws RepositoryException {
		return this.seekOne(klass, CollectionUtil.convert(propositions));
	}

	/**
	 * Retorna a quantidade de instancias de uma determinada classe armazenadas no repositorio. Para definir o perfil dessas instancias, deve-se usar
	 * proposicoes.
	 * 
	 * @param klass
	 * @param propositions
	 * @return Quantidade de instancias
	 * @throws RepositoryException
	 * @see Proposition
	 */
	public <T> Long count(Class<T> klass, Proposition... propositions) throws RepositoryException {
		return this.count(klass, CollectionUtil.convert(propositions));
	}

	/**
	 * Retorna todas as instancias de uma determinada classe armazenadas no repositorio semelhantes a uma instancia de exemplo.
	 * 
	 * @param instance
	 * @return Lista de instancias
	 * @throws RepositoryException
	 *             se a instancia de exemplo for nula
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> seekByExample(T instance) throws RepositoryException {
		if (instance == null) {
			throw new RepositoryException(ErrorEnum.NULL_INSTANCE);
		}

		List<Proposition> propositions = new ArrayList<Proposition>();

		Class<T> klass = (Class<T>) instance.getClass();
		List<Field> fields = ReflectionUtil.getPersistentFields(klass);
		for (Field field : fields) {
			String fieldName = field.getName();
			Object value = ReflectionUtil.get(field, instance);

			if (!FieldEnum.contains(fieldName) && value != null) {
				if (field.getType().equals(String.class)) {
					propositions.add(new Like(fieldName, value));
					continue;
				}

				if (this.classValidator.isEntity(field.getType())) {
					propositions.add(new In(fieldName, this.seekByExample(value)));
					continue;
				}

				propositions.add(new Equals(fieldName, value));
			}
		}

		try {
			return this.seekAll(klass, propositions);
		} catch (RepositoryException e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}

	}

	/**
	 * Retorna todas as instancias de uma determinada classe armazenadas no repositorio. Para definir o perfil dessas instancias, deve-se usar
	 * proposicoes.
	 * 
	 * @param klass
	 * @param propositions
	 * @return Lista de instancias
	 * @throws RepositoryException
	 * @see Proposition
	 */
	public <T> List<T> seekAll(Class<T> klass, List<Proposition> propositions) throws RepositoryException {
		this.validate(klass, propositions);

		StringBuilder builder = new StringBuilder();
		builder.append(QLEnum.SELECT.getValue());
		this.fillQLString(klass, propositions, builder);

		try {
			TypedQuery<T> query = this.manager.createQuery(builder.toString(), klass);
			this.setParameters(propositions, query);

			return query.getResultList();
		} catch (Exception e) {
			throw new RepositoryException(ErrorEnum.UNEXPECTED_EXCEPTION.getMessage(e.getMessage()));
		}
	}

	/**
	 * Retorna uma unica instancia de uma determinada classe armazenada no repositorio. Para definir o perfil dessa instancia, deve-se usar
	 * proposicoes.
	 * 
	 * @param klass
	 * @param propositions
	 * @return Uma unica instancia
	 * @throws RepositoryException
	 * @see Proposition
	 */
	public <T> T seekOne(Class<T> klass, List<Proposition> propositions) throws RepositoryException {
		this.validate(klass, propositions);

		StringBuilder builder = new StringBuilder();
		builder.append(QLEnum.SELECT.getValue());
		this.fillQLString(klass, propositions, builder);

		try {
			TypedQuery<T> query = this.manager.createQuery(builder.toString(), klass);
			this.setParameters(propositions, query);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			throw new RepositoryException(ErrorEnum.MORE_THAN_ONE_INSTANCE);
		} catch (Exception e) {
			throw new RepositoryException(ErrorEnum.UNEXPECTED_EXCEPTION.getMessage(e.getMessage()));
		}
	}

	/**
	 * Retorna a quantidade de instancias de uma determinada classe armazenadas no repositorio. Para definir o perfil dessas instancias, deve-se usar
	 * proposicoes.
	 * 
	 * @param klass
	 * @param propositions
	 * @return Quantidade de instancias
	 * @throws RepositoryException
	 * @see Proposition
	 */
	public <T> Long count(Class<T> klass, List<Proposition> propositions) throws RepositoryException {
		this.validate(klass, propositions);

		StringBuilder builder = new StringBuilder();
		builder.append(QLEnum.COUNT.getValue());
		this.fillQLString(klass, propositions, builder);

		try {
			TypedQuery<Long> query = this.manager.createQuery(builder.toString(), Long.class);
			this.setParameters(propositions, query);

			return query.getSingleResult();
		} catch (Exception e) {
			throw new RepositoryException(ErrorEnum.UNEXPECTED_EXCEPTION.getMessage(e.getMessage()));
		}
	}

	private <T> void validate(Class<T> klass, List<Proposition> propositions) throws RepositoryException {
		if (klass == null) {
			throw new RepositoryException(ErrorEnum.NULL_CLASS);
		}

		this.classValidator.validate(klass);

		propositions.add(new Equals(FieldEnum.ACTIVE.getValue(), Boolean.TRUE));
		for (Proposition proposition : propositions) {
			this.propositionValidator.validate(proposition);
		}

		Collections.sort(propositions);
	}

	private <T> void fillQLString(Class<T> klass, List<Proposition> propositions, StringBuilder builder) throws RepositoryException {
		builder.append(QLEnum.FROM.getValue());
		builder.append(klass.getName());
		builder.append(QLEnum.WHERE.getValue());

		for (int i = 0; i < propositions.size(); i++) {
			Proposition proposition = propositions.get(i);

			if (i != 0 && !proposition.getVerb().equals(VerbEnum.ORDER_BY)) {
				builder.append(QLEnum.AND.getValue());
			}

			proposition.fillQLString(builder);
		}
	}

	private <T> void setParameters(List<Proposition> propositions, Query query) {
		for (Proposition proposition : propositions) {
			proposition.setParameters(query);
		}
	}

}
