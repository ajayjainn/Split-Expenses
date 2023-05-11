package com.example.splitexpenses.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.round
import kotlin.math.roundToLong

class SplitViewModel: ViewModel() {
    val allTransactions = mutableListOf<String>()
    val _member_names = LinkedHashMap<String, Double>()

    val _owedBy = MutableLiveData<MutableList<String>>(mutableListOf(""))
    var owedBy: LiveData<MutableList<String>> = _owedBy
    var netTransactions = mutableListOf<String>()

    fun addMember(name: String) {
        _member_names[name] = 0.0
    }

    fun spendBy(amount: String, by: String) {
        val div = amount.toInt() * 1.0 / (owedBy.value!!.size-1)
        if(div*(_owedBy.value!!.size-1)!=amount.toDouble()){
            round(div)
        }
        _member_names[by] = _member_names[by]!!+amount.toDouble()
        for (name in owedBy.value!!) {
            if (name == "") {
                continue
            }
            _member_names[name] = _member_names[name]!! - div.toDouble()
        }
        allTransactions.add("$by spends $amount owed by ${owedBy.value!!.joinToString(" ")}")
        _owedBy.value!!.clear()
        _owedBy.value!!.add("")
    }

    fun minCashFlowRec(amount: MutableList<Double>,names:List<String>) {
        val mxCredit = amount.indexOf(amount.toList().max())
        val mxDebit = amount.indexOf(amount.toList().min())
        if ((amount[mxCredit]<1 ) && amount[mxDebit]>-1)
            return;
        val min: Double = minOf(-amount[mxDebit], amount[mxCredit])
        amount[mxCredit] = amount[mxCredit] - min
        amount[mxDebit] =amount[mxDebit] + min
        netTransactions.add("• ${names[mxDebit]} pays ${min.toInt()} to ${names[mxCredit]} ")
        minCashFlowRec(amount,names)
    }

    fun updateTransactions(){
        netTransactions.clear()
        val amount = mutableListOf<Double>()
        val names = mutableListOf<String>()
        for((key,value) in _member_names){
            if(key==""){
                continue
            }
            names.add(key)
            amount.add(value)
        }
        minCashFlowRec(amount,names)
        _owedBy.value!!.clear()
        _owedBy.value!!.add("")
    }

}