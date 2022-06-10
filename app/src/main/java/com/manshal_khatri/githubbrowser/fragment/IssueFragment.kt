package com.manshal_khatri.githubbrowser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.databinding.FragmentBranchBinding
import com.manshal_khatri.githubbrowser.databinding.FragmentIssueBinding

private const val owner = "owner"
private const val repoName = "repo"
class IssueFragment : Fragment() {
    private var mOwner: String? = null
    private var mRepoName : String? = null
    lateinit var binding: FragmentIssueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mOwner = it.getString(owner)
            mRepoName = it.getString(repoName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_branch, container, false)
        binding = FragmentIssueBinding.bind(view)

        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(owner: String, repoName: String) =
            BranchFragment().apply {
                arguments = Bundle().apply {
                    putString(com.manshal_khatri.githubbrowser.fragment.owner, owner)
                    putString(com.manshal_khatri.githubbrowser.fragment.repoName, repoName)
                }
            }
    }
}