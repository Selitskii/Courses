package com.example.courses.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.courses.domain.entity.Person

class PersonsCompareCallback : DiffUtil.ItemCallback<Person>() {

    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}