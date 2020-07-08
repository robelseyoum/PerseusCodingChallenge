package com.robelseyoum3.perseuscodingchallenge.ui.space

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.robelseyoum3.perseuscodingchallenge.R
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Response
import com.robelseyoum3.perseuscodingchallenge.ui.adapter.SpaceAdapter
import com.robelseyoum3.perseuscodingchallenge.ui.nasaimage.SatelliteImageFragment
import com.robelseyoum3.perseuscodingchallenge.ui.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_isspasstimes.*
import javax.inject.Inject


class IssPassFragment : DaggerFragment() {

    lateinit var spaceAdapter: SpaceAdapter
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var currentDate: String

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: SpaceViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_isspasstimes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(SpaceViewModel::class.java)
        }?: throw Exception("Invalid Activity")

   //     Log.d(TAG, "IssPassFragment: ${viewModel.hashCode()} ")
        setupRecyclerView()
        latitude = arguments?.getString(LATITUDE1).orEmpty()
        longitude = arguments?.getString(LONGITUDE1).orEmpty()
        currentDate = arguments?.getString(CURRENT_DATE).orEmpty()
        subscribes(latitude, longitude)
    }

    private fun subscribes(latitude: String, longitude: String) {

        if(viewModel.lastFetchedTime == null){
            viewModel.getISSOverheadLocation(latitude, longitude)
        }


        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when(it){
                    is SpaceViewModel.LoadingState.LOADING -> displayProgressbar()
                    is SpaceViewModel.LoadingState.SUCCESS-> displayList(it.response.toMutableList())
                    is SpaceViewModel.LoadingState.ERROR -> displayMessageContainer(it.message)
                    else -> displayMessageContainer("Unknown Error")
                }
        })

        btnRetry.setOnClickListener {
            viewModel.getISSOverheadLocation(latitude, longitude)
        }
    }

    private fun displayMessageContainer(message: String?) {
        llMessageContainer.visibility = View.VISIBLE
        rvList.visibility = View.GONE
        progress_bar_frg.visibility = View.GONE
        tvMessage.text = message
    }

    private fun displayList(list: MutableList<Response>) {
        spaceAdapter.notify.clear()
        spaceAdapter.notify.addAll(list)
        spaceAdapter.notifyDataSetChanged()
        llMessageContainer.visibility = View.GONE
        rvList.visibility = View.VISIBLE
        progress_bar_frg.visibility = View.GONE
    }

    private fun displayProgressbar() {
            progress_bar_frg.visibility = View.VISIBLE
            rvList.visibility = View.GONE
            llMessageContainer.visibility = View.GONE
    }

    private fun setupRecyclerView() {
            rvList.layoutManager = LinearLayoutManager(view?.context)
            spaceAdapter = SpaceAdapter(mutableListOf())
            rvList.adapter = spaceAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.image_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.acton_image -> triggerNasaImage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerNasaImage() {
        val satelliteImageFragment =
            SatelliteImageFragment()
        val args = Bundle()
        args.apply {
            putString(LATITUDE1, latitude)
            putString(LONGITUDE1, longitude)
            putString(CURRENT_DATE, currentDate)
        }
        satelliteImageFragment.arguments = args

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, satelliteImageFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    companion object {
        const val LATITUDE1 = "latitude"
        const val LONGITUDE1 = "longitude"
        const val CURRENT_DATE = "date"

        fun newInstance(latitude: String?, longitude: String?, currentDate: String): IssPassFragment {
            val fragment = IssPassFragment()
            val bundle =  Bundle().apply {
                putString(LATITUDE1, latitude)
                putString(LONGITUDE1, longitude)
                putString(CURRENT_DATE, currentDate)

            }
            fragment.arguments = bundle
            return  fragment
        }
    }


}