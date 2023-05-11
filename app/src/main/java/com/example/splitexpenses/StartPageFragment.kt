package com.example.splitexpenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitexpenses.databinding.FragmentStartPageBinding
import com.example.splitexpenses.model.SplitViewModel

class StartPageFragment : Fragment() {
    private lateinit var binding: FragmentStartPageBinding

    private val myViewModel: SplitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartPageBinding.inflate(inflater, container, false)
        binding.listNames.layoutManager = LinearLayoutManager(requireActivity())
        binding.listNames.adapter = MemberNameAdapter(myViewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nameSubmit.setOnClickListener {
            val name = binding.enterNameSubmitText.text
            if (name.isNullOrEmpty()) {
                Toast.makeText(requireActivity(), "Enter the name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (myViewModel._member_names.containsKey(name.toString())) {
                Toast.makeText(
                    requireContext(),
                    "Member name already added",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                myViewModel.addMember(name.toString())
                binding.listNames.adapter?.notifyItemInserted(myViewModel._member_names.size)
            }
            name.clear()
        }

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_startPageFragment2_to_transactionsFragment2)
        }
    }

}