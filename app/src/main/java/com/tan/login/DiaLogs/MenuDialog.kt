package com.tan.login.DiaLogs

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.tan.login.R
import kotlinx.android.synthetic.main.menu_dialog.*
import kotlinx.android.synthetic.main.menu_dialog.view.*


class MenuDialog: DialogFragment() {

    private val STOREAGE_PERMISSION_CODE = 100
    private val TAG = "MenuDialog"

    override fun onStart() {
        super.onStart()
        val d = dialog
        if (d != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            d.window!!.setLayout(width, height)
            d.window!!.setGravity(Gravity.TOP or Gravity.LEFT)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.menu_dialog,container,false)
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        view.btn_requestPermission.setOnClickListener {
            if ( checkPermissionStoreage()) {
                makeToast("Đã được cấp quyền!")
            } else {
                requestPermisstions()
            }
        }

        view.btn_back_to_home.setOnClickListener {
            dialog!!.dismiss()
        }
        return view
    }

    private fun checkPermissionStoreage(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Log.e(TAG, "checkPermissionStoreage: API above 11")
            Environment.isExternalStorageManager()
        } else {
            Log.e(TAG, "checkPermissionStoreage: API below 11")
            val write = ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val read = ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
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
                Log.e(TAG,"requestPer: try")
                var intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package",requireActivity().packageName,null)
                intent.data = uri
                storeageActivityResult.launch(intent)
            } catch (e: Exception) {
                Log.e(TAG,"requestPer ",e)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                storeageActivityResult.launch(intent)
            }
        } else {
            // Android 11 below
            Log.e(TAG, "requestPermisstions: API below 11")
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE),
                STOREAGE_PERMISSION_CODE)
        }
    }

    private val storeageActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Log.e(TAG, "storeageActivityResult : ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if ( Environment.isExternalStorageManager()) {
                makeToast("Request Granted!")
            } else {
                makeToast("Request Denied!")
            }
        } else {

        }
    }

    private fun makeToast(msg: String){
        Toast.makeText(requireActivity(),msg, Toast.LENGTH_SHORT).show()
    }
}