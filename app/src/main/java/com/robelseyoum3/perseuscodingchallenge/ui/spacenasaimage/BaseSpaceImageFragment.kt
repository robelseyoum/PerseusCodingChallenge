package com.robelseyoum3.perseuscodingchallenge.ui.spacenasaimage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.robelseyoum3.perseuscodingchallenge.ui.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseSpaceImageFragment : DaggerFragment() {

    val TAG: String = "AppDebug"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: SpaceViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(SpaceViewModel::class.java)
        }?: throw Exception("Invalid Activity")

    }
}