package com.robelseyoum3.perseuscodingchallenge.ui.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robelseyoum3.perseuscodingchallenge.R
import com.robelseyoum3.perseuscodingchallenge.data.model.isspasstimes.Response
import com.robelseyoum3.perseuscodingchallenge.ui.adapter.viewholder.SpaceViewHolder
import java.text.SimpleDateFormat
import java.util.*


class SpaceAdapter constructor(val notify: MutableList<Response>): RecyclerView.Adapter<SpaceViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceViewHolder =
        SpaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.time_rows, parent, false))

    override fun getItemCount() =  notify.size

    override fun onBindViewHolder(holder: SpaceViewHolder, position: Int) {
        holder.tvDuration.text = formatDuration(notify[position].duration.toLong())
        holder.tvRiseTime.text = getDateString(notify[position].risetime.toLong())
    }

    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)

    private fun getDateString(time: Long) : String = simpleDateFormat.format(time * 1000L)

    private fun formatDuration(seconds: Long): String = if (seconds < 60) {
        seconds.toString()
    } else {
        DateUtils.formatElapsedTime(seconds)
    }


}