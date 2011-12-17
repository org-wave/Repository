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

import java.util.List;

import org.wave.repository.enums.VerbEnum;
import org.wave.utils.collection.CollectionUtil;


/**
 * Define que o atributo de uma instancia nao seja igual a um dos valores. 
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 *
 */
public class NotIn extends SimpleProposition {

	public NotIn(String fieldName, Object... values) {
		this(fieldName, CollectionUtil.convert(values));
	}

	public NotIn(String fieldName, List<?> list) {
		super(fieldName, list);
	}

	@Override
	public VerbEnum getVerb() {
		return VerbEnum.NOT_IN;
	}

	@Override
	public void fillQLString(StringBuilder builder) {
		builder.append("o.");
		builder.append(this.getSubject());
		builder.append(this.getVerb().getValue());
		builder.append("(");
		builder.append(":");
		builder.append(this.getSubject());
		builder.append(String.valueOf(this.hashCode()));
		builder.append(")");
	}

}
