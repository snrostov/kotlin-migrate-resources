import org.zeroturnaround.exec.ProcessExecutor
import java.io.File
import java.nio.file.Files


fun main(args: Array<String>) {
    File("data.txt").forEachLine {
        if (it.startsWith("!!! ")) {
            val srcRoot = File(it.removePrefix("!!! "))
            if (srcRoot.isDirectory) {
                val resDir = srcRoot.parentFile.resolve("resources")
                resDir.mkdir()
                val metaInf = srcRoot.resolve("META-INF")
                if (metaInf.isDirectory) {
                    metaInf.walk()
                        .filter { it.isFile }
                        .toList()
                        .forEach {
                            move(it, srcRoot, resDir)
                        }
                }
                srcRoot.walk()
                    .filter { it.extension == "properties" }
                    .toList()
                    .forEach {
                        move(it, srcRoot, resDir)
                    }
            } else {
                println("not a dir: $srcRoot")
            }
        }
    }
}

val root = File("/Users/jetbrains/tasks")

private fun move(src: File, srcRoot: File, resRoot: File) {
    println(src)

    val relative = src.path.substring(srcRoot.path.length + 1)
    val dest = resRoot.resolve(relative)
    dest.parentFile.mkdirs()

    val cmd = arrayOf("git", "mv", src.path, dest.path)
    ProcessExecutor()
        .directory(root)
        .command(*cmd)
        .redirectError(System.err)
        .redirectOutput(System.out)
        .execute()
}