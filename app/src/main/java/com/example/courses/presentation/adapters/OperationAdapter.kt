package com.example.clearav.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.clearav.domain.UseCase.Operation
import com.example.clearav.domain.UseCase.OperationUseCase
import com.example.clearav.presentation.viewModel.MainViewModel
import com.example.courses.Dependencies
import com.example.courses.R

class OperationAdapter internal constructor(
    private var data: MutableList<Operation>
) : RecyclerView.Adapter<OperationAdapter.ViewHolder>() {
    private val operationUseCase: OperationUseCase by lazy { Dependencies.getOperationUseCase()}
    //private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = data[position]
        viewHolder.text.text=item.toString()
        viewHolder.itemView.setOnClickListener {
            operationUseCase.deleteOperation(position)
           setData(operationUseCase.getOperation())
        // data.removeAt(position)
            //notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = data.size

    fun setData(data: MutableList<Operation>){
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById<TextView>(R.id.itemText)

    }


//    fun setListener(itemClickListener: ItemClickListener?) {
//        listener = itemClickListener
//    }
//
//    fun cellChanged(position: Int, value: Int) {
//        data[position] = value
//        notifyDataSetChanged()
//    }
}