package com.tan.login.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tan.login.Adapters.SearchPlaceFlightAdapter
import com.tan.login.Interfaces.IClickFlight
import com.tan.login.Models.FlightPlace.RegionDatum
import com.tan.login.Models.FlightPlace.ResponseFlight
import com.tan.login.Models.FlightPlace.SelectPeople
import com.tan.login.Models.FlightTicket.Resquest.RequestSearchFlight
import com.tan.login.R
import com.tan.login.Repositories.FlightRepo
import kotlinx.android.synthetic.main.fragment_flight_booking.view.*
import kotlinx.android.synthetic.main.fragment_search_flight.view.*
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.util.regex.Pattern

class SearchFlightFragment : Fragment() {

    private val SAVE_KEY_TIME = "SAVE BUNDLE TIME"
    private val SAVE_KEY_TIME_TO = "SAVE BUNDLE TIME TO"
    private val SAVE_KEY_GO = "SAVE BUNDLE_GO"
    private val SAVE_KEY_TO = "SAVE BUNDLE_TO"
    private val SAVE_IS_ROUND_TRIP = "SAVE ROUND TRIP"
    private val REQUEST_SEARCH_TICKET = "REQUEST_TICKET"

    private var dataFlight: MutableList<Any> = mutableListOf()
    private var responseFlight: ResponseFlight? = null
    private val flightRepo = FlightRepo()
    private var selectGoOrTo = 1

    private val SELECT_PLACE_1 = "PLACE1"
    private val SELECT_PLACE_2 = "PLACE2"
    private val SELECT_KEY_TO_PASS = "SELECT"
    private val SELECT_KEY_PLACE = "PLACE SELECTED"
    private var placeGo: RegionDatum? = null
    private var placeTo: RegionDatum? = null
    private var placeGoString =""
    private var placeToString =""
    private var timeGo = ""
    private var timeTo = ""
    private var isRoundTrip = false
    private var requestSearchFlight: RequestSearchFlight? = null
    private var requestBundle: RequestSearchFlight? = null


    var iClickFlight = object : IClickFlight {
        override fun clickBtnYesSelectPeople(selectPeople: SelectPeople) {

        }

        override fun clickItemPlaceSearchFlight(regionDatum: RegionDatum) {
            clickItemPlace(regionDatum)
        }

    }

    private fun clickItemPlace(regionDatum: RegionDatum) {
        var bundle = Bundle()
        if (selectGoOrTo == 1) {
            placeGo = regionDatum
        } else {
            placeTo = regionDatum
        }
//        if (placeGo != null) {
//            bundle.putSerializable(SELECT_PLACE_1,placeGo)
//        }
//        if (placeTo != null) {
//            bundle.putSerializable(SELECT_PLACE_2,placeTo)
//        }
        bundle.putSerializable(SELECT_PLACE_1, placeGo)
        bundle.putSerializable(SELECT_PLACE_2, placeTo)

        bundle.putString(SAVE_KEY_GO,placeGoString)
        bundle.putString(SAVE_KEY_TO,placeToString)
        bundle.putString(SAVE_KEY_TIME,timeGo)
        bundle.putSerializable(REQUEST_SEARCH_TICKET,requestBundle)
        if (isRoundTrip){
            bundle.putString(SAVE_KEY_TIME_TO,timeTo)
            bundle.putBoolean(SAVE_IS_ROUND_TRIP,isRoundTrip)
        }

        var flightBookingFragment = FlightBookingFragment()
        flightBookingFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
            .replace(R.id.root_container, flightBookingFragment).commit()

        if (view?.edt_search_flight!!.isFocused)
            hideKeyboard()
    }

    private val flightAdapter = SearchPlaceFlightAdapter(iClickFlight)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getDataBundle() {
        var bundle = this.arguments
        if (bundle != null) {
            if (bundle.getInt(SELECT_KEY_TO_PASS) != null) {
                val selectGoOrToBundle = bundle.getInt(SELECT_KEY_TO_PASS)
                selectGoOrTo = selectGoOrToBundle
            }
            if (bundle.getSerializable(SELECT_PLACE_1) != null) {
                placeGo = bundle.getSerializable(SELECT_PLACE_1) as RegionDatum
            }
            if (bundle.getSerializable(SELECT_PLACE_2) != null) {
                placeTo = bundle.getSerializable(SELECT_PLACE_2) as RegionDatum
            }
            if (bundle.getSerializable(REQUEST_SEARCH_TICKET) != null) {
                requestBundle =
                    bundle.getSerializable(REQUEST_SEARCH_TICKET) as RequestSearchFlight?
            }
            requestSearchFlight = bundle.getSerializable(REQUEST_SEARCH_TICKET) as RequestSearchFlight?
            if (bundle.getString(SAVE_KEY_GO)!=null) {
                placeGoString = bundle.getString(SAVE_KEY_GO).toString()
                placeToString = bundle.getString(SAVE_KEY_TO).toString()
                Log.e("Bundle Search",placeToString)
            }

            timeGo = bundle.getString(SAVE_KEY_TIME).toString()
            if (bundle.getBoolean(SAVE_IS_ROUND_TRIP)!= null){
                isRoundTrip = bundle.getBoolean(SAVE_IS_ROUND_TRIP)
                timeTo = bundle.getString(SAVE_KEY_TIME_TO).toString()
            }

        }
    }

