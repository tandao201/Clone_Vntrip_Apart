package com.tan.login.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tan.login.Models.Login.RequestLogin
import com.tan.login.Models.Login.ResponseLogin
import com.tan.login.R
import com.tan.login.Repositories.UserRepo
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

	var userRepo = UserRepo()
	var mShowPass = true
	var errorNoti :String = ""
	var responseLogin: ResponseLogin? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		var view = inflater.inflate(R.layout.fragment_login, container, false)
		userRepo.setUserDao(requireActivity())
		if (view.layout_main != null){
			view.layout_main.setOnTouchListener(object : View.OnTouchListener {
				override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
					view.et_username.clearFocus()
					view.et_password.clearFocus()
					hideKeyboard()
					return false
				}
			})
		}
		showPassTouchEvent(view)
		view.btn_login.setOnClickListener {
			errorNoti = checkInput()
			if (errorNoti.isNotEmpty()) {
				view.tv_error.visibility = View.VISIBLE
				view.tv_error.text = errorNoti
			} else {
				Toast.makeText(requireContext(),"Thông tin đúng định dạng!", Toast.LENGTH_SHORT).show()
				var phone = view.et_username.text.toString().trim()
				phone = "+84"+phone.substring(1)
				var password = view.et_password.text.toString().trim()
				var requestLogin = RequestLogin(phone,password)
				login(requestLogin)
			}
			view.et_username.clearFocus()
			view.et_password.clearFocus()
			hideKeyboard()
		}
		return view
	}

	private fun login(requestLogin: RequestLogin) {
		var call = userRepo.login(requestLogin)
		call.enqueue(object : Callback<ResponseLogin> {
			@SuppressLint("ResourceType")
			override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
				if ( response?.body() != null){
					responseLogin = response.body()!!

					Log.e("Login successfull",responseLogin!!.status.toString())
					Log.e("Login successfull",responseLogin!!.message.toString())

					if (responseLogin!!.status == "success") {
						Log.e("Login data",responseLogin?.dataLogin!!.refreshToken!!)
						if (responseLogin?.dataLogin!! != null){
							lifecycleScope.launch {
								userRepo.addDataLogin(responseLogin?.dataLogin!!)
							}
						} else {
							Log.e("Login error ","can not insert to db")
						}
						var bundle = Bundle()
						bundle.putSerializable("USER",responseLogin)
						var homeFragment: HomeFragment = HomeFragment()
						homeFragment.arguments = bundle
						requireActivity().supportFragmentManager.beginTransaction().replace(R.id.root_container,homeFragment)
							.addToBackStack(null).commit()
					} else {
						Toast.makeText(requireContext(),responseLogin?.message,Toast.LENGTH_SHORT).show()
					}
				}
			}

			override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

			}

		})
	}

	private fun checkInput() :String{
		var error = ""
		var username = et_username.text
		var password = et_password.text

		if (TextUtils.isEmpty(username) ){
			et_username.setBackgroundResource(R.drawable.border_et_error)
			error = "Số điện thoại hoặc Email không để trống."
			return error
		}

		if (!isValidMobile(username.toString()) && !isValidEmail(username.toString())){
			et_username.setBackgroundResource(R.drawable.border_et_error)
			error = "Email hoặc số điện thoại không đúng định dạng."
			return error
		}

		if (TextUtils.isEmpty(password) ){
			et_password.setBackgroundResource(R.drawable.border_et_error)
			error = "Mật khẩu không để trống."
			return error
		}

		if (password.length<8){
			et_password.setBackgroundResource(R.drawable.border_et_error)
			error = "Mật khẩu phải chứa từ 8 kí tự trở lên."
			return error
		}

		return error
	}

	private fun isValidMobile(phone: String): Boolean {
		val allCountryRegex =
			"^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$"
		return phone.matches(allCountryRegex.toRegex())
	}

	private fun isValidEmail(target: String?): Boolean {
		return  Patterns.EMAIL_ADDRESS.matcher(target).matches()
	}

	private fun showPassTouchEvent(view: View) {
		view.et_username.setOnTouchListener(object :View.OnTouchListener {
			override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
				if (errorNoti.isNotEmpty()){
					view.et_username.setBackgroundResource(R.drawable.edittext)
					view.tv_error?.visibility = View.GONE
				} else {
					view.et_username.setBackgroundResource(R.drawable.edittext)
				}
				return false
			}

		})

		view.et_password.setOnTouchListener(object : View.OnTouchListener {
			override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
				if (errorNoti.isNotEmpty()){
//					et_password.setBackgroundResource(R.drawable.border_et_focused)
					view.et_password.setBackgroundResource(R.drawable.edittext)
					view.tv_error.visibility = View.GONE
				} else {
					view.et_password.setBackgroundResource(R.drawable.edittext)
				}
				var DRAWABLE_RIGHT: Int = 2;

				if (p1?.getAction() == MotionEvent.ACTION_UP) {
					if (p1?.getRawX()!! >= (et_password.getRight() - view.et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds()
							.width()-25)
					) {
						hideKeyboard()
						mShowPass = !mShowPass
						showPass(mShowPass)
						return true;
					}
				}
				return false;
			}

		})
		showPass(mShowPass)
	}

	private fun showPass(isShow: Boolean) {
		if (!isShow) {
			view?.et_password?.transformationMethod = HideReturnsTransformationMethod.getInstance()
			view?.et_password?.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visible, 0);
		} else {
			view?.et_password?.transformationMethod = PasswordTransformationMethod.getInstance()
			view?.et_password?.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_invisible, 0);
		}
		view?.et_password?.setSelection(et_password.text.toString().length)
	}

	private fun hideKeyboard() {
		val inputManager: InputMethodManager =
			requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		inputManager.hideSoftInputFromWindow(
			requireActivity().currentFocus!!.windowToken,
			InputMethodManager.HIDE_NOT_ALWAYS
		)
	}

}