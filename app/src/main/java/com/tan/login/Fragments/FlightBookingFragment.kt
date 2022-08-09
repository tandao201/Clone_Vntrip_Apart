package com.tan.login.Fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.core.util.Pair
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.tan.login.DiaLogs.SelectPeoPleDialog
import com.tan.login.Interfaces.IClickFlight
import com.tan.login.Models.FlightPlace.RegionDatum
import com.tan.login.Models.FlightPlace.SelectPeople
import com.tan.login.Models.FlightTicket.Resquest.ListFlight
import com.tan.login.Models.FlightTicket.Resquest.RequestSearchFlight
import com.tan.login.R
import com.tan.login.FormatString.CurTime
import kotlinx.android.synthetic.main.fragment_flight_booking.*
import kotlinx.android.synthetic.main.fragment_flight_booking.view.*
import kotlinx.android.synthetic.main.fragment_flight_booking.view.im_menu_header
import kotlinx.android.synthetic.main.fragment_flight_booking.view.ln_check_out
import kotlinx.android.synthetic.main.fragment_flight_booking.view.tv_check_in
import kotlinx.android.synthetic.main.fragment_flight_booking.view.tv_check_out
import kotlinx.android.synthetic.main.fragment_hotel.tv_check_in
import kotlinx.android.synthetic.main.fragment_hotel.tv_check_out
import kotlinx.android.synthetic.main.fragment_hotel.view.*
import java.time.LocalDate
import java.util.*

class FlightBookingFragment : Fragment() {

    private val SELECT_PLACE_1 = "PLACE1"
    private val SELECT_PLACE_2 = "PLACE2"
    private val SELECT_KEY_TO_PASS = "SELECT"
    private val SELECT_KEY_PLACE = "PLACE SELECTED"
    private val REQUEST_SEARCH_TICKET = "REQUEST_TICKET"
    private val TAG = "FlightBookingFragment"
    private val SAVE_KEY_TIME = "SAVE BUNDLE TIME"
    private val SAVE_KEY_TIME_TO = "SAVE BUNDLE TIME TO"
    private val SAVE_KEY_GO = "SAVE BUNDLE_GO"
    private val SAVE_KEY_TO = "SAVE BUNDLE_TO"
    private val SAVE_IS_ROUND_TRIP = "SAVE ROUND TRIP"

    private val MINISECOND_IN_DAY: Long = 86400000
    private var MINISECOND_30_DAYS: Long = MINISECOND_IN_DAY * 30

    private var isRoundTrip = false
    private var selectPeopleMain: SelectPeople? = null
    private var selectGoOrTo = 0
    private var requestSearchFlight: RequestSearchFlight? = null
    private var placeGo = ""
    private var placeTo = ""
    private var timeGo = ""
    private var timeTo = ""
    private var regionDatum1: RegionDatum? = null
    private var regionDatum2: RegionDatum? = null

    var iClickFlight = object : IClickFlight {
        override fun clickBtnYesSelectPeople(selectPeople: SelectPeople) {
            clickBtnYes(selectPeople)
        }

        override fun clickItemPlaceSearchFlight(regionDatum: RegionDatum) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flight_booking, container, false)

