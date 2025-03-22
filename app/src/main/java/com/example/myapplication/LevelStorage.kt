package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.myapplication.models.Element
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


const val  KEY_LEVEL = "key_level"

class LevelStorage(val context: Context) {
    private val prefs = (context as Activity).getPreferences(MODE_PRIVATE)
    private val gson = Gson()

    fun saveLevel(elementsonContainer: List<Element>){
        prefs.edit()
            .putString(KEY_LEVEL,gson.toJson(elementsonContainer))
            .apply()
    }

    fun loadLevel():List<Element>?{
        val levelFromPrefs = prefs.getString(KEY_LEVEL, null) ?: return null
            val type = object : TypeToken<List<Element>>() {}.type
            return Gson().fromJson(levelFromPrefs,type)

    }
}
