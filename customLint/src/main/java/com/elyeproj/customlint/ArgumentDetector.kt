package com.elyeproj.customlint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import java.util.*

const val STANDARD_PARAMETER_SIZE = 4

val ISSUE_ARGUMENTS_EXCEEDED = Issue.create("ArgumentLengthExceeded",
        "Only $STANDARD_PARAMETER_SIZE arguments allow in method or constructor.",
        "Method or constructor should not use more than $STANDARD_PARAMETER_SIZE parameter.",
        Category.CORRECTNESS, 5, Severity.WARNING,
        Implementation(ArgumentDetector::class.java, EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)))

class ArgumentDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes() = listOf(UClass::class.java)
    override fun createUastHandler(context: JavaContext) = ArgumentHandler(context)

    class ArgumentHandler(private val context: JavaContext) :
            UElementHandler() {
        override fun visitClass(node: UClass) {
            node.methods.forEach {
                if (it.parameters.size > STANDARD_PARAMETER_SIZE) {
                    context.report(ISSUE_ARGUMENTS_EXCEEDED, node,
                            context.getNameLocation(node),
                            "Method ${it.name} contains ${it.parameters.size} arguments, Only $STANDARD_PARAMETER_SIZE arguments in allow in method.")
                }
            }

            node.constructors.forEach {
                if (it.parameters.size > STANDARD_PARAMETER_SIZE) {
                    context.report(ISSUE_ARGUMENTS_EXCEEDED, node,
                            context.getNameLocation(node),
                            "${it.name} contains ${it.parameters.size} arguments, Only $STANDARD_PARAMETER_SIZE arguments in allow in constructor.")
                }
            }
        }
    }
}
