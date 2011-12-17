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

import org.wave.repository.enums.OrderEnum;
import org.wave.repository.enums.VerbEnum;


/**
 * Define a ordem de retorno das instancias.
 * 
 * @author Benedito Barbosa
 * @author Christian Peixoto
 * @author Mauricio Marinho
 * 
 * @see OrderEnum
 *
 */
public class Order extends SimpleProposition {

	private Order(String fieldName, Object value) {
		super(fieldName, value);
	}

	public Order(String fieldName) {
		this(fieldName, OrderEnum.ASC.getValue());
	}

	public Order(String fieldName, OrderEnum orderEnum) {
		this(fieldName, orderEnum.getValue());
	}

	@Override
	public VerbEnum getVerb() {
		return VerbEnum.ORDER_BY;
	}

	@Override
	public void fillQLString(StringBuilder builder) {
		if (builder.indexOf(VerbEnum.ORDER_BY.getValue()) == -1) {
			builder.append(this.getVerb().getValue());
		} else {
			builder.append(", ");
		}

		builder.append("o.");
		builder.append(this.getSubject());
		builder.append(" ");
		builder.append(this.getPredicative()[0]);
	}

	@Override
	public void setParameters(Query query) {

	}

}
