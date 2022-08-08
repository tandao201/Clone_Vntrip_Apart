package com.tan.login.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tan.login.Interfaces.IClickFlight
import com.tan.login.Models.FlightPlace.RegionDatum
import com.tan.login.R

class SearchPlaceFlightAdapter(var iClickFlight: IClickFlight): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: MutableList<Any> = mutableListOf()

    fun setData(mData: MutableList<Any>){
        this.data = mData
        notifyDataSetChanged()
    }

    class PlaceViewHoler(view: View) : RecyclerView.ViewHolder(view) {
        var tvPlaceInfo: TextView = view.findViewById(R.id.tv_info_flight)
        var linearMain: LinearLayout = view.findViewById(R.id.ln_item_search_place_flight)
    }

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.findViewById(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> {
                var view = inflater.inflate(R.layout.item_title_flight_search,parent,false)
                TitleViewHolder(view)
            }
            1 -> {
                var view = inflater.inflate(R.layout.item_search_flight,parent,false)
                PlaceViewHoler(view)
            }
            else -> {
                var view = inflater.inflate(R.layout.item_search_flight,parent,false)
                PlaceViewHoler(view)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var dataItem = data[position]
        when (holder.itemViewType) {
            0 -> {
                var titleHolder = holder as TitleViewHolder
                var dataBinding = dataItem as String
                titleHolder.tvTitle.text = dataBinding
            }
            1 -> {
                var placeHolder = holder as PlaceViewHoler
                var dataBinding = dataItem as RegionDatum
                var dot = '\u00B7'
                var place = "${dataBinding.code} $dot ${dataBinding.provinceName}"
                placeHolder.tvPlaceInfo.text = place
                if (dataBinding.isSelected) {
                    holder.tvPlaceInfo.setTextColor(R.color.gray)
                } else {
                    placeHolder.linearMain.setOnClickListener {
                        iClickFlight.clickItemPlaceSearchFlight(dataBinding)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        var dataItem = data[position]
        if (dataItem is String)
            return 0
        return 1
    }
}