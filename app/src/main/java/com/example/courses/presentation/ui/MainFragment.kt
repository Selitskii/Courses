package com.example.clearav.presentation.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clearav.domain.UseCase.Operation
import com.example.clearav.presentation.adapters.OperationAdapter
import com.example.clearav.presentation.viewModel.MainViewModel
import com.example.courses.R
import com.example.courses.presentation.adapters.ItemClickListener
import com.example.courses.presentation.viewModel.CalculationState

class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var inputFirst: EditText
    private lateinit var inputSecond: EditText
    private lateinit var btncalculate: Button
    private lateinit var operation: RecyclerView
    private lateinit var textState: TextView
    private var adapter = OperationAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        inputFirst.doAfterTextChanged {
            viewModel.first = it.toString()
        }
        inputSecond.doAfterTextChanged {
            viewModel.second = it.toString()
        }
        btncalculate.setOnClickListener {
            val toast = Toast.makeText(requireContext(), "${viewModel.calculate()}", Toast.LENGTH_SHORT)
            toast.show()
        }
        viewModel.getOperations().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        viewModel.calculationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                CalculationState.Free -> btncalculate.isEnabled = true
                CalculationState.Loading -> btncalculate.isEnabled = false
                CalculationState.Result -> btncalculate.isEnabled = false
            }

            textState.text = getString(when (it) {
                CalculationState.Free -> R.string.free
                CalculationState.Loading -> R.string.loading
                CalculationState.Result -> R.string.result
            })
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputFirst = view.findViewById(R.id.edit_Text_First)
        inputSecond = view.findViewById(R.id.edit_Text_Second)
        btncalculate = view.findViewById(R.id.calculate)
        operation = view.findViewById(R.id.operation)
        textState = view.findViewById(R.id.state_text)
        operation.layoutManager = LinearLayoutManager(requireContext())
        operation.adapter = adapter
        adapter.setListener(this)
    }

    override fun onClick(operation: Operation) {
        viewModel.onOperationSelected(operation)
    }

    override fun onDestroyView() {
        adapter.setListener(null)
        super.onDestroyView()
    }
}