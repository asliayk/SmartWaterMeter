package com.example.smartwatermeter.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(
    supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val flist = ArrayList<Fragment>()
    private val ftitlelist = ArrayList<String>()

    override fun getCount(): Int {
        return flist.size
    }

    override fun getItem(position: Int): Fragment {
        return flist[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return ftitlelist[position]
    }

    fun addFragment(fragment: Fragment,title: String) {
        flist.add(fragment)
        ftitlelist.add(title)
    }
}