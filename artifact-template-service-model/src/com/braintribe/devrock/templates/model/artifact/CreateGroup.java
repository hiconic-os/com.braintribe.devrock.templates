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

import com.braintribe.devrock.templates.model.ArtifactTemplateRequest;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Description("Creates a group consisting of a group build script and the parent artifact.")
@PositionalArguments({"version", "buildSystem", "ide", "sourceControl"})
public interface CreateGroup extends ArtifactTemplateRequest {

	EntityType<CreateGroup> T = EntityTypes.T(CreateGroup.class);
	
	@Description("The group id of the group.")
	@Alias("gid")
	@Initializer("'${support.getFileName(request.installationPath)}'")
	String getGroupId();
	void setGroupId(String groupId);

	@Description("The initial version of the artifacts in the group.")
	@Alias("v")
	@Initializer("'1.0'")
	String getVersion();
	void setVersion(String version);
	
	@Description("The build system used in the group. Currently available options are 'bt-ant' and 'maven'.")
	@Alias("bs")
	@Initializer("'dr'")
	String getBuildSystem();
	void setBuildSystem(String buildSystem);
	
	@Description("The IDE to create project metadata for. Currently only 'eclipse' is available.")
	@Initializer("'eclipse'")
	String getIde();
	void setIde(String ide);
	
	@Description("The source control to create group configuration for. Currently only 'git' is available.")
	@Alias("sc")
	@Initializer("'git'")
	String getSourceControl();
	void setSourceControl(String sourceControl);
	
	@Override
	default String template() {
		return "com.braintribe.devrock.templates:group-template#2.0";
	}

}
