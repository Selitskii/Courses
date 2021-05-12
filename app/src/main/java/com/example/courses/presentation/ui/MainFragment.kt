package com.example.courses.presentation.ui

import android.content.*
import android.os.BatteryManager
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.courses.presentation.services.AddPersonService
import com.example.courses.Const
import com.example.courses.Dependencies
import com.example.courses.presentation.adapter.PersonAdapter
import com.example.courses.presentation.viewmodel.MainViewModel
import com.example.courses.R
import com.example.courses.presentation.adapter.ItemClickListener
import com.example.courses.domain.entity.Person
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()
    private lateinit var name: EditText
    private lateinit var rating: EditText
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var btnAddPerson: Button
    private lateinit var btnFilterName: Button
    private lateinit var btnFilterRating: Button
    private lateinit var fullList: RecyclerView
    private lateinit var filterList: RecyclerView
    private var adapter = PersonAdapter()
    private var adapterFilter = PersonAdapter()
    private val batery = BatteryBroadcastReceiver()
    private val addedPersonBroadcast = PersonAddBroadcastReviever()
    private var personService: AddPersonService? = null
    private var boundToPersonService = false
    private var currentPersonFlag = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            boundToPersonService = true
            personService = (service as AddPersonService.PersonServiceBinder).getService()
            if (currentPersonFlag) {
                personService?.startAddPersonProcess(viewModel.name, viewModel.rating.toInt())
                currentPersonFlag = false
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            boundToPersonService = false
            personService = null
        }

    }

    /* private fun handleAtPersonClick() {
         if (boundToPersonService) {
             personService?.startAddPersonProcess(viewModel.name, viewModel.rating.toInt())
         } else {
             val addPersonServiceIntent =
                 Intent(requireContext(), AddPersonService::class.java).apply {
                     this.putExtra(Const.NAME, viewModel.name)
                     this.putExtra(Const.RATING, viewModel.rating.toInt())
                     currentPersonFlag = true
                 }
             requireActivity().bindService(
                 addPersonServiceIntent,
                 serviceConnection,
                 Context.BIND_AUTO_CREATE
             )
         }
     }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

/*
        val factory = Factory(Dependencies.getPersonUseCase(requireContext()))
*/

/*
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
*/

        name.doAfterTextChanged {
            viewModel.name = it.toString()
        }

        rating.doAfterTextChanged {
            viewModel.rating = it.toString()
        }

        btnAddPerson.setOnClickListener {
            viewModel.addPersonVM()

            /*handleAtPersonClick()*/
        }

        btnFilterName.setOnClickListener {
            viewModel.nameFilter()

        }

        btnFilterRating.setOnClickListener {
            viewModel.ratingFilter()
        }


        viewModel.getPersonsVM().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        viewModel.getPersonsVMFilter().observe(viewLifecycleOwner, {
            adapterFilter.setData(it)
        })

        swipe.setOnRefreshListener {
            viewModel.updatePerosn()
            viewModel.init()
            //adapter.setData(viewModel.getPersonsVM().value!!)
            /*  Intent(context,GetPersonService::class.java).also {
                  JobIntentService.enqueueWork(requireContext(),
                  GetPersonService::class.java,
                  123,
                  it)
              }*/

            swipe.isRefreshing = false
        }

        viewModel.getError().observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), viewModel.getError().toString(), Toast.LENGTH_SHORT)
                .show()
        })


        viewModel.getPersonDataReady().observe(viewLifecycleOwner, {
            if (boundToPersonService) {
                personService?.startAddPersonProcess(it.first, it.second)
            } else {
                val addPersonServiceIntent =
                    Intent(requireContext(), AddPersonService::class.java).apply {
                        this.putExtra(Const.NAME, it.first)
                        this.putExtra(Const.RATING, it.second)
                        currentPersonFlag = true
                    }
                requireActivity().bindService(
                    addPersonServiceIntent,
                    serviceConnection,
                    Context.BIND_AUTO_CREATE
                )
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.edit_Text_First)
        rating = view.findViewById(R.id.edit_Text_Second)
        btnAddPerson = view.findViewById(R.id.addPerson)
        btnFilterName = view.findViewById(R.id.button_filter_name)
        btnFilterRating = view.findViewById(R.id.button_filter_rating)
        filterList = view.findViewById(R.id.filter_list)
        fullList = view.findViewById(R.id.full_list)
        swipe = view.findViewById(R.id.swipe)
        fullList.layoutManager = LinearLayoutManager(requireContext())
        fullList.adapter = adapter
        filterList.layoutManager = LinearLayoutManager(requireContext())
        adapterFilter.setListener(this)
        filterList.adapter = adapterFilter
    }

    override fun onClick(person: Person) {
        viewModel.removePerson(person)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.setListener(null)
        if (personService != null) {
            requireActivity().unbindService(serviceConnection)
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().registerReceiver(batery, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        requireActivity().registerReceiver(
            addedPersonBroadcast,
            IntentFilter(Const.ADD_PERSON_ACTION)
        )

    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(batery)
        requireActivity().unregisterReceiver(addedPersonBroadcast)
    }


    inner class BatteryBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val battaryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            rating.setText("$battaryLevel")
        }
    }

    inner class PersonAddBroadcastReviever : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.updatePerosn()
        }

    }

}