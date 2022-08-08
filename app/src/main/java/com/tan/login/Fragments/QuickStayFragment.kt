package com.tan.login.Fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.webkit.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tan.login.R
import kotlinx.android.synthetic.main.fragment_combo.*
import kotlinx.android.synthetic.main.fragment_combo.view.*
import kotlinx.android.synthetic.main.fragment_quick_stay.*
import kotlinx.android.synthetic.main.fragment_quick_stay.progress_circular
import kotlinx.android.synthetic.main.fragment_quick_stay.view.*
import kotlinx.android.synthetic.main.fragment_quick_stay.view.imv_icon_back
import kotlinx.android.synthetic.main.fragment_quick_stay.view.imv_icon_close
import kotlinx.android.synthetic.main.fragment_quick_stay.view.webview_combo
import kotlinx.android.synthetic.main.fragment_quick_stay.webview_combo

class QuickStayFragment: Fragment() {
    private val URL: String = "https://quickstay.vntrip.vn/"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_quick_stay, container, false)
        eventClick(view)
        setUpWebview(view)

        return view
    }

    private fun setUpWebview(view: View) {
        view.webview_combo.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return false
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progress_circular.visibility = View.GONE
                webview_combo.visibility = View.VISIBLE
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress_circular.visibility = View.VISIBLE
                webview_combo.visibility = View.GONE
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                val errorMessage = "Got Error! $error"
                Toast.makeText(requireActivity(),errorMessage,Toast.LENGTH_SHORT).show()
                super.onReceivedError(view, request, error)
            }
        }

        view.webview_combo.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        CookieManager.getInstance().setAcceptThirdPartyCookies(view.webview_combo, true)


        view.webview_combo.apply {
            loadUrl(URL)
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            canGoBack()
            setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && view.webview_combo.canGoBack()){
                    view.webview_combo.goBack()
                    return@OnKeyListener true
                }
                false
            })
        }
    }

    private fun eventClick(view: View) {
        view.imv_icon_close?.setOnClickListener {
            var homeFragment: HomeFragment = HomeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
                .replace(R.id.root_container, homeFragment).commit()
        }
        view.imv_icon_back?.setOnClickListener {
            if (webview_combo.canGoBack())
                webview_combo.goBack()
            else {
                var homeFragment: HomeFragment = HomeFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
                    .replace(R.id.root_container, homeFragment).commit()
            }
        }
    }
}