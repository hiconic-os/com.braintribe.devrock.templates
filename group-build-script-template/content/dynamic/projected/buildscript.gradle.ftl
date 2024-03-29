<#if request.buildSystem != 'dr'>
	${template.ignore()}
</#if>
<#noparse>
// setup buildscript dependencies based on the libFolder to make RepositoryConfigurations helper available on classpath
buildscript {
    // Determines devrock sdk home directory either by environment variable or by assuming that
    // the projectDir lies relatively like that DEVROCK_SDK_HOME/env/dev-env/git/group
    String devrockSdkRootVar = System.getenv('DEVROCK_SDK_HOME')
    ext.devrockSdkRoot = devrockSdkRootVar != null?
        new File(devrockSdkRootVar):
        new File(projectDir, '../../../..').toPath().toAbsolutePath().normalize().toFile()
    File libFolder = new File(devrockSdkRoot, 'tools/ant-libs');
    dependencies { classpath files(fileTree(dir: libFolder.absolutePath, include: '*.jar')) }
}

// load and check the repository configuration
File repoConfig = new File(devrockSdkRoot, 'conf/repository-configuration-devrock.yaml');
def configMaybe = devrock.repository.configuration.RepositoryConfigurations.load repoConfig

if (configMaybe.unsatisfied)
    throw new GradleException("Error while loading repository configuration for buildscript: " + configMaybe.whyUnsatisfied().stringify())

// transpose the repository configuration to gradle style and pass to to the callers buildscript
configureBuildscript {

    repositories {
        for (def repo: configMaybe.get().getRepositories()) {
            if (repo instanceof com.braintribe.devrock.model.repository.MavenHttpRepository)
                maven {
                    url repo.url
                    if (repo.user != null) credentials {
                        username repo.user
                        password repo.password
                    }
                }
            else if (repo instanceof com.braintribe.devrock.model.repository.MavenFileSystemRepository)
                maven { url uri("file://${repo.rootPath}") }
        }
    }

}
</#noparse>