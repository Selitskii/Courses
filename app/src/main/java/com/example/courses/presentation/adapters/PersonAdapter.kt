package com.example.clearav.presentation.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.R
import com.example.courses.entity.Person
import com.example.courses.presentation.adapters.ItemClickListener
import com.example.courses.presentation.adapters.PersonsCompareCallback

class PersonAdapter internal constructor(

) : ListAdapter<Person,PersonAdapter.ViewHolder>(PersonsCompareCallback()) {
    private var listener: ItemClickListener? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = getItem(position)
        viewHolder.text.text = item.toString()
        viewHolder.itemView.setOnClickListener {
            listener?.onClick(item)
        }

/*
        val item = data[position]
*/
       /* viewHolder.text.text = item.toString()
        viewHolder.itemView.setOnClickListener {
            listener?.onClick(item)
        }*/


    }
    /*data.size*/

    fun setData(data: List<Person>) {

        submitList(data)

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.itemText)
    }


    fun setListener(itemClickListener: ItemClickListener?) {
        listener = itemClickListener
    }
//
//    fun cellChanged(position: Int, value: Int) {
//        data[position] = value
//        notifyDataSetChanged()
//    }
}