package com.darkwater.lwrays.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darkwater.lwrays.MainScreenActivity
import com.darkwater.lwrays.R
import com.darkwater.lwrays.ResultContract
import com.darkwater.lwrays.adapters.CommunityListAdapter

class CommunityListFragment : Fragment() {

    private val contract = ResultContract()
    private val launcher = registerForActivityResult(contract) { result ->
        requireActivity()
            .supportFragmentManager
            .setFragmentResult("thread", bundleOf("id" to result))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_community_list, container, false)
        return setup(view)
    }

    private fun setup(view: View): View {
        val client = (requireActivity() as MainScreenActivity).client
        val recyclerView = view.findViewById<RecyclerView>(R.id.communityRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        client.loadCircleList { response ->
            if (response.list.isEmpty()) {
                Toast.makeText(requireActivity(), "Сообществ нет", Toast.LENGTH_SHORT).show()
            }
            val adapter = CommunityListAdapter(response.list)
            adapter.addClickListener { position ->
                launcher.launch(adapter.circles[position])
                requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
            }
            recyclerView.adapter = adapter
        }
        return view
    }
}