package com.tan.login.Activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.webkit.WebViewFragment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.tan.login.Fragments.ComboFragment
import com.tan.login.Fragments.HomeFragment
import com.tan.login.Fragments.LoginFragment
import com.tan.login.Models.Login.DataLogin
import com.tan.login.Models.Login.ResponseLogin
import com.tan.login.R
import com.tan.login.Repositories.UserRepo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {

    private val DATA_DB_KEY = "DATA DB"
    private val DATA_API_KEY = "DATA API"

    private val STOREAGE_PERMISSION_CODE = 100
    private val TAG = "MainActivity"

    private val userRepo = UserRepo()
    private var dataLoginDb: DataLogin? = null
    var dataLoginApi: DataLogin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRepo.setUserDao(this)
        getDataIntent()
        if (savedInstanceState == null) {
            hasUserLogined()
        }
    }

    private fun checkPermissionStoreage(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val write = ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val read = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
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

                } else {

                }
            }
        }
    }

    private fun requestPermisstions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Log.e(TAG,"requestPer: try")
                var intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package",this.packageName,null)
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
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE),
            STOREAGE_PERMISSION_CODE)
        }
    }

    private val storeageActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Log.e(TAG, "storeageActivityResult : ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if ( Environment.isExternalStorageManager()) {

            }
        }
    }

    private fun hasUserLogined() {
        if (dataLoginDb != null) {
            if (dataLoginApi != null) {
                Log.e("Main activity", "dataLoginApi khac null")
                var homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.root_container, homeFragment).commit()
            } else {
                Log.e("Main activity", "dataLoginApi null")
                openLoginFragment()
            }
        } else {
            Log.e("Main activity", "dataLoginDb null")
            openLoginFragment()
        }
    }

    private fun getDataIntent() {
        val intent = intent
        if (intent != null) {
            if (intent.hasExtra(DATA_DB_KEY)) {
                dataLoginDb = intent.getSerializableExtra(DATA_DB_KEY) as DataLogin?
            }
            if (intent.hasExtra(DATA_API_KEY)) {
                dataLoginApi = intent.getSerializableExtra(DATA_API_KEY) as DataLogin?
            }
        }
    }

    private fun openLoginFragment() {
        var loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.root_container, loginFragment)
            .commit()
    }
}