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
package com.braintribe.devrock.templates.model.artifact;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Description("Creates a js-library artifact.")
public interface CreateJsLibrary extends CreateArtifact {

	EntityType<CreateJsLibrary> T = EntityTypes.T(CreateJsLibrary.class);
	
	@Initializer("'vscode'")
	@Override
	String getIde();

	@Description("Specifies whether or not this library is an asset - if set to 'true' according asset.man file will be projected.")
	@Alias("a")
	boolean getAsset();
	void setAsset(boolean asset);
	
	@Override
	default String template() {
		return "com.braintribe.devrock.templates:js-library-template#2.0";
	}
	
}
