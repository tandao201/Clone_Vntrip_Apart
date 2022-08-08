package com.tan.login.DiaLogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.tan.login.Fragments.HomeFragment
import com.tan.login.Fragments.HotelFragment
import com.tan.login.R
import kotlinx.android.synthetic.main.date_picker_dialog.*
import kotlinx.android.synthetic.main.fragment_hotel.*
import kotlinx.android.synthetic.main.fragment_hotel.im_menu_header

class DatePickerDialog: DialogFragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		var view = inflater.inflate(R.layout.date_picker_dialog,container,false)
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		im_menu_header?.setOnClickListener {
			var hotelFragment: HotelFragment = HotelFragment()
			requireActivity().supportFragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right)
				.replace(R.id.root_container,hotelFragment)
				.addToBackStack(null).commit()
		}
//		calv_date_pick.setOnDateChangeListener { calendarView, i, i2, i3 ->
//			Toast.makeText(requireContext(),"nam: $i, thang: $i2, ngay: $i3",Toast.LENGTH_SHORT).show()
//		}

		val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
		datePicker.show(requireActivity().supportFragmentManager, "DatePicker")

		datePicker.addOnPositiveButtonClickListener {
			Toast.makeText(requireContext(), "${datePicker.headerText} is selected", Toast.LENGTH_LONG).show()
		}

		// Setting up the event for when cancelled is clicked
		datePicker.addOnNegativeButtonClickListener {
			Toast.makeText(requireContext(), "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
		}

		// Setting up the event for when back button is pressed
		datePicker.addOnCancelListener {
			Toast.makeText(requireContext(), "Date Picker Cancelled", Toast.LENGTH_LONG).show()
		}
	}
}