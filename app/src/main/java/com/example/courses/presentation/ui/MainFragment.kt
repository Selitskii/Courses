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
import com.example.clearav.presentation.adapters.PersonAdapter
import com.example.clearav.presentation.viewModel.MainViewModel
import com.example.courses.R
import com.example.courses.entity.Person
import com.example.courses.presentation.adapters.ItemClickListener

class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var inputFirst: EditText
    private lateinit var inputSecond: EditText
    private lateinit var btncalculate: Button
    private lateinit var persons: RecyclerView
    private lateinit var textState: TextView
    private var adapter = PersonAdapter(mutableListOf())
    private lateinit var person: Person
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        person = viewModel.take()
        inputFirst.setText(person.name, TextView.BufferType.EDITABLE)
        inputSecond.setText(person.rating.toString(), TextView.BufferType.EDITABLE)
        inputFirst.doAfterTextChanged {
            viewModel.first = it.toString()
        }
        inputSecond.doAfterTextChanged {
            viewModel.second = it.toString()
        }
        btncalculate.setOnClickListener {
            viewModel.creat()
            viewModel.save()
        }
        viewModel.getPersons().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inputFirst = view.findViewById(R.id.edit_Text_First)
        inputSecond = view.findViewById(R.id.edit_Text_Second)
        btncalculate = view.findViewById(R.id.creat)
        persons = view.findViewById(R.id.persons)
        textState = view.findViewById(R.id.state_text)
        persons.layoutManager = LinearLayoutManager(requireContext())
        persons.adapter = adapter
        adapter.setListener(this)
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        adapter.setListener(null)
        super.onDestroyView()
    }

    override fun onClick(person: Person) {
        viewModel.onOperationSelected(person)
    }
}