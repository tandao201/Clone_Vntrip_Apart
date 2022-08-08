package com.tan.login.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tan.login.Interfaces.IClickLocation
import com.tan.login.Models.Location
import com.tan.login.Models.Suggest.Hotel
import com.tan.login.Models.Suggest.Region
import com.tan.login.R

class SearchLocationAdapter(var iClickLocation: IClickLocation): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: MutableList<Any> = mutableListOf()

    fun setData(mData: MutableList<Any>){
        this.data = mData
        notifyDataSetChanged()
    }

    class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var icon: ImageView = view.findViewById(R.id.imv_icon)
        var name: TextView = view.findViewById(R.id.tv_location)
        var ln_main : LinearLayout = view.findViewById(R.id.item_location)
    }

    class HotelViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var icon: ImageView = view.findViewById(R.id.imv_icon_hotel)
        var name: TextView = view.findViewById(R.id.tv_hotel_name)
        var address: TextView = view.findViewById(R.id.tv_hotel_address)
        var ln_main: LinearLayout = view.findViewById(R.id.ln_hotel_item_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> {
                var view = inflater.inflate(R.layout.item_search_location,parent,false)
                LocationViewHolder(view)
            }
            1 -> {
                var view = inflater.inflate(R.layout.item_search_hotel,parent,false)
                HotelViewHolder(view)
            }
            else -> {
                var view = inflater.inflate(R.layout.item_search_location,parent,false)
                LocationViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var dataItem = data[position]
        when (holder.itemViewType) {
            0 -> {
                var locationHolder = holder as LocationViewHolder
                var dataBinding = dataItem as Region
                locationHolder.icon.setImageResource(R.drawable.ic_location_on)
                locationHolder.name.text = dataBinding.regionnamelongVi
                locationHolder.ln_main.setOnClickListener {
                    var location = Location(dataBinding.regionnameVi!!,dataBinding.regionid!!.toInt())
                    iClickLocation.clickedLocationItem(location)
                    Log.e("SearchAdapter",location.id.toString())
                    Log.e("SearchAdapter2",dataBinding.regionid!!.toString())
                }
            }
            1 -> {
                var hotelViewHolder = holder as HotelViewHolder
                var dataBinding = dataItem as Hotel
                hotelViewHolder.icon.setImageResource(R.drawable.ic_location_city)
                hotelViewHolder.name.text = dataBinding.nameVi
                hotelViewHolder.address.text = dataBinding.address
                holder.ln_main.setOnClickListener {

                    var location = Location(dataBinding.nameVi!!,dataBinding.vntExtData!!.provinceId!!.toInt())
                    iClickLocation.clickedHotelItem(location)
                    Log.e("SearchAdapter",location.id.toString())
                    Log.e("SearchAdapter2",dataBinding.vntExtData!!.provinceId!!.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        var dataItem = data[position]
        if (dataItem is Region) {
            return 0
        } else {
            return 1
        }
    }

}