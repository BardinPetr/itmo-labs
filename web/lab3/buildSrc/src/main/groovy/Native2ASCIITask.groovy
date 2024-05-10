import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

import java.util.stream.Collectors

abstract class Native2ASCIITask extends DefaultTask {


    @InputDirectory
    abstract DirectoryProperty getInputDir()

    @TaskAction
    void convert() {
        inputDir.getAsFileTree()
                .matching { include '**/*.properties' }
                .forEach {
                    it.text = it.text
                            .codePoints()
                            .mapToObj { (it > 0x80 ? "\\u%04x" : "%c").formatted(it) }
                            .collect(Collectors.joining())
                }
    }
}

