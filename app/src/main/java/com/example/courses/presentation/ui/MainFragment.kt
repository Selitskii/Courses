package com.example.courses.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.courses.presentation.adapter.PersonAdapter
import com.example.courses.presentation.viewmodel.MainViewModel
import com.example.courses.R
import com.example.courses.presentation.adapter.ItemClickListener
import com.example.courses.domain.entity.Person
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var inputLogin: EditText
    private lateinit var inputPassword: EditText
    private lateinit var swipe:SwipeRefreshLayout
    private lateinit var btnAddPerson: Button
    private lateinit var btnFilterName: Button
    private lateinit var btnFilterRating: Button
    private lateinit var fullList: RecyclerView
    private lateinit var filterList: RecyclerView
    private var adapter = PersonAdapter()
    private var adapterFilter = PersonAdapter()
    private val compositeDisposable = CompositeDisposable()
    private val batery = BatteryBroadcastReceiver()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        inputLogin.doAfterTextChanged {
            viewModel.first = it.toString()
        }

        inputPassword.doAfterTextChanged {
            viewModel.second = it.toString()
        }

        val observableNameFilter = Observable.create<Unit> { subscriber ->
            btnFilterName.setOnClickListener {
                subscriber.onNext(Unit)
            }
        }

        val subscribeNameFilter = observableNameFilter
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.nameFilter()
            }

        compositeDisposable.add(subscribeNameFilter)

        val observableRatingFIlter = Observable.create<Unit> { subscriber ->
            btnFilterRating.setOnClickListener {
                subscriber.onNext(Unit)
            }
        }

        val subscriberRatingFilter = observableRatingFIlter
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.ratingFilter()
            }

        compositeDisposable.add(subscriberRatingFilter)

        val observable = Observable.create<Unit> { subscriber ->
            btnAddPerson.setOnClickListener {
                subscriber.onNext(Unit)
            }
        }

        val subscribe = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.addPersonVM()
            }
        compositeDisposable.add(subscribe)

        viewModel.getPersonsVM().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        viewModel.getPersonsVMFilter().observe(viewLifecycleOwner, {
            adapterFilter.setData(it)
        })



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputLogin = view.findViewById(R.id.edit_Text_First)
        inputPassword = view.findViewById(R.id.edit_Text_Second)
        btnAddPerson = view.findViewById(R.id.addPerson)
        btnFilterName = view.findViewById(R.id.button_filter_name)
        btnFilterRating = view.findViewById(R.id.button_filter_rating)
        filterList = view.findViewById(R.id.filter_list)
        fullList = view.findViewById(R.id.full_list)
        swipe = view.findViewById(R.id.swipe)
        fullList.layoutManager = LinearLayoutManager(requireContext())
        fullList.adapter = adapter
        adapter.setListener(this)
        filterList.layoutManager = LinearLayoutManager(requireContext())
        filterList.adapter = adapterFilter
    }

    override fun onClick(person: Person) {
        viewModel.removePerson(person)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setListener(null)
        viewModel.destroy()
        compositeDisposable.dispose()
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(batery)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(batery, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    inner class BatteryBroadcastReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val battaryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL,-1)?:-1
            inputPassword.setText("$battaryLevel")
        }

    }

}