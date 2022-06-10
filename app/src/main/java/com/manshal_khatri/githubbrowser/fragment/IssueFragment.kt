package com.manshal_khatri.githubbrowser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.adapter.IssueAdapter
import com.manshal_khatri.githubbrowser.databinding.FragmentIssueBinding
import com.manshal_khatri.githubbrowser.issuesCount
import com.manshal_khatri.githubbrowser.viewmodel.IssueViewModel

private const val owner = "owner"
private const val repoName = "repo"
class IssueFragment : Fragment() {
    private var mOwner: String? = null
    private var mRepoName : String? = null
    lateinit var viewModel : IssueViewModel
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
        val view = inflater.inflate(R.layout.fragment_issue, container, false)
        binding = FragmentIssueBinding.bind(view)
        viewModel = ViewModelProvider(this).get(IssueViewModel::class.java)
        binding.rvIssues.layoutManager = LinearLayoutManager(activity)

       viewModel.fetchIssues(requireContext(),mOwner!!,mRepoName!!)
        viewModel.issues.observe(viewLifecycleOwner, Observer {
            binding.rvIssues.adapter = IssueAdapter(it)
            issuesCount.value = it.size
            issuesCount.value = issuesCount.value
        })
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(owner: String, repoName: String) =
            IssueFragment().apply {
                arguments = Bundle().apply {
                    putString(com.manshal_khatri.githubbrowser.fragment.owner, owner)
                    putString(com.manshal_khatri.githubbrowser.fragment.repoName, repoName)
                }
            }
    }
}