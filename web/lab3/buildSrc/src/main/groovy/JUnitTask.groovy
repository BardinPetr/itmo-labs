import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class JUnitTask extends DefaultTask {

    @Input
    abstract ListProperty<String> getArguments()

    @Input
    abstract Property<Configuration> getTestConfiguration()

    @InputDirectory
    abstract DirectoryProperty getSourceClasses()

    @InputDirectory
    abstract DirectoryProperty getTestClasses()

    @OutputDirectory
    abstract DirectoryProperty getReportDir()

    @TaskAction
    void run() {
        def junitJar = getTesterJar()

        project.exec {
            commandLine = [
                    "${project.javaDir}/bin/java",
                    '-jar', junitJar,
                    *arguments.getOrElse([]),
                    '-cp', testConfiguration.get().asPath,
                    '-cp', testClasses.get(),
                    '-cp', sourceClasses.get(),
                    '--reports-dir', reportDir.get(),
                    '--scan-class-path'
            ]
        }
    }

    private File getTesterJar() {
        def jarURL = 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.0-M1/junit-platform-console-standalone-1.11.0-M1.jar'
        def jar = project.layout.buildDirectory.file('junit.jar').get().asFile
        if (!jar.exists()) {
            project.exec {
                commandLine 'wget', '-O', jar.path, jarURL
            }
        }
        return jar
    }
}

