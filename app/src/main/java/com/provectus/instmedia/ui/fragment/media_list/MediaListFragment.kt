package com.provectus.instmedia.ui.fragment.media_list

import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.provectus.instmedia.R
import com.provectus.instmedia.entity.MediaFile
import com.provectus.instmedia.ui.activity.MainActivity
import com.provectus.instmedia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.frag_media_list.*
import org.koin.android.ext.android.get

class MediaListFragment : BaseFragment(), MediaListView {

    @InjectPresenter
    lateinit var mediaListPresenter: MediaListPresenter

    @ProvidePresenter
    fun provideMediaListPresenter() = get<MediaListPresenter>()

    override fun getLayoutResId(): Int = R.layout.frag_media_list

    override fun setUpMediaRecyclerView(mediaListAdapter: MediaListAdapter) {
        mediaRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mediaListAdapter
        }
    }

    override fun setCurrentRecyclerViewPosition(position: Int) {
        mediaRecyclerView.scrollToPosition(position)
    }

    override fun setUserName(userName: String) {
        (activity as MainActivity).setToolbarTitle(userName)
    }

    override fun navigateToShowDetails(mediaFile: MediaFile) {
        (activity as MainActivity).navigateToShowDetailsFragment(mediaFile)
    }

}