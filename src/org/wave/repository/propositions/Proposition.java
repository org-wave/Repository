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
package org.wave.repository.propositions;

import javax.persistence.Query;

import org.wave.repository.enums.VerbEnum;


/**
 * E uma sentenca que afirma ou nega um predicado de um sujeito. Em outras palavras, uma proposicao define um determinado perfil para as instancias
 * armazenadas no repositorio.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see And
 * @see Between
 * @see Equals
 * @see Greater
 * @see GreaterEquals
 * @see In
 * @see IsNotNull
 * @see IsNull
 * @see Lesser
 * @see LesserEquals
 * @see Like
 * @see Not
 * @see NotEquals
 * @see NotIn
 * @see Or
 * @see Order
 * 
 */
public abstract class Proposition implements Comparable<Proposition> {

	public abstract Object getSubject();

	public abstract VerbEnum getVerb();

	public abstract Object[] getPredicative();

	public abstract void fillQLString(StringBuilder builder);

	public abstract void setParameters(Query query);

	@Override
	public int compareTo(Proposition proposition) {
		if (this.getVerb().equals(VerbEnum.ORDER_BY) && !proposition.getVerb().equals(VerbEnum.ORDER_BY)) {
			return 1;
		}

		return 0;
	}

}
