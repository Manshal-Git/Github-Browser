package com.manshal_khatri.githubbrowser.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.manshal_khatri.githubbrowser.fragment.BranchFragment
import com.manshal_khatri.githubbrowser.fragment.IssueFragment

class ViewPagerAdapter(private val context: Context, fm: FragmentManager, val owner:String, val repoName : String) :
        FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return  if(position==1) IssueFragment()
            else BranchFragment.newInstance(owner,repoName)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if(position==0) "Branches"
            else "Issues"
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }

}