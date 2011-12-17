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

import javax.persistence.Query;

import org.wave.repository.propositions.Proposition;


/**
 * Define o local onde o valor esteja presente no atributo.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 *
 */
public enum LikeEnum {

	START {
		@Override
		public void setParameters(Proposition proposition, Query query) {
			query.setParameter(proposition.getSubject() + String.valueOf(proposition.hashCode()), proposition.getPredicative()[0] + "%");
		}
	},
	ANY_WHERE {
		@Override
		public void setParameters(Proposition proposition, Query query) {
			query.setParameter(proposition.getSubject() + String.valueOf(proposition.hashCode()), "%" + proposition.getPredicative()[0] + "%");
		}
	},
	END {
		@Override
		public void setParameters(Proposition proposition, Query query) {
			query.setParameter(proposition.getSubject() + String.valueOf(proposition.hashCode()), "%" + proposition.getPredicative()[0]);
		}
	};

	public abstract void setParameters(Proposition proposition, Query query);

}
