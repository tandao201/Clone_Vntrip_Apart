package com.tan.login.Fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tan.login.Adapters.HotelSearchedAdapter
import com.tan.login.Models.HotelSearch.HotelSearch
import com.tan.login.Models.HotelSearch.RequestHotelSearch
import com.tan.login.Models.HotelSearch.ResponseHotelSearch
import com.tan.login.Models.Location
import com.tan.login.R
import com.tan.login.Repositories.HotelSearchRepo
import com.tan.login.FormatString.CurTime
import kotlinx.android.synthetic.main.fragment_hotel_searched.view.*
import kotlinx.coroutines.launch

class HotelSearchedFragment : Fragment() {

    var requestHotelSearch : RequestHotelSearch? = null
    var hotelSearchRepo = HotelSearchRepo()
    var responseHotelSearch: ResponseHotelSearch? = null
    var page=1
    var data:MutableList<HotelSearch> = mutableListOf()
    private lateinit var hotelSearchedAdapter: HotelSearchedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataBundle()
        hotelSearchedAdapter = HotelSearchedAdapter(requireActivity())
        lifecycleScope.launch {
            getDataSearch()
        }
    }

    private suspend fun getDataSearch() {

        var response = hotelSearchRepo.getHotelSearch(requestHotelSearch?.request_source!!,
                                                            requestHotelSearch?.province_id!!,
                                                            requestHotelSearch?.nights!!,
                                                            page,
                                                            requestHotelSearch?.check_in_date!!)

        if ( response.isSuccessful) {
            responseHotelSearch = response.body()
            if (responseHotelSearch?.status == "success"){
                var dataTmp = responseHotelSearch?.data as MutableList<HotelSearch>
                if (dataTmp.size == 0) page--
                data.addAll(dataTmp)
                hotelSearchedAdapter.setData(data)
                Log.e("Hotel Fragment", responseHotelSearch?.status!!)
                Log.e("Hotel Fragment page",page.toString())
//            Log.e("Hotel Fragment1", responseHotelSearch?.data?.get(0)!!.name_vi.toString())
            }
        }
    }

    private fun getDataBundle() {
        var bundle = this.arguments
        if ( bundle != null ){
            requestHotelSearch = bundle.getSerializable("REQUEST_SEARCH") as RequestHotelSearch?
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_hotel_searched, container, false)
        bindingView(view)
        view.recycler_hotel_searched.setOnScrollChangeListener { p0, p1, p2, p3, p4 ->
            if (!p0.canScrollVertically(1)){
                Toast.makeText(requireContext(),"Load more",Toast.LENGTH_SHORT).show()
                page++
                lifecycleScope.launch {
                    getDataSearch()
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClick(view)
    }

    private fun eventClick(view: View) {
        view.imv_icon_back.setOnClickListener {
            var bundle = Bundle()
            var location = Location(requestHotelSearch!!.province_name,requestHotelSearch!!.province_id)
            var checkIn = requestHotelSearch!!.checkInDateFull
            var checkOut = requestHotelSearch!!.checkOutDateFull
            bundle.putString("checkIn",checkIn)
            bundle.putString("checkOut",checkOut)
            bundle.putSerializable("CITY",location)
            var hotelFragment = HotelFragment()
            hotelFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
                .replace(R.id.root_container,hotelFragment).commit()
        }
    }


    private fun bindingView(view: View) {
        view.recycler_hotel_searched.adapter = hotelSearchedAdapter
        view.recycler_hotel_searched.layoutManager = LinearLayoutManager(context)
        var date = "${requestHotelSearch?.nights} đêm (${CurTime.getDayAndMonth(requestHotelSearch!!.checkInDateFull)} - ${CurTime.getDayAndMonth(requestHotelSearch!!.checkOutDateFull)})"
        view.tv_dateTime.text = date
        view.tv_place_hotel_search.text = requestHotelSearch!!.province_name
    }

}