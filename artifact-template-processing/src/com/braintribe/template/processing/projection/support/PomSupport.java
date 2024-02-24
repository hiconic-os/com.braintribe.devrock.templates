package com.braintribe.template.processing.projection.support;

import static com.braintribe.console.ConsoleOutputs.brightRed;
import static com.braintribe.console.ConsoleOutputs.print;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.braintribe.artifact.declared.marshaller.DeclaredArtifactMarshaller;
import com.braintribe.devrock.templates.model.ArtifactTemplateRequest;
import com.braintribe.exception.Exceptions;
import com.braintribe.model.artifact.declared.DeclaredArtifact;
import com.braintribe.model.version.Version;
import com.braintribe.utils.DOMTools;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.template.Template;

/**
 * @author peter.gazdik
 */
/* package */ class PomSupport {

	private final ArtifactTemplateRequest request;

	public PomSupport(ArtifactTemplateRequest request) {
		this.request = request;
	}

	public String getDefaultArtifactVersionFromParentPom(String defaultVersion) {
		try {
			String versionAsString = readVersionFromPom();
			Version v = Version.parse(versionAsString);
			return v.getMajor() + "." + v.getMinor();

		} catch (Exception e) {
			return defaultVersion;
		}
	}

	private String readVersionFromPom() {
		Path parentPom = parentPomPath();

		DeclaredArtifact da = readPom(parentPom);

		return Template.merge(da.getVersion(), (Map<String, Object>) (Object) da.getProperties());
	}

	public void ensureDependencyVersions(String... dependencies) {
		if (dependencies == null || dependencies.length == 0)
			return;

		if (dependencies.length % 2 == 1)
			throw new IllegalArgumentException("Expected even number of elements - pairs of <groupId, version> - but got: " + dependencies);

		Path parentPom = parentPomPath();

		Map<String, String> missingVars = findMissingGroupVars(parentPom, dependencies);

		addGroupVarsToPom(parentPom, missingVars);
	}

	private Map<String, String> findMissingGroupVars(Path parentPom, String... dependencies) {
		DeclaredArtifact da = readPom(parentPom);

		Map<String, String> result = newMap();
		Map<String, String> properties = da.getProperties();

		int i = 0;
		do {
			String groupId = dependencies[i++];
			String version = dependencies[i++];

			String groupVar = "V." + groupId;

			if (!properties.containsKey(groupVar))
				result.put(groupVar, version);

		} while (i < dependencies.length);

		return result;
	}

	private DeclaredArtifact readPom(Path pom) {
		DeclaredArtifactMarshaller marshaller = new DeclaredArtifactMarshaller();

		try (InputStream is = Files.newInputStream(pom)) {

			return marshaller.unmarshall(is);

		} catch (Exception e) {
			throw Exceptions.unchecked(e, "Failed to parse pom.xml from provided path " + pom);
		}
	}

	/* Parses the pom as Document and inserts elements for missing properties at the end of <properties>. Tries to use the original padding. */
	private void addGroupVarsToPom(Path pom, Map<String, String> missingVars) {
		if (missingVars.isEmpty())
			return;

		Document document = DOMTools.parse(FileTools.read(pom).asString());
		Element projectElement = document.getDocumentElement();

		Element propsElement = DOMTools.getElementByPath(projectElement, "properties");
		if (propsElement == null) {
			print(brightRed("WARN: Will not add group variables to parent pom: " + pom
					+ ". This pom doesn't contain <properties> element, which is unexpected." + varsToAdd(missingVars)));
			return;
		}

		Node child = propsElement.getFirstChild();
		if (child == null) {
			print(brightRed("WARN: Will not add group variables to parent pom: " + pom + ", <properties> element is empty, which is unexpected."
					+ varsToAdd(missingVars)));
			return;
		}

		Text newLineAndPadding = null;
		if (child instanceof Text t) {
			newLineAndPadding = (Text) t.cloneNode(true);
			document.adoptNode(newLineAndPadding);

		} else {
			newLineAndPadding = document.createTextNode("\n        ");
		}

		while (child.getNextSibling() != null)
			child = child.getNextSibling();

		for (Entry<String, String> e : missingVars.entrySet()) {
			String varName = e.getKey();
			String varValue = e.getValue();

			Text varValueNode = document.createTextNode(varValue);

			Element varElement = document.createElement(varName);
			varElement.appendChild(varValueNode);

			propsElement.insertBefore(newLineAndPadding, child);
			propsElement.insertBefore(varElement, child);

			newLineAndPadding = (Text) newLineAndPadding.cloneNode(true);
			document.adoptNode(newLineAndPadding);
		}

		String xml = DOMTools.toString(document);

		FileTools.write(pom).string(xml);
	}

	private String varsToAdd(Map<String, String> missingVars) {
		StringJoiner sj = new StringJoiner("\n    ", " Add the following properties to your parent's pom manually:", "");

		for (Entry<String, String> entry : missingVars.entrySet()) {
			String gid = entry.getKey();
			String version = entry.getValue();

			sj.add("<V." + gid + ">" + version + "</V." + gid + ">");
		}

		return sj.toString();
	}

	private Path parentPomPath() {
		return Paths.get(request.getInstallationPath(), "parent", "pom.xml");
	}

}
