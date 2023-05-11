package com.example.splitexpenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitexpenses.model.SplitViewModel

class MemberNameAdapter(private var myViewModel : SplitViewModel) : RecyclerView.Adapter<MemberNameAdapter.MemberNameViewHolder>() {
    class MemberNameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.name_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberNameViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.name, parent, false)
        return MemberNameViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MemberNameViewHolder, position: Int) {
        var count = 0
        for (i in myViewModel._member_names.keys) {
            if (count == position) {
                holder.textView.text = i
                break
            }
            count++
        }

    }
    override fun getItemCount() = myViewModel._member_names.size
}