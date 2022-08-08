package com.tan.login.Adapters

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tan.login.Models.FlightTicket.Response.ListFareDatum
import com.tan.login.R
import com.tan.login.FormatString.CurTime
import com.tan.login.FormatString.Currency

class SearchedTicketAdapter(private var context: Context) :
    RecyclerView.Adapter<SearchedTicketAdapter.TicketViewHolder>() {

    //vnairline 1/7
    // other 1/4
    // a1-eco, a1-dlx + 20k
    // a1 -vboss + 60k
    // Eco SkyBoss Deluxe

    private val ECO_CLASS = "Eco"
    private val DELUXE_CLASS = "Deluxe"
    private val SKYBOSS_CLASS = "SkyBoss"

    private val VIETJET = "VJ"
    private val VIETNAMAIRLINE = "VN"
    private val BAMBOO = "QH"

    private var data: MutableList<ListFareDatum> = mutableListOf()

    fun setData(mData: MutableList<ListFareDatum>) {
        this.data = mData
        notifyDataSetChanged()
    }

    class TicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imv_logo_ariline: ImageView = view.findViewById(R.id.imv_logo_ariline)
        var tv_view_detail: TextView = view.findViewById(R.id.tv_view_detail)
        var tv_timeGo: TextView = view.findViewById(R.id.tv_timeGo)
        var tv_timeTo: TextView = view.findViewById(R.id.tv_timeTo)
        var tv_total_TimeFlight: TextView = view.findViewById(R.id.tv_total_TimeFlight)
        var tv_priceTicketFlight: TextView = view.findViewById(R.id.tv_priceTicketFlight)
        var tv_moreInfoTicket: TextView = view.findViewById(R.id.tv_moreInfoTicket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticket_searched, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {

        val dataItem = data.get(position)
        val airline = dataItem.flightItem?.airline
        val dateTimeGoAndTo = CurTime.getHourGoAnTo(
            dataItem.flightItem?.startDate!!,
            dataItem.flightItem.endDate!!
        )
        var price = getFinalPrice(dataItem.totalPrice, dataItem.flightItem.groupClass)
        val moreInfo =
            getMoreInfo(dataItem.flightItem.flightNumber, dataItem.flightItem.hasDownStop)
        val rangeTime = CurTime.getRangeOfTwoHour(dateTimeGoAndTo[0], dateTimeGoAndTo[1])

        when (airline) {
            BAMBOO -> holder.imv_logo_ariline.setImageResource(R.drawable.bamboo)
            VIETJET -> {
                holder.imv_logo_ariline.layoutParams.width = 60.toPx(context)
                holder.imv_logo_ariline.setImageResource(R.drawable.vietjet)
            }
            VIETNAMAIRLINE -> {
                holder.imv_logo_ariline.layoutParams.width = 140.toPx(context)
                holder.imv_logo_ariline.setImageResource(R.drawable.vietnamairlines)
            }
        }
        holder.tv_timeGo.text = dateTimeGoAndTo[0]
        holder.tv_timeTo.text = dateTimeGoAndTo[1]
        holder.tv_total_TimeFlight.text = rangeTime
        holder.tv_priceTicketFlight.text = price?.let { Currency.displayVndFormat(it) }
        holder.tv_moreInfoTicket.text = moreInfo

    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun getFinalPrice(totalPrice: Int?, groupClass: String?): Int? {
        var result = totalPrice
        if (result != null) {
            result += if (groupClass == SKYBOSS_CLASS) 60000
            else 20000
        }
        return result
    }

    private fun getMoreInfo(flightNumber: String?, hasDownStop: Boolean?): String {
        var state = "Bay thẳng"
        val dot = '\u00B7'
        if (hasDownStop == true) {
            state = "Quá cảnh"
        }
        return "$flightNumber $dot $state"
    }

    fun Int.toPx(context: Context) =
        this * context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
}