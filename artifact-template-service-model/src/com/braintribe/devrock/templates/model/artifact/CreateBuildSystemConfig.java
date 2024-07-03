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
import com.braintribe.devrock.templates.model.Dependency;
import com.braintribe.devrock.templates.model.Property;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Description("Creates the artifact build system configuration files.")
@PositionalArguments({"groupId", "artifactId", "version", "artifactType", "packaging"})
public interface CreateBuildSystemConfig extends ArtifactTemplateRequest {

	EntityType<CreateBuildSystemConfig> T = EntityTypes.T(CreateBuildSystemConfig.class);
	
	@Description("The build system name. Currently available options are 'bt-ant' and 'maven'.")
	@Alias("bs")
	@Initializer("'bt-ant'")
	String getBuildSystem();
	void setBuildSystem(String buildSystem);
	
	@Description("The group id of the artifact.")
	@Alias("gid")
	@Mandatory
	String getGroupId();
	void setGroupId(String groupId);

	@Description("The artifact id of the artifact.")
	@Alias("aid")
	@Mandatory
	String getArtifactId();
	void setArtifactId(String artifactId);
	
	@Description("The version of the artifact.")
	@Alias("v")
	@Initializer("'1.0'")
	String getVersion();
	void setVersion(String version);
	
	@Description("Specifies whether or not the artifact has parent.")
	@Alias("hp")
	@Initializer("true")
	boolean getHasParent();
	void setHasParent(boolean hasParent);
	
	@Description("The parent artifact id of the artifact. Ignored if hasParent is set to 'false'.")
	@Alias("paid")
	@Initializer("'parent'")
	String getParentArtifactId();
	void setParentArtifactId(String parentArtifactId);
	
	@Description("The artifact type, e.g. 'common', 'library', 'model', ... If not specified, the build system default is used.")
	@Alias("at")
	@Mandatory
	String getArtifactType();
	void setArtifactType(String artifactType);
	
	@Description("The packaging of the artifact, e.g. 'jar', 'war', ... If not specified, the build system default is used.")
	@Alias("p")
	String getPackaging();
	void setPackaging(String packaging);
	
	@Description("The properties of the artifact.")
	@Alias("props")
	List<Property> getProperties();
	void setProperties(List<Property> properties);
	
	@Description("The dependencies of the artifact.")
	@Alias("deps")
	List<Dependency> getDependencies();
	void setDependencies(List<Dependency> dependencies);
	
	@Description("The managed dependencies of the artifact.")
	@Alias("mdeps")
	List<Dependency> getManagedDependencies();
	void setManagedDependencies(List<Dependency> managedDependencies);
	
	@Description("The paths of the artifact resources to be included in the build.")
	List<String> getResources();
	void setResources(List<String> resources);
	
	@Override
	default String template() {
		return "com.braintribe.devrock.templates:build-system-template#2.0";
	}
	
}
