package com.elyeproj.customlint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import org.jetbrains.uast.getContainingUFile
import org.jetbrains.uast.getIoFile
import java.io.IOException
import java.io.LineNumberReader
import java.util.*

const val MAX_LINE_IN_CLASS = 250

val ISSUE_CLASS_LINE_EXCEEDED = Issue.create("LinesExceeded",
        "Only $MAX_LINE_IN_CLASS allow in class.",
        "Class should not use more than $MAX_LINE_IN_CLASS lines.",
        Category.CORRECTNESS, 5, Severity.WARNING,
        Implementation(LineDetector::class.java, EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)))

class LineDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes() = listOf(UClass::class.java)
    override fun createUastHandler(context: JavaContext) = LineDetectionHandler(context)

    class LineDetectionHandler(private val context: JavaContext) : UElementHandler() {

        override fun visitClass(node: UClass) {
            val file = node.getContainingUFile()?.getIoFile()
            val lnr = LineNumberReader(file?.bufferedReader())
            try {
                lnr.skip(Long.MAX_VALUE)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val linesInFile = lnr.lineNumber
            if (linesInFile > MAX_LINE_IN_CLASS) {
                context.report(ISSUE_CLASS_LINE_EXCEEDED, node,
                        context.getNameLocation(node),
                        "Class ${file?.name} contains $linesInFile line of code, should contain only $MAX_LINE_IN_CLASS line of code.")
            }
        }
    }
}
