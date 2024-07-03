// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.devrock.templates.model;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@PositionalArguments({ "name", "value" })
public interface Property extends GenericEntity {

	EntityType<Property> T = EntityTypes.T(Property.class);

	@Description("The name of the property.")
	@Mandatory
	@Alias("nm")
	String getName();
	void setName(String name);

	@Description("The value of the property.")
	@Mandatory
	@Alias("vl")
	String getValue();
	void setValue(String value);

	static Property create(String name, String value) {
		Property result = Property.T.create();
		result.setName(name);
		result.setValue(value);

		return result;
	}
}
