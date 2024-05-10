import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class JavadocTask extends DefaultTask {

    @Input
    abstract ListProperty<String> getArguments()

    @Input
    abstract Property<String> getClasspath()

    @InputDirectory
    abstract DirectoryProperty getSourceDir()

    @OutputDirectory
    abstract DirectoryProperty getBuildDir()

    @TaskAction
    void run() {
        def javaFiles = sourceDir
                .asFileTree
                .matching { include '**/*.java' }
                .files.path

        project.exec {
            commandLine = [
                    'javadoc',
                    *arguments.getOrElse([]),
                    '-d', buildDir.get(),
                    '-sourcepath', sourceDir,
                    '-cp', classpath.getOrElse(""),
                    *javaFiles
            ]
        }
    }
}

