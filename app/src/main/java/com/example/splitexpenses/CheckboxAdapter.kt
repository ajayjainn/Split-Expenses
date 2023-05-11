package com.example.splitexpenses
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.splitexpenses.model.SplitViewModel

class CheckboxAdapter(private var myViewModel : SplitViewModel) : RecyclerView.Adapter<CheckboxAdapter.CheckboxViewHolder>()  {
    class CheckboxViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val checkBox : CheckBox = view.findViewById(R.id.checkbox_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.checkbox_item , parent , false)
        return CheckboxViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return myViewModel._member_names.size
    }

    override fun onBindViewHolder(holder: CheckboxViewHolder, position: Int) {
        var count = 0
        for (i in myViewModel._member_names.keys){
            if(count == position){
                holder.checkBox.setOnClickListener{
                    if(!holder.checkBox.isChecked){
                        myViewModel._owedBy.value!!.remove(holder.checkBox.text.toString())

                    }else{
                        myViewModel._owedBy.value!!.add(holder.checkBox.text.toString())
                    }
                }
                holder.checkBox.text = i
                break
            }
            count++
        }
    }
}