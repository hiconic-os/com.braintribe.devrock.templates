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

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@PositionalArguments({"groupId", "artifactId", "version"})
public interface Dependency extends GenericEntity {

	EntityType<Dependency> T = EntityTypes.T(Dependency.class);
	
	@Description("The group id of the dependency.")
	@Mandatory
	@Alias("gid")
	String getGroupId();
	void setGroupId(String groupId);

	@Description("The artifact id of the dependency.")
	@Mandatory
	@Alias("aid")
	String getArtifactId();
	void setArtifactId(String artifactId);
	
	@Description("The version of the dependency.")
	@Alias("v")
	String getVersion();
	void setVersion(String version);
	
	@Description("The scope of the dependency.")
	@Alias("s")
	String getScope();
	void setScope(String scope);
	
	@Description("The classifier of the dependency.")
	@Alias("c")
	String getClassifier();
	void setClassifier(String classifier);
	
	@Description("The type of the dependency.")
	@Alias("t")
	String getType();
	void setType(String type);
	
	@Description("The tags of the dependency, e.g. asset, ...")
	List<String> getTags();
	void setTags(List<String> tags);
	
	@Description("The excluded transitive dependencies of the dependency.")
	List<Dependency> getExclusions();
	void setExclusions(List<Dependency> exclusions);
	
}
