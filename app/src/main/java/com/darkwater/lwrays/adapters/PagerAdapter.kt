package com.darkwater.lwrays.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.darkwater.lwrays.fragments.CommunityListFragment
import com.darkwater.lwrays.fragments.RaidFragment

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RaidFragment()
            1 -> CommunityListFragment()
            else -> Fragment()
        }
    }

}