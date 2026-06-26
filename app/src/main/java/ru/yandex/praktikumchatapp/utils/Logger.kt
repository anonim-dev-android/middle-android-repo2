package ru.yandex.praktikumchatapp.utils

import android.util.Log

/**
 * Кастомный логгер для возможности использовать логи и все еще успешно проходить тесты
 */
class Logger {

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
    }
}