        initTime(view)
        getDataBundle(view)
        eventClick(view)
        return view
    }

    private fun getDataBundle(view: View) {
        var bundle = this.arguments
        if (bundle != null) {
            if (bundle.getSerializable(REQUEST_SEARCH_TICKET)!=null){
                requestSearchFlight = bundle.getSerializable(REQUEST_SEARCH_TICKET) as RequestSearchFlight?
                if (bundle.getString(SAVE_KEY_GO)!=null) {
                    placeGo = bundle.getString(SAVE_KEY_GO).toString()
                    placeTo = bundle.getString(SAVE_KEY_TO).toString()
                }
                timeGo = bundle.getString(SAVE_KEY_TIME).toString()
                if (bundle.getBoolean(SAVE_IS_ROUND_TRIP)!= null){
                    isRoundTrip = bundle.getBoolean(SAVE_IS_ROUND_TRIP)
                    timeTo = bundle.getString(SAVE_KEY_TIME_TO).toString()
                }

                Log.e("Booking fragment bundle",placeTo)
                bindingView(view)
            }
        }
    }

    private fun bindingView(view: View) {
        if (regionDatum1==null) {
            view.tv_flight_code_go.text = requestSearchFlight!!.listFlight[0].startPoint
            view.tv_flight_place_go.text = placeGo
            view.tv_flight_code_to.text = requestSearchFlight!!.listFlight[0].endPoint
            view.tv_flight_place_to.text = placeTo
        }
        view.tv_check_in.text = timeGo
        if (isRoundTrip){
            Log.e("set view" ,timeTo)
            view.ln_check_out.visibility = View.VISIBLE
            view.tv_check_out.text = timeTo
            view.swich_round_trip.isChecked = isRoundTrip
        } else {
            view.swich_round_trip.isChecked = isRoundTrip
            view.ln_check_out.visibility = View.GONE
        }
        Log.e("Booking fragment bundle",timeGo)
        Log.e("Booking fragment bundle",requestSearchFlight!!.adultCount.toString())
        Log.e("Booking fragment bundle",requestSearchFlight!!.childCount.toString())
        var select = SelectPeople(requestSearchFlight!!.adultCount,requestSearchFlight!!.childCount,requestSearchFlight!!.infantCount)
        view.tv_passenger.text = getTextSelectPeople(select)
        selectPeopleMain = select
    }

    private fun clickBtnYes(selectPeople: SelectPeople) {
        val select = getTextSelectPeople(selectPeople)
        selectPeopleMain = selectPeople
        tv_passenger?.text = select
    }

    private fun getTextSelectPeople(selectPeople: SelectPeople): String {
        var adult = "${selectPeople.adult} người lớn"
        var child = " - ${selectPeople.child} trẻ em"
        var baby = " - ${selectPeople.baby} em bé"
        var select = adult
        if (selectPeople.child != 0) select += child
        if (selectPeople.baby != 0) select += baby
        return select
    }

    private fun initTime(view: View) {

        var bundle = this.arguments
        if (bundle != null) {
            if (bundle.getSerializable(SELECT_PLACE_1) != null) {
                regionDatum1 = bundle.getSerializable(SELECT_PLACE_1) as RegionDatum
                view.tv_flight_code_go.text = regionDatum1?.code
                view.tv_flight_place_go.text = regionDatum1?.provinceName
            }
            if (bundle.getSerializable(SELECT_PLACE_2) != null) {
                regionDatum2 = bundle.getSerializable(SELECT_PLACE_2) as RegionDatum
                view.tv_flight_code_to.text = regionDatum2?.code
                view.tv_flight_place_to.text = regionDatum2?.provinceName
            }

        }

        var today = LocalDate.now()
        var curDay = today.dayOfWeek.name
        var curDate = LocalDate.now()
        view.tv_check_in.text = CurTime.getDateTime(curDay, curDate)
        if (isRoundTrip) {
            view.tv_check_out.text =
                CurTime.getDateTime(LocalDate.now().plusDays(1).dayOfWeek.name, curDate.plusDays(1))
        }

        selectPeopleMain = SelectPeople(1, 0, 0)
    }

    private fun eventClick(view: View) {
        view.im_menu_header?.setOnClickListener {
            var homeFragment: HomeFragment = HomeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
                .replace(R.id.root_container, homeFragment).commit()
        }

        view.swich_round_trip.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                view.ln_check_out.visibility = View.VISIBLE
                isRoundTrip = true
                var dateTimeCheckIn =
                    CurTime.getTimeFromString(tv_check_in.text.toString()).time + MINISECOND_IN_DAY

                view.tv_check_out.text = CurTime.getDateTimeString(
                    CurTime.convertLongtoDayofWeek(dateTimeCheckIn),
                    CurTime.convertLongToTime(dateTimeCheckIn)
                )

                view.ln_check_out.setOnClickListener {
                    pickDatePair()
                }
                view.rela_check_in.setOnClickListener {
                    pickDatePair()
                }

                view.btn_search_ticket.setOnClickListener {
                    var listFlight: List<ListFlight> = getListFlightSearch(view)
                    var requestSearchFlight = RequestSearchFlight(
                        selectPeopleMain!!.adult,
                        selectPeopleMain!!.child,
                        selectPeopleMain!!.baby,
                        listFlight
                    )
                    var bundle = Bundle()
                    bundle.putSerializable(REQUEST_SEARCH_TICKET,requestSearchFlight)
                    bundle.putString(SAVE_KEY_GO,view.tv_flight_place_go.text?.toString())
                    bundle.putString(SAVE_KEY_TO,view.tv_flight_place_to.text?.toString())
                    bundle.putString(SAVE_KEY_TIME,view.tv_check_in.text?.toString())
                    if (view.swich_round_trip.isChecked){
                        isRoundTrip = view.swich_round_trip.isChecked
                        bundle.putString(SAVE_KEY_TIME_TO,view.tv_check_out.text?.toString())
                        bundle.putBoolean(SAVE_IS_ROUND_TRIP,view.swich_round_trip.isChecked)
                    }

                    var ticketSearchedFragment = TicketSearchedFragment()
                    ticketSearchedFragment.arguments = bundle
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
                        .replace(R.id.root_container,ticketSearchedFragment)
                        .addToBackStack(null).commit()
                }
            } else {
                view.ln_check_out.visibility = View.GONE
                isRoundTrip = false
            }
        }

        view.rela_check_in.setOnClickListener {
            pickDate()
        }

        if (isRoundTrip) {
            view.rela_check_in.setOnClickListener {
                pickDatePair()
            }

            view.ln_check_out.setOnClickListener {
                pickDatePair()
            }
        }

        view.ln_passenger.setOnClickListener {
            var dialog = SelectPeoPleDialog(iClickFlight)
            selectPeopleMain?.let { it1 -> dialog.setSelectPeople(it1) }

            dialog.isCancelable = true
            dialog.show(requireActivity().supportFragmentManager, "Select People")
        }

        view.imv_icon_change.setOnClickListener {
            swapPlaceGoAndTo(view)
        }

        view.ln_search_place_go.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt(SELECT_KEY_TO_PASS, 1)
            if (view.tv_flight_code_go.text.isNotEmpty()) {
                bundle.putSerializable(
                    SELECT_PLACE_1, RegionDatum(
                        view.tv_flight_code_go.text.toString(),
                        view.tv_flight_place_go.text.toString()
                    )
                )
                Log.e("BookingFragment Go", view.tv_flight_code_go.text.toString())
            }
            if (view.tv_flight_code_to.text.isNotEmpty()) {
                bundle.putSerializable(
                    SELECT_PLACE_2, RegionDatum(
                        view.tv_flight_code_to.text.toString(),
                        view.tv_flight_place_to.text.toString()
                    )
                )
            }
            var listFlight: List<ListFlight> = getListFlightSearch(view)
            var requestTmp  = RequestSearchFlight(
                selectPeopleMain!!.adult,
                selectPeopleMain!!.child,
                selectPeopleMain!!.baby,
                listFlight
            )
            bundle.putSerializable(REQUEST_SEARCH_TICKET,requestTmp)
            bundle.putString(SAVE_KEY_GO,view.tv_flight_place_go.text?.toString())
            bundle.putString(SAVE_KEY_TO,view.tv_flight_place_to.text?.toString())
            bundle.putString(SAVE_KEY_TIME,view.tv_check_in.text?.toString())
            if (view.swich_round_trip.isChecked){
                isRoundTrip = view.swich_round_trip.isChecked
                bundle.putString(SAVE_KEY_TIME_TO,view.tv_check_out.text?.toString())
                bundle.putBoolean(SAVE_IS_ROUND_TRIP,view.swich_round_trip.isChecked)
            }

            var searchFlightFragment = SearchFlightFragment()

            searchFlightFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
                .replace(R.id.root_container, searchFlightFragment)
                .addToBackStack(null).commit()
        }

        view.ln_search_place_to.setOnClickListener {

            var bundle = Bundle()
            bundle.putInt(SELECT_KEY_TO_PASS, 2)
            if (view.tv_flight_code_to.text.isNotEmpty()) {
                bundle.putSerializable(
                    SELECT_PLACE_2, RegionDatum(
                        view.tv_flight_code_to.text.toString(),
                        view.tv_flight_place_to.text.toString()
                    )
                )
            }

            if (view.tv_flight_code_go.text.isNotEmpty()) {
                bundle.putSerializable(
                    SELECT_PLACE_1, RegionDatum(
                        view.tv_flight_code_go.text.toString(),
                        view.tv_flight_place_go.text.toString()
                    )
                )
                Log.e("BookingFragment Go", view.tv_flight_code_go.text.toString())
            }
            var listFlight: List<ListFlight> = getListFlightSearch(view)
            var requestTmp  = RequestSearchFlight(
                selectPeopleMain!!.adult,
                selectPeopleMain!!.child,
                selectPeopleMain!!.baby,
                listFlight
            )
            bundle.putSerializable(REQUEST_SEARCH_TICKET,requestTmp)
            bundle.putString(SAVE_KEY_GO,view.tv_flight_place_go.text?.toString())
            bundle.putString(SAVE_KEY_TO,view.tv_flight_place_to.text?.toString())
            bundle.putString(SAVE_KEY_TIME,view.tv_check_in.text?.toString())
            if (view.swich_round_trip.isChecked){
                isRoundTrip = view.swich_round_trip.isChecked
                bundle.putString(SAVE_KEY_TIME_TO,view.tv_check_out.text?.toString())
                bundle.putBoolean(SAVE_IS_ROUND_TRIP,view.swich_round_trip.isChecked)
            }

            var searchFlightFragment = SearchFlightFragment()

            searchFlightFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
                .replace(R.id.root_container, searchFlightFragment)
                .addToBackStack(null).commit()
        }

        view.btn_search_ticket.setOnClickListener {
            var listFlight: List<ListFlight> = getListFlightSearch(view)
            requestSearchFlight = RequestSearchFlight(
                selectPeopleMain!!.adult,
                selectPeopleMain!!.child,
                selectPeopleMain!!.baby,
                listFlight
            )
            var bundle = Bundle()
            bundle.putSerializable(REQUEST_SEARCH_TICKET,requestSearchFlight)
            bundle.putString(SAVE_KEY_GO,view.tv_flight_place_go.text?.toString())
            bundle.putString(SAVE_KEY_TO,view.tv_flight_place_to.text?.toString())
            bundle.putString(SAVE_KEY_TIME,view.tv_check_in.text?.toString())

            var ticketSearchedFragment = TicketSearchedFragment()
            ticketSearchedFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
                .add(R.id.root_container,ticketSearchedFragment)
                .addToBackStack(null).commit()
        }
    }

    private fun getListFlightSearch(view: View): List<ListFlight> {
        var list : MutableList<ListFlight> = mutableListOf()
        if (isRoundTrip) {
            Log.e(TAG,"isRoundTrip")
            val departureGo = CurTime.formatCheckInDate(view.tv_check_in.text.toString())
            Log.e("FlightBooking",departureGo)
            var flightGo = ListFlight(
                departureGo,
                view.tv_flight_code_to.text.toString(),
                view.tv_flight_code_go.text.toString())

            val departureTo = CurTime.formatCheckInDate(view.tv_check_out.text.toString())
            var flightTo = ListFlight(
                departureTo,
                view.tv_flight_code_go.text.toString(),
                view.tv_flight_code_to.text.toString())
            list.add(flightGo)
            list.add(flightTo)
        } else {
            val departureGo = CurTime.formatCheckInDate(view.tv_check_in.text.toString())

            Log.e(TAG,"Not Round Trip")
            var flightGo = ListFlight(
                departureGo,
                view.tv_flight_code_to.text.toString(),
                view.tv_flight_code_go.text.toString())
            list.add(flightGo)
        }
        return list.toList()
    }

    private fun swapPlaceGoAndTo(view: View) {
        var placeGo = view.tv_flight_place_go.text.toString()
        var placeCodeGo = view.tv_flight_code_go.text.toString()

        view.tv_flight_code_go.text = view.tv_flight_code_to.text
        view.tv_flight_place_go.text = view.tv_flight_place_to.text

        view.tv_flight_code_to.text = placeCodeGo
        view.tv_flight_place_to.text = placeGo
    }

    private fun pickDatePair() {

        var c = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val checkIn =
            CurTime.getTimeFromString(tv_check_in.text.toString()).time + MINISECOND_IN_DAY
        val checkOut =
            CurTime.getTimeFromString(tv_check_out.text.toString()).time + MINISECOND_IN_DAY
        val timePair = Pair<Long, Long>(checkIn, checkOut)
        val contraintsBuider =
            CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
        val datePicker = MaterialDatePicker.Builder
            .dateRangePicker()
            .setCalendarConstraints(contraintsBuider.setStart(c.timeInMillis).build())
            .setTheme(R.style.custom_date_picker)
            .setSelection(timePair).build()

        datePicker.show(requireActivity().supportFragmentManager, "DatePicker")

        datePicker.addOnPositiveButtonClickListener {
            if (it.second - it.first >= MINISECOND_30_DAYS) {
                showDialog(requireActivity())
            } else {
                tv_check_in.text = CurTime.getDateTimeString(
                    CurTime.convertLongtoDayofWeek(it.first),
                    CurTime.convertLongToTime(it.first)
                )
                tv_check_out.text = CurTime.getDateTimeString(
                    CurTime.convertLongtoDayofWeek(it.second),
                    CurTime.convertLongToTime(it.second)
                )
            }

        }

        datePicker

        // Setting up the event for when cancelled is clicked
        datePicker.addOnNegativeButtonClickListener {
            Toast.makeText(
                requireContext(),
                "${datePicker.headerText} is cancelled",
                Toast.LENGTH_LONG
            ).show()
        }

        // Setting up the event for when back button is pressed
        datePicker.addOnCancelListener {
            Toast.makeText(requireContext(), "Date Picker Cancelled", Toast.LENGTH_LONG).show()
        }
    }

    private fun pickDate() {
        var c = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        val contraintsBuider =
            CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())

        val checkIn =
            CurTime.getTimeFromString(tv_check_in.text.toString()).time + MINISECOND_IN_DAY

        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .setCalendarConstraints(contraintsBuider.setStart(c.timeInMillis).build())
            .setTheme(R.style.custom_date_picker)
            .setSelection(checkIn).build()

        datePicker.show(requireActivity().supportFragmentManager, "DatePicker")

        datePicker.addOnPositiveButtonClickListener {
            tv_check_in.text = CurTime.getDateTimeString(
                CurTime.convertLongtoDayofWeek(it),
                CurTime.convertLongToTime(it)
            )
        }

    }

    private fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.noti_dialog_fragment)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );
        val yesBtn = dialog.findViewById(R.id.btn_cancel_noti) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}