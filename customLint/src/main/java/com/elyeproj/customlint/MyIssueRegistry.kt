package com.elyeproj.customlint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import java.util.*

internal const val PRIORITY = 10 // Does not matter anyways within Lint.

class MyIssueRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(ISSUE_METHOD_LINE_EXCEED,
                ISSUE_CLASS_LINE_EXCEEDED,
                ISSUE_ALERT_DIALOG_USAGE,
                ISSUE_INVALID_IMPORT,
                ISSUE_WRONG_LAYOUT_NAME,
                ISSUE_WRONG_MENU_ID_FORMAT,
                ISSUE_WRONG_TEST_METHOD_NAME,
                ISSUE_WRONG_VIEW_ID_FORMAT,
                ISSUE_NAMING_PATTERN,
                ISSUE_METHOD_COUNT_EXCEEDED
                , ISSUE_ARGUMENTS_EXCEEDED
        )

    override val api: Int = com.android.tools.lint.detector.api.CURRENT_API
}

val ISSUE_NAMING_PATTERN = Issue.create("NamingPattern",
        "Names should be well named.",
        "Some long description about this issue",
        CORRECTNESS,
        5,
        Severity.WARNING,
        Implementation(NamingPatternDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES))
)

