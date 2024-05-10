import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

abstract class MusicTask extends DefaultTask {


    @InputFile
    File music

    @TaskAction
    void play() {
        try {
            var res = project.exec {
                commandLine 'mpg123', '-q', music.path
            }
            if (res.exitValue == 0) return
        } catch (Exception ignored) {
        }
        print("\n\nDING!!!\n\n")
    }
}

