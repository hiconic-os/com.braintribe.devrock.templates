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
package com.braintribe.template.processing;

import com.braintribe.model.artifact.essential.PartIdentification;

public interface ArtifactTemplateConsts {
	
	final String DOT_FTL = ".ftl";
	
	final String DEPENDENCIES_SCRIPT = "dependencies.groovy";
	
	final String CONTENT_DIR = "content";
	
	final String STATIC_DIR = "static";
	final String STATIC_DIR_FULL = CONTENT_DIR + "/" + STATIC_DIR;
	final String DYNAMIC_DIR = "dynamic";
	final String DYNAMIC_DIR_FULL = CONTENT_DIR + "/" + DYNAMIC_DIR;
	final String PROJECTED_DIR = "projected";
	final String PROJECTED_DIR_FULL = DYNAMIC_DIR_FULL + "/" + PROJECTED_DIR;
	final String STATIC_TEMPLATE = "static" + DOT_FTL;
	final String STATIC_TEMPLATE_FULL = DYNAMIC_DIR_FULL + "/" + STATIC_TEMPLATE;

	PartIdentification ARCHIVE_ZIP_PART = PartIdentification.create("archive", "zip");

}
