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
package com.braintribe.template.processing.projection.support;

import com.braintribe.template.processing.projection.StopTemplateProjectionException;

/**
 * 
 * Allows templates to control their projection.
 * 
 */
public class TemplateHandler {

	private String relocationTarget;

	/**
	 * Ignores the template projection.
	 */
	public void ignore() throws StopTemplateProjectionException{
		throw new StopTemplateProjectionException();
	}

	/**
	 * 
	 * Relocates the template projection in the installation directory. 
	 * If not set, the template is projected based on the current path in the 'projected' directory with the template extension stripped.
	 * 
	 */
	public void relocate(String relocationTarget) {
		this.relocationTarget = relocationTarget;
	}

	
	public String getRelocationTarget() {
		return relocationTarget;
	}
	
}