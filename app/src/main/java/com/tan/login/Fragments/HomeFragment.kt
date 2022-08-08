package com.tan.login.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.tan.login.Models.Login.ResponseLogin
import com.tan.login.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		var view = inflater.inflate(R.layout.fragment_home, container, false)
		var bundle = this.arguments
		if (bundle != null){
			var responseLogin: ResponseLogin = bundle.getSerializable("USER") as ResponseLogin
			Log.e("Home Fragment", responseLogin?.dataLogin?.accessToken.toString())
		}
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.item_menu_hotel.setOnClickListener {
			var hotelFragment: HotelFragment = HotelFragment()
			requireActivity().supportFragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
				.replace(R.id.root_container,hotelFragment)
				.addToBackStack(null).commit()
		}

		view.item_menu_tiket.setOnClickListener {
			var flightBookingFragment: FlightBookingFragment = FlightBookingFragment()
			requireActivity().supportFragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
				.replace(R.id.root_container,flightBookingFragment)
				.addToBackStack(null).commit()
		}

		view.item_menu_combo.setOnClickListener {
			var comboFragment = ComboFragment()
			requireActivity().supportFragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
				.replace(R.id.root_container,comboFragment)
				.addToBackStack(null).commit()
		}

		view.item_menu_quick_play.setOnClickListener {
			var quickStayFragment = QuickStayFragment()
			requireActivity().supportFragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
				.replace(R.id.root_container,quickStayFragment)
				.addToBackStack(null).commit()
		}
	}

}