package com.elyeproj.democustomlint

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

class MAINActivity : AppCompatActivity() {
    val alertDialog: AlertDialog? = null
    val vfvkjgn: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun add(a: Int, b: Int, c: Int, d: Int, e: Int, f: Int) {
        val sum = a + b + c + d + e
    }

    fun subtract(a: Int, b: Int, c: Int, d: Int, e: Int, f: Int) {
        val sum = a - b - c - d - e
    }

    fun subtract2(a: Int, b: Int, c: Int, d: Int, e: Int, f: Int) {
        val sum = a - b - c - d - e
    }

    fun subtract3(a: Int, b: Int, c: Int, d: Int, e: Int, f: Int) {
        val sum = a - b - c - d - e
    }
}
