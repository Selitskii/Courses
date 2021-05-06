package com.example.courses.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.BatteryManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.JobIntentService
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


class MainFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
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

    private lateinit var locationManager:LocationManager
    private  lateinit var sensorManager: SensorManager
    private var sensor:Sensor? = null
    private val acselerometorListener:SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event == null) return
            name.setText("${event?.values[0]}|||${event?.values[1]}|||${event?.values[2]}")
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }
    private val locationListener:LocationListener= object : LocationListener {
        override fun onLocationChanged(location: Location) {
            rating.setText("${location.latitude.toInt()*100+location.longitude.toInt()}")
            Log.d("location","${location.latitude.toInt()*100+location.longitude.toInt()}")
        }

        @SuppressLint("MissingPermission")
        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
            val location= locationManager.getLastKnownLocation(provider)
        }
    }


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

        val factory = Factory(Dependencies.getPersonUseCase(requireContext()))

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

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

        sensorManager=requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensors.forEach { sensor ->
            val sensorInformation =
                "name = ${sensor.name}, type = ${sensor.type}\nvendor = ${sensor.vendor}" +
                        " ,version = ${sensor.version}\nmax = ${sensor.maximumRange} , power = ${sensor.power}" +
                        ", resolution = ${sensor.resolution}\n--------------------------------------\n"
            Log.d("Sensor", sensorInformation)
        }
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        locationManager=requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

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
        sensorManager.registerListener(acselerometorListener,sensor,SensorManager.SENSOR_DELAY_NORMAL)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,10F,locationListener)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000L,10F,locationListener)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(batery)
        requireActivity().unregisterReceiver(addedPersonBroadcast)
        sensorManager.unregisterListener(acselerometorListener)
        locationManager.removeUpdates(locationListener)
    }


    inner class BatteryBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val battaryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
           // rating.setText("$battaryLevel")
        }
    }

    inner class PersonAddBroadcastReviever : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.updatePerosn()
        }

    }

}