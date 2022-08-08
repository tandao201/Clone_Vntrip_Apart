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
import com.tan.login.R

class LocationAdapter(var iClickLocation: IClickLocation): RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private var data: MutableList<Location> = ArrayList()

    fun setData(mLocation: MutableList<Location>){
        this.data = mLocation
        notifyDataSetChanged()
    }

    class LocationViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var icon: ImageView = view.findViewById(R.id.imv_icon)
        var name: TextView = view.findViewById(R.id.tv_location)
        var lv_main: LinearLayout = view.findViewById(R.id.item_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_location,parent,false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        var location = data?.get(position)
        if ( location?.name!!.contains("Khách sạn") || location?.name!!.contains("Hostel") || location?.name!!.contains("Hotel")) {
            holder.icon.setImageResource(R.drawable.ic_location_city)
        } else {
            holder.icon.setImageResource(R.drawable.ic_location_on)
        }
        holder.name.text = location.name
        holder.lv_main.setOnClickListener {
            Log.e("LocationAdapter",location.id.toString())
            iClickLocation.clickedLocationItem(location)
            Log.e("LocationAdapter2",location.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }
}