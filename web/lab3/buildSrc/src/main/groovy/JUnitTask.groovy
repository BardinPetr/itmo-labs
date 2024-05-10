import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
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
    abstract Property<Configuration> getConfiguration()

    @InputDirectory
    abstract DirectoryProperty getSourceClassesDir()

    @InputDirectory
    abstract DirectoryProperty getTestClassesDir()

    @OutputDirectory
    abstract DirectoryProperty getReportDir()

    @TaskAction
    void run() {
        def junitJar = configuration.get().find { it.name.contains("junit-platform-console-standalone") }
        if (junitJar == null)
            throw new GradleException("JAR for junit-platform-console-standalone not found")

        project.exec {
            commandLine = [
                    'java',
                    '-jar', junitJar,
                    *arguments.getOrElse([]),
                    '-cp', configuration.get().asPath,
                    '-cp', testClassesDir.get(),
                    '-cp', sourceClassesDir.get(),
                    '--reports-dir', reportDir.get(),
                    '--scan-class-path'
            ]
        }
    }
}