    private fun disableData(placeGo: RegionDatum) {
        for (i in 0 until dataFlight.size) {
            if (dataFlight[i] is RegionDatum) {
                var dataTmp = dataFlight[i] as RegionDatum
                if (dataTmp.code == placeGo.code) {
                    dataTmp.isSelected = true
                    dataFlight[i] = dataTmp
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_search_flight, container, false)
        setUpToRecyclerView(view)
        getDataBundle()
        clickEvent(view)
        return view
    }

    private fun setUpToRecyclerView(view: View) {
        lifecycleScope.launch {
            getDataFlightApi()
            addOutstanding()
            addByRegion("other", 0)
            addByRegion("Miền Bắc", 1)
            addByRegion("Miền Trung", 2)
            addByRegion("Miền Nam", 3)

            if (selectGoOrTo == 1) {
                disableData(placeTo!!)
            } else {
                disableData((placeGo!!))
            }

            flightAdapter.setData(dataFlight)
            view.recycler_flight.adapter = flightAdapter
            view.recycler_flight.layoutManager = LinearLayoutManager(context)
        }

    }

    private fun clickEvent(view: View) {
        view.imv_icon_back.setOnClickListener {
            if (view.edt_search_flight.isFocused)
                hideKeyboard()
            var bundle = Bundle()
            bundle.putString(SAVE_KEY_GO,placeGoString)
            bundle.putString(SAVE_KEY_TO,placeToString)
            bundle.putString(SAVE_KEY_TIME,timeGo)
            bundle.putSerializable(REQUEST_SEARCH_TICKET,requestBundle)
            if (isRoundTrip){
                bundle.putString(SAVE_KEY_TIME_TO,timeTo)
                bundle.putBoolean(SAVE_IS_ROUND_TRIP,isRoundTrip)
            }
            var flightBookingFragment = FlightBookingFragment()
            flightBookingFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
                .replace(R.id.root_container, flightBookingFragment).commit()
        }

        view.edt_search_flight.doOnTextChanged { text, start, before, count ->
            var dataTmp: MutableList<Any> = mutableListOf()
            for (i in 0 until dataFlight.size) {
                var dataItem = dataFlight[i]
                if (dataItem is RegionDatum) {
                    var regionData = dataItem as RegionDatum
                    if (removeAccent(regionData.provinceName!!)!!.contains(text.toString(), true)) {
                        Log.e("SearchFlightFragment", removeAccent(regionData.provinceName!!)!!)
                        if (i >= 1) {
                            var title = dataFlight[i - 1]
                            if (title is String)
                                dataTmp.add(title)
                        }
                        dataTmp.add(dataItem)
                    }
                }
            }
            flightAdapter.setData(dataTmp)
        }

        view.recycler_flight.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if (view.edt_search_flight.isFocused)
                    hideKeyboard()
                return false
            }
        })

    }

    private fun addByRegion(title: String, postion: Int) {
        dataFlight.add(title)
        dataFlight.addAll(responseFlight?.data?.domestic?.get(postion)?.regionData!!)
    }

    private fun addOutstanding() {
        dataFlight.add("Nổi bật")
        dataFlight.add(RegionDatum("HAN", "Hà Nội"))
        dataFlight.add(RegionDatum("CXR", "Khánh Hòa"))
        dataFlight.add(RegionDatum("SGN", "TP Hồ Chí Minh"))
        dataFlight.add(RegionDatum("PQC", "Kiên Giang"))
    }

    private suspend fun getDataFlightApi() {
        val response = flightRepo.searchFlight()
        if (response.isSuccessful) {
            responseFlight = response.body()
            Log.e("SearchFlightFragment", responseFlight?.status!!)
        }
    }

    private fun hideKeyboard() {
        val inputManager: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun removeAccent(s: String?): String? {
        val temp: String = Normalizer.normalize(s, Normalizer.Form.NFD)
        val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(temp).replaceAll("")
    }
}