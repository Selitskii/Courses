package com.example.courses.data

import android.content.Context
import android.content.SharedPreferences
import com.example.courses.R
import com.example.courses.UseCase.SharedPreferencesRepository
import com.example.courses.entity.Person

class SharedPreferencesLocalSouse(var context: Context) : SharedPreferencesRepository {

    lateinit var sharedPreferences: SharedPreferences
    override fun save(person: Person) {
        sharedPreferences =
            context.getSharedPreferences(R.string.SPref.toString(), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(R.string.SPref_Name.toString(), person.name)
        editor.putInt(R.string.SPref_Rating.toString(), person.rating)
        editor.commit()
        editor.apply()
    }

    override fun take(): Person {
        sharedPreferences =
            context.getSharedPreferences(R.string.SPref.toString(), Context.MODE_PRIVATE)
        return Person(
            sharedPreferences.getString(R.string.SPref_Name.toString(), "").toString(),
            sharedPreferences.getInt(R.string.SPref_Rating.toString(), 0)
        )

    }
}