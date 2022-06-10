package com.manshal_khatri.githubbrowser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.adapter.BranchAdapter
import com.manshal_khatri.githubbrowser.databinding.FragmentBranchBinding
import com.manshal_khatri.githubbrowser.model.Branch
import com.manshal_khatri.githubbrowser.viewmodel.BranchViewModel

private const val owner = "owner"
private const val repoName = "repo"

class BranchFragment : Fragment() {

    private var mOwner: String? = null
    private var mRepoName : String? = null
    lateinit var viewModel : BranchViewModel
    lateinit var binding: FragmentBranchBinding
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_branch, container, false)
        binding = FragmentBranchBinding.bind(view)
        viewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        binding.rvBranches.layoutManager = LinearLayoutManager(activity)

        viewModel.fetchBranches(requireContext(), mOwner!!, mRepoName!!)
        viewModel.branches.observe(viewLifecycleOwner, Observer {
            binding.rvBranches.adapter = BranchAdapter(it)
        })
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