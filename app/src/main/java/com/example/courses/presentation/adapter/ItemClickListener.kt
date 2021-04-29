package com.example.courses.presentation.adapter

import com.example.courses.domain.entity.Person

interface ItemClickListener {
    fun onClick(person: Person)
}