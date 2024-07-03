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
package com.braintribe.template.processing.helper;

import static com.braintribe.console.ConsoleOutputs.println;

import com.braintribe.console.output.ConsoleOutput;
import com.braintribe.console.output.ConsoleOutputFiles;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;

public final class ConsoleOutputHelper extends ConsoleOutputFiles {

	public static void outTemplateResolvingResult(CompiledArtifactIdentification resolvingResult) {
		println(templateNameOutput(resolvingResult, 1));
	}

	public static ConsoleOutput templateNameOutput(CompiledArtifactIdentification part, int indentCount) {
		return artifactNameOutput(part.getGroupId(), part.getArtifactId(), part.getVersion().asString(), indentCount);
	}

	public static ConsoleOutput templateNameOutput(String artifactName, int indentCount) {
		String[] artifactNameParts = artifactName.split(":|#");
		return artifactNameOutput(artifactNameParts[0], artifactNameParts[1], artifactNameParts[2], indentCount);
	}

}
