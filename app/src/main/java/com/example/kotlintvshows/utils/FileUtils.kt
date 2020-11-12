package com.example.kotlintvshows.utils

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

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


fun createOrOpenUserDataFile(context: Context): MutableList<Int> {
    val filePath = File(context.filesDir.toString() + "/" + Constants.FILE_NAME)

    return if (!filePath.exists()) {
        //FILE DOES NOT EXIST, CREATE IT
        val newEmptyList: MutableList<Int> = ArrayList()
        writeUserDataFile(context, Gson().toJson(newEmptyList))
        newEmptyList
    } else {
        //FILE EXISTS, READ CONTENT
        Gson().fromJson(readUserDataFile(context),
            object : TypeToken<MutableList<Int>>(){}.type)
    }
}

fun openUserDataFile(context: Context): MutableList<Int> {
    val filePath = File(context.filesDir.toString() + "/" + Constants.FILE_NAME)

    return if (!filePath.exists()) {
        //FILE NOT FOUND. NOTIFY
        val newEmptyList: MutableList<Int> = ArrayList()
        Toast.makeText(context, "ERROR: FILE NOT FOUND", Toast.LENGTH_SHORT).show()
        newEmptyList
    } else {
        //FILE EXISTS, READ CONTENT
        Gson().fromJson(readUserDataFile(context),
            object : TypeToken<MutableList<Int>>(){}.type)
    }
}