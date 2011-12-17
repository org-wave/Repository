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

public abstract class CompoundProposition extends Proposition {

	private Proposition subject;

	private Proposition[] predicative;

	public CompoundProposition(Proposition subject, Proposition... predicative) {
		this.subject = subject;
		this.predicative = predicative;
	}

	@Override
	public Object getSubject() {
		return subject;
	}

	@Override
	public Object[] getPredicative() {
		return predicative;
	}

	@Override
	public void fillQLString(StringBuilder builder) {
		if (this.subject != null) {
			builder.append("(");
			this.subject.fillQLString(builder);
		}

		for (Proposition proposition : this.predicative) {
			builder.append(this.getVerb().getValue());

			if (this.subject == null) {
				builder.append("(");
			}

			proposition.fillQLString(builder);
		}

		builder.append(")");
	}

	@Override
	public void setParameters(Query query) {
		if (this.subject != null) {
			this.subject.setParameters(query);
		}

		for (Proposition proposition : this.predicative) {
			proposition.setParameters(query);
		}
	}

}
