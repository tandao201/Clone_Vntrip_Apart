package com.tan.login.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tan.login.FormatString.Currency
import com.tan.login.Models.HotelSearch.HotelSearch
import com.tan.login.R
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.max

class HotelSearchedAdapter(var context: Context): RecyclerView.Adapter<HotelSearchedAdapter.HotelSearchedViewHolder>() {

    private var data: MutableList<HotelSearch> = mutableListOf()
    private var baseUrl = "https://i.vntrip.vn/200x200/smart/https://statics.vntrip.vn/data-v2/hotels/"

    fun setData(mData: MutableList<HotelSearch>) {
        this.data = mData
        notifyDataSetChanged()
    }

    class HotelSearchedViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var imvThumb: ImageView = view.findViewById(R.id.imv_thumb)
        var hotelName: TextView = view.findViewById(R.id.tv_hotel_name_item)
        var ratingBar: RatingBar = view.findViewById(R.id.rtb_hotel_item)
        var hotelAddressItem: TextView = view.findViewById(R.id.tv_hotel_address_item)
        var relaRatingScore: RelativeLayout = view.findViewById(R.id.rela_rating_score)
        var ratingScore: TextView = view.findViewById(R.id.tv_rating_score)
        var subRatingScore: TextView = view.findViewById(R.id.tv_sub_rating_score)
        var relaMobileEndow: RelativeLayout = view.findViewById(R.id.rela_mobile_endow)
        var relaSalePrice: RelativeLayout = view.findViewById(R.id.rela_price_sale)
        var priceSale: TextView = view.findViewById(R.id.tv_price_sale)
        var salePercent: TextView = view.findViewById(R.id.tv_price_sale_per)
        var relaPriceOfficial: RelativeLayout = view.findViewById(R.id.rela_price_official)
        var priceOfficial: TextView = view.findViewById(R.id.tv_price_official)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelSearchedViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel_searched,parent,false)
        return HotelSearchedViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelSearchedViewHolder, position: Int) {
        var hotelItem = data[position]
        holder.hotelName.text = hotelItem.name_vi
        if (hotelItem.star_rate!! < 2){
            holder.ratingBar.visibility = View.GONE
        } else {
            holder.ratingBar.visibility = View.VISIBLE
            holder.ratingBar.numStars = hotelItem.star_rate!!.toInt()
            holder.ratingBar.rating = hotelItem.star_rate!!.toFloat()
        }
        holder.hotelAddressItem.text = hotelItem.full_address
        if (hotelItem.review_point!!.equals(0) || hotelItem.review_point!!.equals(0.0) ){
            holder.relaRatingScore.visibility = View.GONE
        } else {
            holder.relaRatingScore.visibility = View.VISIBLE
            holder.ratingScore.text = hotelItem.review_point.toString()
            holder.subRatingScore.text = getSubRatingString(hotelItem.review_point!!)
        }
        var imgUrl = baseUrl+hotelItem.id+"/img_max/"+hotelItem.thumb_image

        Glide.with(context).load(imgUrl).centerCrop().into(holder.imvThumb)

        // display price
        var pricePerNight = hotelItem.price_one_night
        var incluPrice = hotelItem.show_price?.final_price?.incl_vat_fee_price
        var priceSale = max(pricePerNight!!,incluPrice!!)
        var priceOfficial = 0
        if (pricePerNight == priceSale) priceOfficial = incluPrice
        else priceOfficial = pricePerNight
        var percent: Int = ((priceOfficial.toDouble()/priceSale.toDouble())*100).toInt()
        if (priceOfficial == priceSale ){
            holder.relaSalePrice.visibility = View.GONE
        } else {
            holder.relaSalePrice.visibility = View.VISIBLE
            holder.priceSale.text = Currency.displayVndFormat(priceSale)
            holder.salePercent.text = percent.toString()+"%"
        }
        holder.priceOfficial.text = Currency.displayVndFormat(priceOfficial)

        if (!hotelItem.show_price?.mobile_rate?.show!!){
            holder.relaMobileEndow.visibility = View.GONE
        } else {
            holder.relaMobileEndow.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun getSubRatingString(reviewPoint: Double) : String {
        var result = ""
        if (reviewPoint >= 10 )  result = "Xuất sắc"
        else if (reviewPoint >= 9 ) result = "Tuyệt hảo"
        else if (reviewPoint >= 8 ) result = "Tuyệt vời"
        else if (reviewPoint >= 7)  result = "Tốt"
        else result = "Điểm đánh giá"
        return result
    }

}