package com.tan.login.Fragments

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.*
import com.tan.login.Models.HotelSearch.RequestHotelSearch
import com.tan.login.Models.Location
import com.tan.login.R
import com.tan.login.FormatString.CurTime
import kotlinx.android.synthetic.main.fragment_hotel.*
import java.time.LocalDate
import java.util.*


class HotelFragment : Fragment() {

	companion object {
		private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
		private const val MINISECOND_IN_DAY: Long = 86400000
		private var MINISECOND_30_DAYS: Long = MINISECOND_IN_DAY * 30
	}

	private var locationTv: Location? = null

	private lateinit var fusedLocationClient: FusedLocationProviderClient

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
		return inflater.inflate(R.layout.fragment_hotel, container, false)
	}


	@RequiresApi(Build.VERSION_CODES.O)
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		init()
		eventClick()
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun init() {
		var today = LocalDate.now()
		var curDay = today.dayOfWeek.name
		var curDate = LocalDate.now()
		tv_check_in.text = CurTime.getDateTime(curDay,curDate)
		tv_check_out.text = CurTime.getDateTime(LocalDate.now().plusDays(1).dayOfWeek.name,curDate.plusDays(1))
		getLocation()
		var bundle = this.arguments
		if (bundle != null) {
			locationTv = bundle.getSerializable("CITY") as Location
			if (bundle.getString("checkIn")!=null ){
				tv_check_in.text = bundle.getString("checkIn")
				tv_check_out.text = bundle.getString("checkOut")
			}
			Log.e("Location HotelFragment",locationTv?.regionId.toString())
			tv_search_place.text = locationTv?.name
		} else {
			getLocation()
		}
	}

	private fun eventClick() {
		im_menu_header?.setOnClickListener {
			var homeFragment: HomeFragment = HomeFragment()
			requireActivity().supportFragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
				.replace(R.id.root_container,homeFragment).commit()
		}

		ln_search_place.setOnClickListener {
			var searchFragment: SearchPlaceFragment = SearchPlaceFragment()
			requireActivity().supportFragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.slide_up, R.anim.slide_up_out)
				.replace(R.id.root_container,searchFragment)
				.addToBackStack(null).commit()
		}

		ln_check_in.setOnClickListener {
			pickDate()
		}

		ln_check_out.setOnClickListener {
			pickDate()
		}

		btn_search_room.setOnClickListener {
			try {
				var days = CurTime.dateRange(tv_check_in.text.toString(),tv_check_out.text.toString())
				var checkInDate: String = CurTime.formatCheckInDate(tv_check_in.text.toString())
				Toast.makeText(requireActivity(),"${locationTv?.regionId} ${locationTv?.name} $days ${checkInDate}",Toast.LENGTH_SHORT).show()
				var request = RequestHotelSearch("app_android",locationTv?.regionId!!,days,1,
					checkInDate,tv_check_in.text.toString(),tv_check_out.text.toString(),tv_search_place.text.toString())

				var bundle = Bundle()
				bundle.putSerializable("REQUEST_SEARCH",request)
				var hotelSearchedFragment = HotelSearchedFragment()
				hotelSearchedFragment.arguments = bundle
				requireActivity().supportFragmentManager.beginTransaction()
					.setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
					.replace(R.id.root_container,hotelSearchedFragment)
					.addToBackStack(null).commit()
			} catch (e: Exception) {
				Toast.makeText(requireActivity(),"Lỗi!",Toast.LENGTH_SHORT).show()
			}
		}
	}

	private fun showDialog() {
		val dialog = Dialog(requireActivity())
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
		dialog.setCancelable(false)
		dialog.setContentView(R.layout.noti_dialog_fragment)
		dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
		val yesBtn = dialog.findViewById(R.id.btn_cancel_noti) as Button
		yesBtn.setOnClickListener {
			dialog.dismiss()
		}
		dialog.show()
	}

	private fun pickDate() {

		var c = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
		val checkIn = CurTime.getTimeFromString(tv_check_in.text.toString()).time + MINISECOND_IN_DAY
		val checkOut = CurTime.getTimeFromString(tv_check_out.text.toString()).time + MINISECOND_IN_DAY
		val timePair = Pair<Long,Long>(checkIn,checkOut)
		val contraintsBuider = CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
		val datePicker = MaterialDatePicker.Builder
			.dateRangePicker()
			.setCalendarConstraints(contraintsBuider.setStart(c.timeInMillis).build())
			.setTheme(R.style.custom_date_picker)
			.setSelection(timePair).build()

		datePicker.show(requireActivity().supportFragmentManager, "DatePicker")

		datePicker.addOnPositiveButtonClickListener {
			if (it.second-it.first >= MINISECOND_30_DAYS) {
				showDialog()
			} else {
				tv_check_in.text = CurTime.getDateTimeString(CurTime.convertLongtoDayofWeek(it.first),CurTime.convertLongToTime(it.first))
				tv_check_out.text = CurTime.getDateTimeString(CurTime.convertLongtoDayofWeek(it.second),CurTime.convertLongToTime(it.second))
			}

		}

		datePicker

		// Setting up the event for when cancelled is clicked
		datePicker.addOnNegativeButtonClickListener {
			Toast.makeText(requireContext(), "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
		}

		// Setting up the event for when back button is pressed
		datePicker.addOnCancelListener {
			Toast.makeText(requireContext(), "Date Picker Cancelled", Toast.LENGTH_LONG).show()
		}
	}
	// 0344850761

	fun getLocation() {

		if (ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {
			return
		}
		val geocoder = Geocoder(requireContext(),Locale.getDefault())
		fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
			val location = task.result
			if (location == null) {
				Toast.makeText(requireContext(),"Error!",Toast.LENGTH_SHORT).show()
			} else {
				var address: MutableList<Address>
				try {
					address = geocoder.getFromLocation(location.latitude, location.longitude,1)
					var listAdd = address[0].getAddressLine(0).split(",")
					var district = listAdd[listAdd.size-3].trim()
					var city = listAdd[listAdd.size-2].trim()
					tv_search_place.text = "$district, $city"
					Log.e("Location", "Quận:  $district,  TP: $city")
					locationTv = Location(district,66)
					Log.e("Location2", "Quận:  ${locationTv?.regionId},  TP: ${locationTv?.name}")
				} catch (e: Exception) {
					Log.e("Exception Location",e.toString())
				}
			}
		}
	}

}