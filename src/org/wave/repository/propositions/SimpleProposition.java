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

public abstract class SimpleProposition extends Proposition {

	private String fieldName;

	private Object[] values;

	public SimpleProposition(String fieldName, Object... values) {
		this.fieldName = fieldName;
		this.values = values;
	}

	@Override
	public Object getSubject() {
		return fieldName;
	}

	@Override
	public Object[] getPredicative() {
		return values;
	}

	@Override
	public void fillQLString(StringBuilder builder) {
		builder.append("o.");
		builder.append(this.fieldName);
		builder.append(this.getVerb().getValue());
		builder.append(":");
		builder.append(this.fieldName.replace(".", ""));
		builder.append(String.valueOf(this.hashCode()));
	}

	@Override
	public void setParameters(Query query) {
		query.setParameter(this.fieldName.replace(".", "") + String.valueOf(this.hashCode()), this.values[0]);
	}

}
