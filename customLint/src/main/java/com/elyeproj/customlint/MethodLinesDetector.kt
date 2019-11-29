package com.elyeproj.customlint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope.JAVA_FILE
import com.android.tools.lint.detector.api.Scope.TEST_SOURCES
import com.android.tools.lint.detector.api.Severity.WARNING
import org.jetbrains.uast.UMethod
import java.util.*

const val MAX_LINE_IN_METHOD = 40

val ISSUE_METHOD_LINE_EXCEED = Issue.create("MethodLinesExceed",
        "Only $MAX_LINE_IN_METHOD allow in method.",
        "Method should not use more than $MAX_LINE_IN_METHOD lines.",
        CORRECTNESS, PRIORITY, WARNING,
        Implementation(MethodLinesDetector::class.java, EnumSet.of(JAVA_FILE, TEST_SOURCES), EnumSet.of(JAVA_FILE, TEST_SOURCES)))

class MethodLinesDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes() = listOf(UMethod::class.java)

    override fun createUastHandler(context: JavaContext) = WrongTestMethodNameVisitor(context)

    class WrongTestMethodNameVisitor(private val context: JavaContext) : UElementHandler() {
        override fun visitMethod(node: UMethod) {
            val start = context.getLocation(node)?.start?.line
            val end = context.getLocation(node)?.end?.line
            val lineCount = end?.minus(start ?: 0)
            if (lineCount ?: 0 > MAX_LINE_IN_METHOD && !node.name.contains("Activity", true) && !node.name.contains("Fragment", true)) {
                context.report(ISSUE_METHOD_LINE_EXCEED, node,
                        context.getNameLocation(node),
                        "Method ${node.name} contains $lineCount line of code, should contain only $MAX_LINE_IN_METHOD line of code.")

            }
        }
    }
}

