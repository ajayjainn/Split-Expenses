package com.example.splitexpenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitexpenses.model.SplitViewModel

class SummaryAdapter(private var myViewModel : SplitViewModel): RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {
    class SummaryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val tv = view.findViewById<TextView>(R.id.name_item)!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SummaryAdapter.SummaryViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.name, parent, false)
        return SummaryViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return myViewModel.netTransactions.size
    }

    override fun onBindViewHolder(holder: SummaryAdapter.SummaryViewHolder, position: Int) {
        holder.tv.text = myViewModel.netTransactions[position]
    }

}