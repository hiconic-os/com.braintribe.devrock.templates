<#if request.buildSystem != 'maven'>	
	${template.ignore()}	
</#if>	
<?xml version="1.0" encoding="UTF-8"?>	
<project xmlns="http://maven.apache.org/POM/4.0.0"	
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"	
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">	
	<modelVersion>4.0.0</modelVersion>	
	<groupId>${request.groupId}</groupId>	
	<artifactId>group-builder</artifactId>	
	<version>1.0</version>	
	<packaging>pom</packaging>	
	<modules>
<#list request.builtArtifactIds as baid>
		<module>${baid}</module>
</#list>
	</modules>	
</project>	