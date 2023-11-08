@file:Suppress(
    "MISSING_KDOC_TOP_LEVEL",
    "MISSING_KDOC_CLASS_ELEMENTS",
    "KDOC_NO_EMPTY_TAGS",
    "MISSING_KDOC_ON_FUNCTION",
    "FILE_NAME_MATCH_CLASS",
    "MatchingDeclarationName",
)

package com.saveourtool.save.core.utils

import okio.Path

actual fun createProcessBuilderInternal(
    stdoutFile: Path,
    stderrFile: Path,
    useInternalRedirections: Boolean,
): ProcessBuilderInternal = object : ProcessBuilderInternal {
    override fun prepareCmd(command: String): String = error("Not implemented for JS")

    override fun exec(cmd: String, timeOutMillis: Long): Int = error("Not implemented for JS")
}
