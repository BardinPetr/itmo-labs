import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

import java.time.LocalDateTime

abstract class ReportPusherTask extends DefaultTask {

    @InputDirectory
    abstract DirectoryProperty getReportBuildDirectory()

    @OutputDirectory
    abstract DirectoryProperty getReportStoreDirectory()

    @TaskAction
    void run() {
        def report = reportBuildDirectory.get()
                .asFileTree
                .filter { it.name.endsWith("-junit-jupiter.xml") }
                .take(1)

        if (report.empty)
            throw new GradleException("Couldn't find report")

        def reportOutput = reportStoreDirectory.get()
                .file("report.xml")
                .asFile

        reportOutput.text = report[0].text

        project.exec {
            commandLine "svn", "add", "--parents", "--force", reportOutput.path
        }
        project.exec {
            commandLine "svn", "commit", "-m", "Test report at ${LocalDateTime.now().dateTimeString}"
        }
        project.exec {
            commandLine "svn", "update"
        }
    }
}

