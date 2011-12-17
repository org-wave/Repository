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

import java.util.List;

import org.wave.repository.enums.ErrorEnum;
import org.wave.repository.enums.VerbEnum;
import org.wave.repository.exceptions.RepositoryException;
import org.wave.repository.propositions.Proposition;
import org.wave.utils.reflection.ReflectionUtil;


/**
 * Define as regras de construcao para uma proposicao.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see VerbEnum
 */
public class PropositionValidator {

	/**
	 * Verifica se uma determinada proposicao obedece suas respectivas regras de construcao.
	 * 
	 * @param proposition
	 * @throws RepositoryException
	 */
	public void validate(Proposition proposition) throws RepositoryException {
		VerbEnum verbEnum = proposition.getVerb();
		if (verbEnum.requiresSubject() && proposition.getSubject() == null) {
			throw new RepositoryException(ErrorEnum.NULL_SUBJECT);
		}

		if (verbEnum.requiresPredicative() && hasEmptyPredicative(proposition)) {
			throw new RepositoryException(ErrorEnum.EMPTY_PREDICATIVE);
		}

		if (verbEnum.isConnective()) {
			this.validateInternalPropositions(proposition);
		}
	}

	private boolean hasEmptyPredicative(Proposition proposition) {
		Object[] predicative = proposition.getPredicative();

		if (predicative.length == 0) {
			return true;
		}

		for (Object elementOfPredicative : predicative) {
			if (elementOfPredicative == null) {
				return true;
			}

			if (ReflectionUtil.isCollection(elementOfPredicative.getClass())) {
				List<?> list = (List<?>) elementOfPredicative;
				if (list.isEmpty()) {
					return true;
				}

				for (Object elementOfList : list) {
					if (elementOfList == null) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private void validateInternalPropositions(Proposition proposition) throws RepositoryException {
		Proposition subject = (Proposition) proposition.getSubject();
		if (subject != null) {
			this.validateTermOf(subject);
		}

		Proposition[] predicative = (Proposition[]) proposition.getPredicative();
		for (Proposition elementOfPredicative : predicative) {
			this.validateTermOf(elementOfPredicative);
		}
	}

	private void validateTermOf(Proposition proposition) throws RepositoryException {
		if (proposition.getVerb().equals(VerbEnum.ORDER_BY)) {
			throw new RepositoryException(ErrorEnum.ORDER_NOT_ALLOWED);
		}

		this.validate(proposition);
	}

}
