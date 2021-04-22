@file:JvmName("ProcessBuilderJvm")

package org.cqfn.save.core.utils

import org.cqfn.save.core.logging.logDebug

import java.lang.ProcessBuilder

@Suppress("MISSING_KDOC_TOP_LEVEL",
    "MISSING_KDOC_CLASS_ELEMENTS",
    "MISSING_KDOC_ON_FUNCTION"
)
actual class ProcessBuilderInternal {
    actual fun prepareCmd(command: String): String {
        val shell = if (System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) listOf("CMD", "/C") else listOf("sh", "-c")
        val cmd = shell + listOf(command) + listOf(" >$stdoutFile 2>$stderrFile")
        return cmd.joinToString(" ")
    }

    private val pb = ProcessBuilder()

    actual fun exec(cmd: String): Int {
        logDebug("Executing: $cmd")
        // TODO: Does status == -1 responsible for errors like in posix?
        val status = pb.command((cmd.split(" ")))
            .start()
            .waitFor()
        return status
    }
}
