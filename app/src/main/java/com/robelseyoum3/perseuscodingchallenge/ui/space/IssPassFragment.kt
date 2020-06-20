package com.robelseyoum3.perseuscodingchallenge.ui.space

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.robelseyoum3.perseuscodingchallenge.R
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Response
import com.robelseyoum3.perseuscodingchallenge.ui.adapter.SpaceAdapter
import com.robelseyoum3.perseuscodingchallenge.utils.Resource
import kotlinx.android.synthetic.main.fragment_isspasstimes.*


class IssPassFragment : BaseSpaceFragment() {

    lateinit var spaceAdapter: SpaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_isspasstimes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "IssPassFragment: ${viewModel.hashCode()} ")
        setupRecyclerView()
        val latitude = arguments?.getString(LATITUDE1).orEmpty()
        val longitude = arguments?.getString(LONGITUDE1).orEmpty()
        subscribes(latitude, longitude)
    }

    private fun subscribes(latitude: String, longitude: String) {

        if(viewModel.lastFetchedTime == null){
            viewModel.getISSOverheadLocation(latitude, longitude)
        }

        viewModel.notifyResults.observe(viewLifecycleOwner, Observer {
            when(it){
                    is Resource.Loading -> displayProgressbar()
                    is Resource.Success -> displayList(it.data!!.toMutableList())
                    is Resource.Error -> displayMessageContainer(it.message)
                    else -> displayMessageContainer("Unknown Error")
                }
        })
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

    companion object {
        const val LATITUDE1 = "latitude"
        const val LONGITUDE1 = "longitude"

        fun newInstance(latitude: String?, longitude: String?): IssPassFragment {
            val fragment = IssPassFragment()
            val bundle =  Bundle().apply {
                putString(LATITUDE1, latitude)
                putString(LONGITUDE1, longitude)
            }
            fragment.arguments = bundle
            return  fragment
        }
    }


}