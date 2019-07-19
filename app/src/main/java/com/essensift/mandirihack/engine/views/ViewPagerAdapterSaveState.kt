package com.essensift.mandirihack.engine.views

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import java.util.*

class ViewPagerAdapterSaveState(private val fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getItemPosition(Object: Any): Int {
        val a = mFragmentList.indexOf(Object)
        return if (a > 0)
            a
        else
            PagerAdapter.POSITION_NONE
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    fun removeAllFragment() {
        mFragmentList.clear()
        mFragmentTitleList.clear()
    }

    fun removeAllExcept(fragment: Fragment) {
        //file probably has been deleted
        val iter: MutableIterator<Fragment> = mFragmentList.iterator()
        while (iter.hasNext()) {
            val i = iter.next()
            if (fragment != i) {
                val index = mFragmentList.indexOf(i)
                iter.remove()
                //mFragmentList.remove(i)
                mFragmentTitleList.removeAt(index)
            }
        }
    }

    fun removeAllExcept(fragment1: Fragment? = null, fragment2: Fragment? = null) {
        val childFragments = fm.fragments
        val iter: MutableIterator<Fragment> = mFragmentList.iterator()
        val fragmentTransaction = fm.beginTransaction()
        while (iter.hasNext()) {
            val i = iter.next()
            if (!(fragment1 == i || fragment2 == i)) {
                val index = mFragmentList.indexOf(i)
                fragmentTransaction.remove(i)
                iter.remove()
                //mFragmentList.remove(i)
                mFragmentTitleList.removeAt(index)
            }
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun removeAtIndex(index: Int) {
        mFragmentList.removeAt(index)
        mFragmentTitleList.removeAt(index)
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}