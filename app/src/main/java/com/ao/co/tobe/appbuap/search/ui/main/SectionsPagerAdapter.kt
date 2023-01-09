package com.ao.co.tobe.appbuap.search.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ao.co.tobe.appbuap.R


private val TAB_TITLES = arrayOf(
    R.string.tab_text_2,
    R.string.tab_text_1
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {

    val fragmentArrayList = ArrayList<Fragment>()
    val framentTitle = ArrayList<String>()

    override fun getCount(): Int {
        return fragmentArrayList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArrayList.get(position)
    }

    fun AddFragment(fragment: Fragment?, title: String?) {
        fragmentArrayList.add(fragment!!)
        framentTitle.add(title!!)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return framentTitle.get(position)
    }
}
