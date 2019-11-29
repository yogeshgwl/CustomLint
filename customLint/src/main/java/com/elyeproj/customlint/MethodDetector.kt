package com.elyeproj.customlint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import java.util.*

const val STANDARD_METHOD_COUNT = 20

val ISSUE_METHOD_COUNT_EXCEEDED = Issue.create("MethodCountExceeded",
        "Only $STANDARD_METHOD_COUNT methods allow in a class.",
        "A class should not use more than $STANDARD_METHOD_COUNT methods.",
        Category.CORRECTNESS, 5, Severity.WARNING,
        Implementation(MethodDetector::class.java, EnumSet.of(Scope.JAVA_FILE)))

class MethodDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes() = listOf(UClass::class.java)
    override fun createUastHandler(context: JavaContext) = MethodDetectionHandler(context)

    class MethodDetectionHandler(private val context: JavaContext) :
            UElementHandler() {
        override fun visitClass(node: UClass) {
            val meth =node.psi.methods
            val methodCounts = node.psi.methods.size
            if (methodCounts > STANDARD_METHOD_COUNT) {
                context.report(ISSUE_METHOD_COUNT_EXCEEDED, node,
                        context.getNameLocation(node),
                        "Class contains ${node.psi.name} $methodCounts  methods, Only $STANDARD_METHOD_COUNT methods are allowed in a class.")

            }
        }
    }
}
