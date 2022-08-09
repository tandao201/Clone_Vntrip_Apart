package com.tan.login.Fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tan.login.DiaLogs.MenuDialog
import com.tan.login.Models.Login.ResponseLogin
import com.tan.login.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.menu_dialog.view.*
import org.greenrobot.eventbus.EventBus


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private val STOREAGE_PERMISSION_CODE = 100

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
        if (bundle != null) {
            var responseLogin: ResponseLogin = bundle.getSerializable("USER") as ResponseLogin
            Log.e("Home Fragment", responseLogin?.dataLogin?.accessToken.toString())
        }
        if (checkPermissionStoreage()) {
            makeToast("Đã được cấp quyền!")
        } else {
            requestPermisstions()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.item_menu_hotel.setOnClickListener {
            EventBus.getDefault().postSticky("Khách sạn")
            var hotelFragment: HotelFragment = HotelFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
                .replace(R.id.root_container, hotelFragment)
                .addToBackStack(null).commit()
        }

        view.item_menu_tiket.setOnClickListener {
            EventBus.getDefault().postSticky("Vé máy bay")
            var flightBookingFragment: FlightBookingFragment = FlightBookingFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
                .replace(R.id.root_container, flightBookingFragment)
                .addToBackStack(null).commit()
        }

        view.item_menu_combo.setOnClickListener {
            EventBus.getDefault().postSticky("Combo phòng vé")
            var comboFragment = ComboFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
                .replace(R.id.root_container, comboFragment)
                .addToBackStack(null).commit()
        }

        view.item_menu_quick_play.setOnClickListener {
            EventBus.getDefault().postSticky("Quick stay")
            var quickStayFragment = QuickStayFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_enter, R.anim.slide_leave)
                .replace(R.id.root_container, quickStayFragment)
                .addToBackStack(null).commit()
        }
        view.im_menu_header.setOnClickListener {
            val dialog = MenuDialog()
            dialog.show(requireActivity().supportFragmentManager, "Menu dialog")

        }
//        view.ln_group_contact.setOnClickListener {
//            EventBus.getDefault().postSticky("Clicked!")
//        }
    }

    private fun checkPermissionStoreage(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val write = ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val read = ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STOREAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty()) {
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if (write && read) {
                    makeToast("Request Granted!")
                } else {
                    makeToast("Request Denied!")
                }
            }
        }
    }

    private fun requestPermisstions() {
        Log.e(TAG, "requestPermisstions: trying...")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Log.e(TAG, "requestPermisstions: API above 11")
            try {
                Log.e(TAG, "requestPer: try")
                var intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                storeageActivityResult.launch(intent)
            } catch (e: Exception) {
                Log.e(TAG, "requestPer ", e)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                storeageActivityResult.launch(intent)
            }
        } else {
            // Android 11 below
            Log.e(TAG, "requestPermisstions: API below 11")
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                STOREAGE_PERMISSION_CODE
            )
        }
    }

    private val storeageActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.e(TAG, "storeageActivityResult : ")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    makeToast("Request Granted!")
                } else {
                    makeToast("Request Denied!")
                }
            } else {

            }
        }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

}