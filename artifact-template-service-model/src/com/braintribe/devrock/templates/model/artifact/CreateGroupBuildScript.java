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
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Description("Creates a group build script.")
@PositionalArguments({"buildSystem"})
public interface CreateGroupBuildScript extends ArtifactTemplateRequest {

	EntityType<CreateGroupBuildScript> T = EntityTypes.T(CreateGroupBuildScript.class);
	
	@Description("The id of the group.")
	@Alias("gid")
	@Initializer("'${support.getFileName(request.installationPath)}'")
	String getGroupId();
	void setGroupId(String groupId);
	
	@Description("The build system name. Currently available options are 'bt-ant' and 'maven'.")
	@Alias("bs")
	@Initializer("'bt-ant'")
	String getBuildSystem();
	void setBuildSystem(String buildSystem);
	
	@Description("The artifact ids of the artifacts that are to be built by the build script. Needed only when the 'maven' group build script is created.")
	@Alias("baids")
	List<String> getBuiltArtifactIds();
	void setBuiltArtifactIds(List<String> builtArtifactIds);
	
	@Override
	default String template() {
		return "com.braintribe.devrock.templates:group-build-script-template#2.0";
	}

}
