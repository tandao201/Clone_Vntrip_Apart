package com.tan.login.Fragments

import android.annotation.SuppressLint
import com.tan.login.Models.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tan.login.Adapters.LocationAdapter
import com.tan.login.Adapters.SearchLocationAdapter
import com.tan.login.DB.LocationDB
import com.tan.login.Interfaces.IClickLocation
import com.tan.login.Models.HotelSearch.RequestHotelSearch
import com.tan.login.Models.Login.ResponseLogin
import com.tan.login.Models.Suggest.DataSuggest
import com.tan.login.Models.Suggest.Hotel
import com.tan.login.Models.Suggest.Region
import com.tan.login.R
import com.tan.login.Repositories.LocationRepo
import com.tan.login.Repositories.SuggestRepo
import kotlinx.android.synthetic.main.fragment_hotel.view.*
import kotlinx.android.synthetic.main.fragment_search_place.*
import kotlinx.android.synthetic.main.fragment_search_place.view.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPlaceFragment : Fragment() {

    companion object {
        private var data: MutableSet<Location> = hashSetOf()
        var dataSearch = mutableListOf<Any>()
    }

    var requestHotelSearch : RequestHotelSearch? = null

    lateinit var locationRepo: LocationRepo
    var suggestRepo = SuggestRepo()
    var dataSuggest: DataSuggest? = null
    var iClickLocation = object : IClickLocation {

        override fun clickedLocationItem(location: Location) {
            clickLocationItemRecycler(location)
        }

        override fun clickedHotelItem(location: Location) {
            clickedHotelItemRecycler(location)
        }
    }

    var searchLocationAdapter = SearchLocationAdapter(iClickLocation)
    var locationAdapter = LocationAdapter(iClickLocation)

    private fun clickLocationItemRecycler(location: Location) {
        clickOnHotPlace(location)
    }
    private fun clickedHotelItemRecycler(location: Location) {
        sv_place.clearFocus()
        data.add(location)

        lifecycleScope.launch {
            locationRepo.addNewLocation(location)
        }
        if (data.size > 5 ){
            data.remove(data.elementAt(0))
        }
        locationAdapter.setData(data.toMutableList())
        Toast.makeText(requireContext(),"Clicked "+location.name,Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_place, container, false)
        eventListener(view)
        setUpToRecycler(view)
        getDataBundle()
        return view
    }

    private fun setUpToRecycler(view: View?) {
        locationRepo = LocationRepo(LocationDB.getDatabase(context).locationDao())
        lifecycleScope.launch {
            var dataDB = locationRepo.get5LastLocation()
            data.addAll(dataDB)
            if (data.size > 5 ){
                data.remove(data.elementAt(0))
            }
            locationAdapter.setData(data.toMutableList())
        }
        view?.recycler_recent?.adapter = locationAdapter
        view?.recycler_recent?.layoutManager = LinearLayoutManager(context)
    }

    private fun getDataBundle() {
        var bundle = this.arguments
        if ( bundle != null ){
            requestHotelSearch = bundle.getSerializable("REQUEST_SEARCH") as RequestHotelSearch?
        }
    }

    private fun eventListener(view: View) {
        view.rela_main.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                sv_place.clearFocus()
                return false
            }
        })

        view.tv_cancel.setOnClickListener {
            sv_place.clearFocus()
            var hotelFragment = HotelFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.root_container,hotelFragment).commit()
        }

        view.ln_recent_place.setOnClickListener {
            sv_place.clearFocus()
            var hotelFragment = HotelFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.root_container,hotelFragment).commit()
        }

        view.ln_hot_place_hanoi.setOnClickListener {
            var location = Location("Hà Nội",66)
            clickOnHotPlace(location)
        }
        view.ln_hot_place_hcm.setOnClickListener {
            var location = Location("TP Hồ Chí Minh",67)
            clickOnHotPlace(location)
        }
        view.ln_hot_place_danang.setOnClickListener {
            var location = Location("Đà Nẵng",68)
            clickOnHotPlace(location)
        }

        view.sv_place.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                sv_place.clearFocus()
                return true
            }

            @SuppressLint("ClickableViewAccessibility")
            override fun onQueryTextChange(p0: String?): Boolean {
                lifecycleScope.launch {
                    getData(p0.toString())
                    setUpToRecyclerSearch(view)
                    if (dataSearch.size > 0){
                        view.recycler_searched.visibility = View.VISIBLE
                        view.tv_noti_not_found.visibility = View.GONE
                        searchLocationAdapter.setData(dataSearch)
                        view.recycler_searched.adapter = searchLocationAdapter
                        view.recycler_searched.layoutManager = LinearLayoutManager(context)
                    } else {
                        view.tv_noti_not_found.visibility = View.VISIBLE
                        view.recycler_searched.visibility = View.GONE
                    }
                    view.ln_default.visibility = View.GONE
                    view.rela_searched.visibility = View.VISIBLE
                    if (p0!!.isEmpty()){
                        view.ln_default.visibility = View.VISIBLE
                        view.rela_searched.visibility = View.GONE
                    }
                }
                view.recycler_searched.setOnTouchListener(object : View.OnTouchListener{
                    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                        sv_place.clearFocus()
                        return false
                    }
                } )
                return false
            }

        })
    }

    private fun setUpToRecyclerSearch(view: View) {
        try {
            dataSearch.clear()
            var regions = dataSuggest?.data?.regions!!
            var hotels = dataSuggest?.data?.hotels!!

            for (i in 0..9){
                regions[i]?.let { dataSearch.add(it) }
            }
            for (i in 0..9){
                hotels[i]?.let { dataSearch.add(it) }
            }

        } catch (e: Exception) {
            Log.e("Error SearchFragment",e.toString())
        }
    }

    private fun clickOnHotPlace(location: Location) {
        sv_place.clearFocus()
        var bundle = Bundle()
        bundle.putSerializable("CITY",location)
        requestHotelSearch?.province_id = location.regionId
        requestHotelSearch?.province_name = location.name
        EventBus.getDefault().postSticky(requestHotelSearch)
        data.add(location)
        lifecycleScope.launch {
            locationRepo.addNewLocation(location)
        }
        if (data.size > 5 ){
            data.remove(data.elementAt(0))
        }
        locationAdapter.setData(data.toMutableList())
        var hotelFragment = HotelFragment()
        hotelFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.root_container,hotelFragment).commit()
    }

    private suspend fun getData(search: String){
        Log.e("Search",search)
        var response = suggestRepo.getSuggest(search.trim())
        if (response.isSuccessful){
            dataSuggest = response.body()
        }
//        call.enqueue(object : Callback<DataSuggest> {
//            override fun onResponse(call: Call<DataSuggest>, response: Response<DataSuggest>) {
//                dataSuggest = response.body()
//                if (dataSuggest?.data != null){
//                    if (dataSuggest?.data?.regions?.size!! > 0 ){
//                        Log.e("Suggest", dataSuggest?.status.toString())
//                        Log.e("City Suggest", dataSuggest?.data?.regions?.get(0)!!.regionnamelongVi.toString())
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<DataSuggest>, t: Throwable) {
//
//            }
//
//        })
    }
}