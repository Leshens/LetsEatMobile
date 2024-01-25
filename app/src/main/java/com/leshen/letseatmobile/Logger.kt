package com.leshen.letseatmobile

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable?)
}