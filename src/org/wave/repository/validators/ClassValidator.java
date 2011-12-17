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
package org.wave.repository.validators;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import org.wave.repository.enums.ErrorEnum;
import org.wave.repository.enums.FieldEnum;
import org.wave.repository.enums.MethodEnum;
import org.wave.repository.exceptions.RepositoryException;
import org.wave.utils.reflection.ReflectionUtil;


/**
 * Define as regras que garantem a manutencao dos dados de uma instancia.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see FieldEnum
 * @see MethodEnum
 */
public class ClassValidator {

	/**
	 * Verifica se uma determinada classe obedece as regras que definem uma entidade.
	 * <p>
	 * 
	 * <b>Exemplo:</b>
	 * 
	 * <pre>
	 * &#064;Entity
	 * public class Entidade implements Serializable {
	 * 
	 * 	private static final long serialVersionUID = 1L;
	 * 
	 * 	&#064;Id
	 * 	private Long id;
	 * 
	 * 	&#064;Version
	 * 	private Integer version;
	 * 
	 * 	private Boolean active;
	 * 
	 * 	// Atributos
	 * 
	 * 	public Entidade() {
	 * 
	 * 	}
	 * 
	 * 	&#064;Override
	 * 	public boolean equals(Object obj) {
	 * 		// Implementacao do metodo.
	 * 	}
	 * 
	 * 	&#064;Override
	 * 	public int hashCode() {
	 * 		// Implementacao do metodo.
	 * 	}
	 * 
	 * 	public Long getId() {
	 * 		return id;
	 * 	}
	 * 
	 * 	public Integer getVersion() {
	 * 		return version;
	 * 	}
	 * 
	 * 	public Boolean getActive() {
	 * 		return active;
	 * 	}
	 * 
	 * 	// Metodos get e set dos atributos.
	 * 
	 * }
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param klass
	 * @throws RepositoryException
	 */
	public void validate(Class<?> klass) throws RepositoryException {
		if (!ReflectionUtil.isAnnotated(klass, Entity.class)) {
			throw new RepositoryException(ErrorEnum.ANNOTATION_ENTITY_NOT_FOUND, klass.getName());
		}

		if (!ReflectionUtil.implementz(klass, Serializable.class)) {
			throw new RepositoryException(ErrorEnum.INTERFACE_NOT_FOUND);
		}

		this.validateIdentifier(klass);

		this.validateVersion(klass);

		this.validateActive(klass);

		if (!ReflectionUtil.hasConstructor(klass)) {
			throw new RepositoryException(ErrorEnum.CONSTRUCTOR_NOT_FOUND, klass.getName());
		}

		if (!ReflectionUtil.hasMethod(klass, MethodEnum.EQUALS.getValue())) {
			throw new RepositoryException(ErrorEnum.METHOD_NOT_FOUND, MethodEnum.EQUALS.getValue());
		}

		if (!ReflectionUtil.hasMethod(klass, MethodEnum.HASHCODE.getValue())) {
			throw new RepositoryException(ErrorEnum.METHOD_NOT_FOUND, MethodEnum.HASHCODE.getValue());
		}
	}

	/**
	 * Indica que uma determinada classe e entidade.
	 * 
	 * @param klass
	 * @return true se a classe e entidade.
	 * @see #validate(Class)
	 */
	public boolean isEntity(Class<?> klass) {
		try {
			this.validate(klass);
		} catch (RepositoryException e) {
			return false;
		}

		return true;
	}

	private void validateIdentifier(Class<?> klass) throws RepositoryException {
		if (!ReflectionUtil.hasField(klass, FieldEnum.ID.getValue())) {
			throw new RepositoryException(ErrorEnum.ID_NOT_FOUND);
		}

		Field field = ReflectionUtil.getField(FieldEnum.ID.getValue(), klass);
		if (!field.getType().equals(Long.class)) {
			throw new RepositoryException(ErrorEnum.ID_NOT_LONG);
		}

		if (!ReflectionUtil.isAnnotated(field, Id.class)) {
			throw new RepositoryException(ErrorEnum.ANNOTATION_ID_NOT_FOUND);
		}
	}

	private void validateVersion(Class<?> klass) throws RepositoryException {
		if (!ReflectionUtil.hasField(klass, FieldEnum.VERSION.getValue())) {
			throw new RepositoryException(ErrorEnum.VERSION_NOT_FOUND);
		}

		Field field = ReflectionUtil.getField(FieldEnum.VERSION.getValue(), klass);
		if (!field.getType().equals(Integer.class)) {
			throw new RepositoryException(ErrorEnum.VERSION_NOT_INTEGER);
		}

		if (!ReflectionUtil.isAnnotated(field, Version.class)) {
			throw new RepositoryException(ErrorEnum.ANNOTATION_VERSION_NOT_FOUND);
		}
	}

	private void validateActive(Class<?> klass) throws RepositoryException {
		if (!ReflectionUtil.hasField(klass, FieldEnum.ACTIVE.getValue())) {
			throw new RepositoryException(ErrorEnum.ACTIVE_NOT_FOUND);
		}

		Field field = ReflectionUtil.getField(FieldEnum.ACTIVE.getValue(), klass);
		if (!field.getType().equals(Boolean.class)) {
			throw new RepositoryException(ErrorEnum.ACTIVE_NOT_BOOLEAN);
		}
	}

}
