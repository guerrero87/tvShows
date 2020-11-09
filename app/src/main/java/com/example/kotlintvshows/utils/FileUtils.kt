package com.example.kotlintvshows.utils

import android.content.Context

fun readUserDataFile(context: Context): String {
    context.openFileInput(Constants.FILE_NAME).use { stream ->
        return stream.bufferedReader().use {
            it.readText()
        }
    }
}

fun writeUserDataFile(context: Context, jsonStringToWrite: String) {
    context.openFileOutput(Constants.FILE_NAME, Context.MODE_PRIVATE).use { output ->
        output.write(jsonStringToWrite.toByteArray())
    }
}