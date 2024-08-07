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

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.FolderName;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

@Abstract
@Description("The base request of all the artifact template requests.")
@PositionalArguments({ "installationPath", "directoryName" })
public interface ArtifactTemplateRequest extends ServiceRequest {
	
	EntityType<ArtifactTemplateRequest> T = EntityTypes.T(ArtifactTemplateRequest.class);
	
	@Description("The installation path of the artifact template projection.")
	@Alias("ip")
	@Initializer("'.'")
	@FolderName
	String getInstallationPath();
	void setInstallationPath(String installationPath);
	
	@Description("The directory name of the artifact template projection. If specified, the directory name is appended to the installation path.")
	@Alias("dn")
	String getDirectoryName();
	void setDirectoryName(String directoryName);
	
	@Description("Specifies whether the template projection should overwrite existing files in case they exist or report an error.")
	@Alias("o")
	boolean getOverwrite();
	void setOverwrite(boolean overwrite);

	@Description("Fully qualified artifact id of the template. Typically null, as each request has its default, but this can override that default.")
	String getTemplate();
	void setTemplate(String template);

	/**
	 * Provides the fully-qualified name of the artifact template associated with the request.
	 */
	default String template() {
		return null;
	}

	/**
	 * If {@code true}, no directory will be projected for this template. Instead, the template is expected to evaluate other templates in its
	 * dependencies.groovy, which is also expected to return an empty list of dependencies (not that dependencies would be projected into the same
	 * directory).
	 */
	default boolean delegatingOnly() {
		return false;
	}
	
	@Override
	EvalContext<? extends ArtifactTemplateResponse> eval(Evaluator<ServiceRequest> evaluator);

}
