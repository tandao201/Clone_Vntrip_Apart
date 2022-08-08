package com.tan.login.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tan.login.Adapters.SearchedTicketAdapter
import com.tan.login.Models.FlightTicket.Response.ListFareDatum
import com.tan.login.Models.FlightTicket.Response.ResponseSearchFlight
import com.tan.login.Models.FlightTicket.Resquest.RequestSearchFlight
import com.tan.login.R
import com.tan.login.Repositories.FlightRepo
import kotlinx.android.synthetic.main.fragment_flight_booking.view.*
import kotlinx.android.synthetic.main.fragment_search_place.view.*
import kotlinx.android.synthetic.main.fragment_ticket_searched.view.*
import kotlinx.android.synthetic.main.fragment_ticket_searched.view.tv_noti_not_found
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TicketSearchedFragment : Fragment() {

    private val TAG = "TICKETFRAGMENT"
    private val REQUEST_SEARCH_TICKET = "REQUEST_TICKET"
    private val SAVE_KEY_TIME = "SAVE BUNDLE TIME"
    private val SAVE_KEY_GO = "SAVE BUNDLE_GO"
    private val SAVE_KEY_TO = "SAVE BUNDLE_TO"
    private val SAVE_IS_ROUND_TRIP = "SAVE ROUND TRIP"
    private val SAVE_KEY_TIME_TO = "SAVE BUNDLE TIME TO"

    private var placeGo = ""
    private var placeTo = ""
    private var time = ""
    private var timeTo = ""
    private var isRoundTrip = false


    private val flightRepo = FlightRepo()
    private var responseSearchFlight: ResponseSearchFlight? = null
    private var requestBundle: RequestSearchFlight? = null
    private var ticketAdapter: SearchedTicketAdapter? = null
    private var dataTicket = mutableListOf<ListFareDatum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataBundle()
        ticketAdapter = SearchedTicketAdapter(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_ticket_searched, container, false)
        eventclick(view)
        setUptoRecycler(view)
        setDataBundle(view)
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                view.linear_content.visibility = View.GONE
                view.progress_circular.visibility = View.VISIBLE
            }

            getDataApi()

            withContext(Dispatchers.Main) {
                view.linear_content.visibility = View.VISIBLE
                view.progress_circular.visibility = View.GONE
            }
            if (dataTicket.size == 0){
                withContext(Dispatchers.Main) {
                    view.tv_noti_not_found.visibility = View.VISIBLE
                    view.recycler_ticket_searched.visibility = View.GONE
                }
            }
        }
        return view
    }

    private fun setDataBundle(view: View) {
        view.tv_place_go_to.text = "$placeGo - $placeTo"
        view.tv_dateTime.text = time.split("-")[1].trim()
    }

    private fun eventclick(view: View) {
        view.imv_icon_back?.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(SAVE_KEY_GO,placeGo)
            bundle.putString(SAVE_KEY_TO,placeTo)
            bundle.putString(SAVE_KEY_TIME,time)
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
    }

    private fun setUptoRecycler(view: View) {
        view.recycler_ticket_searched.adapter = ticketAdapter
        view.recycler_ticket_searched.layoutManager = LinearLayoutManager(context)
    }

    private suspend fun getDataApi() {
        try {
            val response = flightRepo.searchFlightTicket(requestBundle!!)
            if (response.isSuccessful) {
                responseSearchFlight = response.body()
                Log.e(TAG, responseSearchFlight?.message!!)
                dataTicket = (responseSearchFlight?.listFareData as MutableList<ListFareDatum>?)!!
                dataTicket.sortBy { it.totalPrice }
                ticketAdapter?.setData(dataTicket)
            } else {
                Log.e(TAG, response.errorBody().toString())
                Log.e(TAG, "Loi!")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Loi!")
        }
    }

    private fun getDataBundle() {
        val bundle = this.arguments
        if (bundle != null) {
            if (bundle.getSerializable(REQUEST_SEARCH_TICKET) != null) {
                requestBundle =
                    bundle.getSerializable(REQUEST_SEARCH_TICKET) as RequestSearchFlight?
            }

            placeGo = bundle.getString(SAVE_KEY_GO).toString()
            placeTo = bundle.getString(SAVE_KEY_TO).toString()
            time = bundle.getString(SAVE_KEY_TIME).toString()
            if (bundle.getBoolean(SAVE_IS_ROUND_TRIP)!= null){
                isRoundTrip = bundle.getBoolean(SAVE_IS_ROUND_TRIP)
                timeTo = bundle.getString(SAVE_KEY_TIME_TO).toString()
            }
        }
    }

}