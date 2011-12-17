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
 * Define que o atributo de uma instancia esteja compreendido entre 2 valores, incluindo esses. 
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 *
 */
public class Between extends SimpleProposition {

	public Between(String fieldName, Object value1, Object value2) {
		super(fieldName, value1, value2);
	}

	@Override
	public VerbEnum getVerb() {
		return VerbEnum.BETWEEN;
	}

	@Override
	public void fillQLString(StringBuilder builder) {
		builder.append("o.");
		builder.append(this.getSubject());
		builder.append(this.getVerb().getValue());
		builder.append(":min");
		builder.append(String.valueOf(this.hashCode()));
		builder.append(" and ");
		builder.append(":max");
		builder.append(String.valueOf(this.hashCode()));
	}

	@Override
	public void setParameters(Query query) {
		query.setParameter("min" + String.valueOf(this.hashCode()), this.getPredicative()[0]);
		query.setParameter("max" + String.valueOf(this.hashCode()), this.getPredicative()[1]);
	}

}
