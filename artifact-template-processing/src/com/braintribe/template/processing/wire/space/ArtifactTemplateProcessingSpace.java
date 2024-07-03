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
package com.braintribe.template.processing.wire.space;

import java.io.File;

import com.braintribe.devrock.templates.config.model.ArtifactTemplatesConfiguration;
import com.braintribe.gm.config.wire.contract.ModeledConfigurationContract;
import com.braintribe.template.processing.ArtifactTemplateProcessor;
import com.braintribe.template.processing.projection.ArtifactTemplateFreeMarkerProjector;
import com.braintribe.template.processing.projection.ArtifactTemplateRequestFreeMarkerProjector;
import com.braintribe.template.processing.wire.contract.ArtifactTemplateProcessingContract;
import com.braintribe.ve.api.VirtualEnvironment;
import com.braintribe.ve.impl.OverridingEnvironment;
import com.braintribe.ve.impl.StandardEnvironment;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import freemarker.template.Configuration;
import freemarker.template.Version;

@Managed
public class ArtifactTemplateProcessingSpace implements ArtifactTemplateProcessingContract {

	private static final Version FREEMARKER_VERSION = Configuration.VERSION_2_3_28;

	@Import
	private ModeledConfigurationContract modelledConfiguration;

	@Managed
	@Override
	public ArtifactTemplateProcessor artifactTemplateProcessor() {
		ArtifactTemplatesConfiguration config = modelledConfiguration.config(ArtifactTemplatesConfiguration.T);
		String repositoryConfigurationLocation = config.getRepositoryConfigurationLocation();
		
		ArtifactTemplateProcessor bean = new ArtifactTemplateProcessor();
		bean.setVirtualEnvironment(virtualEnvironment());
		bean.setRequestProjector(requestProjector());
		bean.setTemplateProjector(templateProjector());
		bean.setModeledConfiguration(modelledConfiguration.config());
		
		if (repositoryConfigurationLocation != null)
			bean.setUseCaseRepositoryConfigurationLocation(new File(repositoryConfigurationLocation));
		return bean;
	}

	@Managed
	private ArtifactTemplateRequestFreeMarkerProjector requestProjector() {
		ArtifactTemplateRequestFreeMarkerProjector bean = new ArtifactTemplateRequestFreeMarkerProjector(FREEMARKER_VERSION, modelledConfiguration.config());
		return bean;
	}

	@Managed
	private ArtifactTemplateFreeMarkerProjector templateProjector() {
		ArtifactTemplateFreeMarkerProjector bean = new ArtifactTemplateFreeMarkerProjector(FREEMARKER_VERSION, modelledConfiguration.config());
		return bean;
	}

	@Managed
	private VirtualEnvironment virtualEnvironment() {
		OverridingEnvironment bean = new OverridingEnvironment(StandardEnvironment.INSTANCE);
		bean.setEnv("PROFILE_USECASE", "CORE");
		return bean;
	}

}