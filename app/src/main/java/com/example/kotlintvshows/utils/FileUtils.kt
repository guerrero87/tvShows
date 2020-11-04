package com.example.kotlintvshows.utils

import android.content.Context

fun readUserDataFile(context: Context): String {
    context.openFileInput(Constants.fileName).use { stream ->
        return stream.bufferedReader().use {
            it.readText()
        }
    }
}

fun writeUserDataFile(context: Context, jsonStringToWrite: String) {
    context.openFileOutput(Constants.fileName, Context.MODE_PRIVATE).use { output ->
        output.write(jsonStringToWrite.toByteArray())
    }
}
