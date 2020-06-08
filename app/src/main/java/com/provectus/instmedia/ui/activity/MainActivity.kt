package com.provectus.instmedia.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.provectus.instmedia.R
import com.provectus.instmedia.entity.MediaFile
import com.provectus.instmedia.ui.fragment.login.LoginFragment
import com.provectus.instmedia.ui.fragment.media_list.MediaListFragment
import com.provectus.instmedia.ui.fragment.show_details.ShowDetailsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get

class MainActivity : MainView, MvpAppCompatActivity() {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = get<MainPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun navigateToLoginFragment() {
        replace(LoginFragment(), false)
    }

    fun navigateToMediaListFragment() {
        showToolbar()
        replace(MediaListFragment(), false)
    }

    fun navigateToShowDetailsFragment(mediaFile: MediaFile) {
        showToolbar()
        replace(ShowDetailsFragment.newInstance(mediaFile), true)
    }

    fun setToolbarTitle(username: String) {
        toolbarTitle.text = username
    }

    private fun replace(fragment: Fragment, addToBackStack: Boolean) {
        val tag = fragment::class.java.name

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment, tag)
            if (addToBackStack) addToBackStack(tag)
            commit()
        }
    }

    private fun showToolbar() {
        toolbar.visibility = View.VISIBLE
    }

}