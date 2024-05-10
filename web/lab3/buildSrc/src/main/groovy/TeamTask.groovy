import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.Directory
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path

abstract class TeamTask extends DefaultTask {
    @TaskAction
    void run() {
        def repoURL = getRepoURL()
        def rev = getCurrentRevision()
        if (rev < 2)
            throw new GradleException("Not enough revisions")

        def destDir = project.layout.buildDirectory.dir("team").get()
        destDir.asFile.mkdir()

        for (def i : [rev - 2, rev - 1])
            compile(destDir, repoURL, i)
    }

    private void compile(Directory outDir, String repo, int rev) {
        def tempDir = File.createTempDir()

        println("Checking out revision r${rev} in ${tempDir}")
        call("svn", "checkout", "-r", rev.toString(), repo, tempDir.path)

        def res = project.exec {
            commandLine "./gradlew", "war"
            workingDir tempDir
        }
        if (res.exitValue != 0)
            throw new GradleException("Failed to build")

        def war = project
                .fileTree(tempDir)
                .matching { include("**/*.war") }
                .singleFile

        Files.copy(Path.of(war.path), Path.of("${outDir}/r${rev}.war"))

        println("Clearing revision r${rev}")
        tempDir.deleteDir()
    }

    private int getCurrentRevision() {
        def output = call("svn", "info", "--show-item", "revision")
        if (output.empty)
            throw new GradleException("SVN failed")
        return Integer.parseInt(output.get())
    }

    private String getRepoURL() {
        def output = call("svn", "info", "--show-item", "url")
        if (output.empty)
            throw new GradleException("SVN failed")
        return output.get()
    }

    private Optional<String> call(String... inputCommandline) {
        return new ByteArrayOutputStream().withStream { os ->
            def res = project.exec {
                commandLine = inputCommandline
                standardOutput = os
            }
            if (res.exitValue != 0)
                return Optional.empty()
            return Optional.of(os.toString().strip())
        } as Optional<String>
    }
}
