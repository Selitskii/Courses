package com.example.courses.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.R
import com.example.courses.domain.entity.Person

class PersonAdapter (
    private var data: List<Person>
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = data[position]
        viewHolder.text.text = item.toString()
        viewHolder.itemView.setOnClickListener {
            listener?.onClick(item)
        }
    }

    override fun getItemCount() = data.size

    fun setData(data: List<Person>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setListener(itemClickListener: ItemClickListener?) {
        listener = itemClickListener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.itemText)
    }

}