package com.robelseyoum3.perseuscodingchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robelseyoum3.perseuscodingchallenge.R
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.OpenNotify
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Response
import com.robelseyoum3.perseuscodingchallenge.ui.adapter.viewholder.SpaceViewHolder

class SpaceAdapter constructor(val notify: MutableList<Response>): RecyclerView.Adapter<SpaceViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceViewHolder =
        SpaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.time_rows, parent, false))

    override fun getItemCount() =  notify.size

    override fun onBindViewHolder(holder: SpaceViewHolder, position: Int) {
        holder.tvDuration.text = notify[position].duration.toString()
        holder.tvRiseTime.text = notify[position].risetime.toString()
    }

}