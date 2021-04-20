package com.example.courses.data

import android.content.Context
import android.content.SharedPreferences
import com.example.courses.R
import com.example.courses.UseCase.SharedPreferencesRepository
import com.example.courses.entity.Person

class SharedPreferencesLocalSouse(var context: Context) : SharedPreferencesRepository {
    private val sPref="SPref"
    private val sPrefName="SPref_Name"
    private val sPrefRating="SPref_Rating"
    lateinit var sharedPreferences: SharedPreferences
    override fun save(person: Person) {
        sharedPreferences =
            context.getSharedPreferences(sPref, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(sPrefName, person.name)
        editor.putInt(sPrefRating, person.rating)
        editor.commit()
        editor.apply()
    }

    override fun take(): Person {
        sharedPreferences =
            context.getSharedPreferences(sPref, Context.MODE_PRIVATE)
        return Person(
            sharedPreferences.getString(sPrefName, "").toString(),
            sharedPreferences.getInt(sPrefRating, 0)
        )

    }
}