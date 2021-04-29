package com.example.courses.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.R
import com.example.courses.domain.entity.Person

class PersonAdapter(

) : ListAdapter<Person, PersonAdapter.ViewHolder>(PersonsCompareCallback()) {

    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = getItem(position)
        viewHolder.text.text = getItem(position).toString()
        viewHolder.itemView.setOnClickListener {
            listener?.onClick(item)

        }

    }

    fun setData(data: List<Person>) {
        submitList(data)
    }

    fun setListener(itemClickListener: ItemClickListener?) {
        listener = itemClickListener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.itemText)
    }

}