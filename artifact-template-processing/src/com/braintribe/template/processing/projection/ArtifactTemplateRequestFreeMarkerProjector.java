// ============================================================================
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
// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License along with this library; See http://www.gnu.org/licenses/.
// ============================================================================
package com.braintribe.template.processing.projection;

import static com.braintribe.utils.lcd.CollectionTools2.asMap;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.braintribe.devrock.templates.model.ArtifactTemplateRequest;
import com.braintribe.exception.Exceptions;
import com.braintribe.gm.config.api.ModeledConfiguration;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.SimpleTypes;
import com.braintribe.template.processing.api.ArtifactTemplateRequestProjector;
import com.braintribe.template.processing.projection.support.TemplateSupport;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

/**
 * 
 * Using FreeMarker, projects the entity property values.
 * 
 */
public class ArtifactTemplateRequestFreeMarkerProjector implements ArtifactTemplateRequestProjector {

	private final Version freeMarkerVersion;
	private final ModeledConfiguration modeledConfiguration;
	
	public ArtifactTemplateRequestFreeMarkerProjector(Version freeMarkerVersion, ModeledConfiguration modeledConfiguration) {
		this.freeMarkerVersion = freeMarkerVersion;
		this.modeledConfiguration = modeledConfiguration;
	}

	@Override
	public void project(ArtifactTemplateRequest request) {
		Map<String, Object> dataModel = asMap("request", request, "support", new TemplateSupport(request, modeledConfiguration));

		Configuration freeMarkerConfig = new Configuration(freeMarkerVersion);

		for (Property property : request.entityType().getProperties()) {
			if (property.getType() != SimpleTypes.TYPE_STRING)
				continue;
			
			String value = property.get(request);
			if (value == null)
				continue;

			try {
				property.set(request, resolveTemplate(freeMarkerConfig, value, dataModel));
			} catch (Exception e) {
				throw Exceptions.unchecked(e, "FreeMarker failed while processing " + request.entityType().getShortName() + "." + property.getName()
						+ "'s value '" + value + "'.");
			}
		}
	}

	private String resolveTemplate(Configuration freeMarkerConfig, String templatedString, Map<String, Object> dataModel) throws Exception {
		try (StringReader in = new StringReader(templatedString); Writer out = new StringWriter()) {
			Template template = new Template("", in, freeMarkerConfig);
			template.process(dataModel, out);
			return out.toString();
		}
	}
	
}
