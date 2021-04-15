package com.example.courses.data

import android.content.Context
import android.content.SharedPreferences
import com.example.courses.UseCase.SharedPreferencesRepository
import com.example.courses.entity.Person

class SharedPreferencesLocalSouse(var context: Context):SharedPreferencesRepository {

    lateinit var sharedPreferences:SharedPreferences
    override fun save(person: Person) {
        sharedPreferences=context.getSharedPreferences("SPref",Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedPreferences.edit()
        editor.putString("SPref_Name",person.name)
        editor.putInt("SPref_Rating",person.rating)
        editor.commit()
        editor.apply()
    }

    override fun take(): Person {
        sharedPreferences=context.getSharedPreferences("SPref",Context.MODE_PRIVATE)
        return Person(
            sharedPreferences.getString("SPref_Name","").toString(),
            sharedPreferences.getInt("SPref_Rating",0))

    }
}