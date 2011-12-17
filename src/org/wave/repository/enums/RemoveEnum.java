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

import java.lang.reflect.Field;

import javax.persistence.EntityManager;

import org.wave.utils.reflection.ReflectionUtil;


/**
 * Define as formas de retirar os dados de uma determinada instancia do repositorio.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 */
public enum RemoveEnum {

	LOGICAL {
		// TODO Remover os objetos em cascata de forma logica.
		@Override
		public <T> void remove(T instance, EntityManager manager) {
			Field activeField = ReflectionUtil.getField(FieldEnum.ACTIVE.getValue(), instance.getClass());
			ReflectionUtil.set(Boolean.FALSE, activeField, instance);

			Field idField = ReflectionUtil.getField(FieldEnum.ID.getValue(), instance.getClass());
			if (ReflectionUtil.get(idField, instance) == null) {
				manager.persist(instance);
			} else {
				manager.merge(instance);
			}

		}
	},
	PHYSICAL {
		@Override
		public <T> void remove(T instance, EntityManager manager) {
			if (manager.contains(instance)) {
				manager.remove(instance);
			} else {
				Field field = ReflectionUtil.getField(FieldEnum.ID.getValue(), instance.getClass());
				Object id = ReflectionUtil.get(field, instance);

				if (manager.find(instance.getClass(), id) != null) {
					manager.remove(manager.merge(instance));
				}
			}
		}
	};

	public abstract <T> void remove(T instance, EntityManager manager) throws Exception;

}
