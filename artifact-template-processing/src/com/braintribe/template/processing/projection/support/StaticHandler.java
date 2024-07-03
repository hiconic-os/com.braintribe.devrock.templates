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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Allows manipulation of static structure in the static handler template.
 * 
 */
public class StaticHandler {
	
	private List<String> dirsToCreate = new ArrayList<>();
	private List<String> ignoredFiles = new ArrayList<>();
	private Map<String, String> fileRelocations = new HashMap<>();
	
	/**
	 * Creates an empty directory in the installation directory.
	 */
	public void createDir(String dirPath) {
		dirsToCreate.add(dirPath);
	}
	
	/**
	 * Ignores a file or a directory projected into the installation directory.
	 */
	public void ignore(String filePath) {
		ignoredFiles.add(filePath);
	}
	
	/**
	 * Relocates a file or a directory projected in the installation directory.
	 */
	public void relocate(String source, String target) {
		fileRelocations.put(source, target);
	}
	
	public List<String> getDirsToCreate() {
		return dirsToCreate;
	}

	public List<String> getIgnoredFiles() {
		return ignoredFiles;
	}

	public Map<String, String> getFileRelocations() {
		return fileRelocations;
	}
	
}