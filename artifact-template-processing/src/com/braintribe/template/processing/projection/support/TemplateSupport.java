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
package com.braintribe.template.processing.projection.support;

import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.WordUtils;

import com.braintribe.devrock.templates.model.ArtifactTemplateRequest;
import com.braintribe.devrock.templates.model.Dependency;
import com.braintribe.devrock.templates.model.Property;
import com.braintribe.exception.Exceptions;
import com.braintribe.gm.config.api.ModeledConfiguration;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.version.Version;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.StringTools;
import com.braintribe.utils.lcd.NullSafe;

/**
 * Provides the artifact template with a set of utility functions.
 */
public class TemplateSupport {

	private final PomSupport pomSupport;
	private final ModeledConfiguration modeledConfiguration;

	private static final String DEFAULT_ARTIFACT_VERSION = "1.0";

	public TemplateSupport(ArtifactTemplateRequest request, ModeledConfiguration modeledConfiguration) {
		this.pomSupport = new PomSupport(request);
		this.modeledConfiguration = modeledConfiguration;
	}

	public GenericEntity config(String typeSignature) {
		EntityType<GenericEntity> entityType = GMF.getTypeReflection().findEntityType(typeSignature);
		if (entityType == null) {
			throw new IllegalArgumentException("Entity type typeSignature=" + typeSignature + " not found.");
		}
		return modeledConfiguration.config(entityType);
	}

	public GenericEntity config(EntityType<?> type) {
		return modeledConfiguration.config(type);
	}

	/**
	 * Copies property values of the first entity to the second. Properties are copied based on their name, even if the two entity types are
	 * different.
	 */
	public void mapFromTo(GenericEntity from, GenericEntity to) {
		mapFromTo(from, to, Collections.EMPTY_LIST);
	}

	/** Similar to {@link #mapFromTo(GenericEntity, GenericEntity)}, but excludes given properties */
	public void mapFromTo(GenericEntity from, GenericEntity to, List<String> excludedProperties) {
		EntityType<GenericEntity> toType = to.entityType();
		EntityType<GenericEntity> fromType = from.entityType();

		for (int i = 0; i < toType.getProperties().size(); i++) {
			com.braintribe.model.generic.reflection.Property toProperty = toType.getProperties().get(i);
			if (excludedProperties.contains(toProperty.getName())) {
				continue;
			}
			com.braintribe.model.generic.reflection.Property fromProperty = fromType.findProperty(toProperty.getName());
			if (fromProperty != null) {
				toProperty.set(to, from.read(fromProperty));
			}
		}
	}

	/** Equivalent to {@code Version.parse(versionAsString)) } */
	public Version createVersionFromString(String versionAsString) {
		return Version.parse(versionAsString);
	}

	public String generateRandomUUID() {
		return UUID.randomUUID().toString();
	}

	/** Example: toPascalCase("foo-bar", "-") -> "FooBar" */
	public String toPascalCase(String originalString, Character delimiter) {
		NullSafe.nonNull(originalString, "value");
		NullSafe.nonNull(delimiter, "delimiter");
		return WordUtils.capitalizeFully(originalString, new char[] { delimiter }).replace(Character.toString(delimiter), "");
	}

	/** Returns a file name targeted by the provided path. */
	public String getFileName(String path) {
		try {
			return Paths.get(path).toRealPath().getFileName().toString();
		} catch (IOException e) {
			throw Exceptions.unchecked(e,
					"Failed getting file name from '" + path + "'. Check if the specified path is correct and the file exists.");
		}
	}

	public String getFileNameWithoutExtension(String filename) {
		return FileTools.getNameWithoutExtension(filename);
	}

	public String getFileNameExtension(String filename) {
		return FileTools.getExtension(filename);
	}

	public String smartPackageName(String basePackage, String name) {
		String lastPartOfPackage = StringTools.findSuffix(basePackage, ".");
		if (name.equals(lastPartOfPackage))
			name = "";
		else if (name.startsWith(lastPartOfPackage + "_"))
			name = StringTools.removeFirstNCharacters(name, lastPartOfPackage.length() + 1);

		return name.isEmpty() ? basePackage : basePackage + "." + name;
	}

	public String getDefaultArtifactVersion(String buildSystem) {
		if (buildSystem == null)
			return DEFAULT_ARTIFACT_VERSION;

		switch (buildSystem) {
			case "dr":
			case "bt-ant":
			case "maven":
				return pomSupport.getDefaultArtifactVersionFromParentPom(DEFAULT_ARTIFACT_VERSION);

			default:
				return DEFAULT_ARTIFACT_VERSION;
		}
	}

	public void ensuerDependencyVersions(String... dependencies) {
		pomSupport.ensuerDependencyVersions(dependencies);
	}

	public List<Dependency> distinctDependencies(List<Dependency> dependencies) {
		Map<String, Dependency> distinctDeps = new LinkedHashMap<String, Dependency>();
		dependencies.stream().forEach(d -> {
			distinctDeps.put(d.getGroupId() + ":" + d.getArtifactId(), d);
		});

		return newList(distinctDeps.values());
	}

	public List<Property> distinctProperties(List<Property> properties) {
		Map<String, Property> distinctProps = new LinkedHashMap<String, Property>();
		properties.stream().forEach(p -> {
			distinctProps.put(p.getName(), p);
		});

		return newList(distinctProps.values());
	}

}