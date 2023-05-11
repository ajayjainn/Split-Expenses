package com.example.splitexpenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitexpenses.databinding.FragmentTransactionsBinding
import com.example.splitexpenses.model.SplitViewModel
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class TransactionsFragment : Fragment() {

    private val myViewModel: SplitViewModel by activityViewModels()
    private lateinit var binding: FragmentTransactionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        binding.listNames.layoutManager = LinearLayoutManager(requireContext())
        binding.listTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.listNames.adapter = CheckboxAdapter(myViewModel)
        binding.listTransactions.adapter = TransactionAdapter(myViewModel)
        return binding.root
    }

    private var currentSelected: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = myViewModel._member_names.keys.toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.name, items)
        (binding.menu.editText as? MaterialAutoCompleteTextView)?.setAdapter(adapter)
        binding.addTransaction.setOnClickListener {
            addTransaction()
        }
        binding.viewSummary.setOnClickListener {
            findNavController().navigate(R.id.action_transactionsFragment2_to_summaryFragment2)
        }
        binding.teriMommy.setOnItemClickListener { parent, _, position, _ ->
            currentSelected = parent.getItemAtPosition(position).toString()
        }
    }

    private fun addTransaction() {
        val amt = binding.AmountEditText.editableText.toString()
        if (myViewModel._owedBy.value!!.size == 1 || currentSelected == "" || amt == "") {
            Toast.makeText(requireContext(),"Enter the required information",Toast.LENGTH_SHORT).show()
            return
        }

        myViewModel.spendBy(amt, currentSelected)

        binding.listTransactions.adapter?.notifyItemInserted(myViewModel.allTransactions.size-1)
        binding.listNames.adapter = CheckboxAdapter(myViewModel)
        binding.AmountEditText.text!!.clear()
    }
}