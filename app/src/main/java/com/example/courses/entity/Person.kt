package com.example.courses.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(@PrimaryKey val name: String, val rating: Int)
