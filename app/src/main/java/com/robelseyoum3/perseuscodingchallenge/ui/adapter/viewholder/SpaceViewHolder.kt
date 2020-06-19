package com.robelseyoum3.perseuscodingchallenge.ui.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.time_rows.view.*

class SpaceViewHolder(item: View): RecyclerView.ViewHolder(item){
    val tvDuration: TextView = item.tvDisplayDuration
    val tvRiseTime: TextView = item.tvDisplayRiseTime
}