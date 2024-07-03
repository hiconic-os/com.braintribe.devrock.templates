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

import java.util.List;

import com.braintribe.devrock.templates.model.ArtifactTemplateRequest;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Description("Creates the artifact source control configuration files.")
public interface CreateSourceControlConfig extends ArtifactTemplateRequest {

	EntityType<CreateSourceControlConfig> T = EntityTypes.T(CreateSourceControlConfig.class);
	
	@Description("The source control name. Currently only 'git' is available.")
	@Alias("sc")
	@Initializer("'git'")
	String getSourceControl();
	void setSourceControl(String sourceControl);
	
	@Description("The build system name. If provided, the standard build system output files will be ignored. Currently available options are 'bt-ant' and 'maven'.")
	@Alias("bs")
	String getBuildSystem();
	void setBuildSystem(String buildSystem);
	
	@Description("The paths of the files to be ignored when checking artifact into source control.")
	@Alias("if")
	List<String> getIgnoredFiles();
	void setIgnoredFiles(List<String> ignoredFiles);
	
	@Override
	default String template() {
		return "com.braintribe.devrock.templates:source-control-configuration-template#2.0";
	}
	
}
