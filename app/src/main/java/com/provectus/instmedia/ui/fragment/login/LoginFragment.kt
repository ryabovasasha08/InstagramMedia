package com.provectus.instmedia.ui.fragment.login

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.provectus.instmedia.Constants.BASE_URL_AUTH
import com.provectus.instmedia.Constants.CLIENT_ID
import com.provectus.instmedia.Constants.REDIRECT_URL
import com.provectus.instmedia.R
import com.provectus.instmedia.ui.activity.MainActivity
import com.provectus.instmedia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.frag_login.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import java.net.URLDecoder

class LoginFragment : BaseFragment(), LoginView {

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    @ProvidePresenter
    fun provideLoginPresenter() = get<LoginPresenter> { parametersOf(context) }

    override fun getLayoutResId(): Int = R.layout.frag_login

    @SuppressLint("SetJavaScriptEnabled")
    override fun openWebView() {
        val requestUrl = getWebViewUrl()

        insta_login_webview.apply {
            settings.javaScriptEnabled = true
            loadUrl(requestUrl)
            webViewClient = instWebViewClient
        }
    }

    override fun showNoInternetDialog() {
        MaterialAlertDialogBuilder(context)
                .setTitle(R.string.no_internet)
                .setMessage(R.string.no_internet_message)
                .setPositiveButton("Try again") { _, _ -> loginPresenter.checkInternetConnection(context!!) }
                .create()
                .show()
    }

    override fun navigateToMediaList() {
        (activity as MainActivity).navigateToMediaListFragment()
    }

    private fun getWebViewUrl() =
            BASE_URL_AUTH
                    .plus(WEB_VIEW_SUB_URL)
                    .plus("?client_id=${CLIENT_ID}")
                    .plus("&redirect_uri=${REDIRECT_URL}")
                    .plus("&response_type=code")
                    .plus("&display=touch")
                    .plus("&scope=user_profile,user_media")

    private fun getAuthCode(url: String): String {
        val decodedUrl = URLDecoder.decode(url, "UTF-8")
        return decodedUrl.substringBeforeLast("#_").substringAfterLast("=")
    }

    private val instWebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
            return url.startsWith(REDIRECT_URL)
        }

        override fun onPageFinished(view: WebView?, url: String) {
            super.onPageFinished(view, url)
            if (!url.contains(WEB_VIEW_SUB_URL)) {
                val accessCode = getAuthCode(url)
                loginPresenter.getAccessToken(accessCode)
            }
        }

    }

    companion object {
        const val WEB_VIEW_SUB_URL = "oauth/authorize/"
    }

}