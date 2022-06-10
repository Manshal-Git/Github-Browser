package com.manshal_khatri.githubbrowser.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.adapter.CommitAdapter
import com.manshal_khatri.githubbrowser.util.BaseActivity
import com.manshal_khatri.githubbrowser.viewmodel.CommitViewModel

class CommitsActivity : BaseActivity() {
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var commitViewModel: CommitViewModel
    lateinit var rvCommits : RecyclerView
    private var mOwner = "owner"
    private var mRepoName = "repo"
    private var mBranchName = "branchName"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commits)
        toolbar = findViewById(R.id.toolbarCommit)
        rvCommits = findViewById(R.id.rvCommits)
        if(intent!=null){
            mOwner = intent.getStringExtra("owner").toString()
            mBranchName = intent.getStringExtra("branchName").toString()
            mRepoName = intent.getStringExtra("repoName").toString()
        }
        setupActionBar(toolbar,"Commits\n$mBranchName",true)
        commitViewModel = ViewModelProvider(this).get(CommitViewModel::class.java)
        rvCommits.layoutManager = LinearLayoutManager(this)

        commitViewModel.fetchCommits(this, mOwner, mRepoName, mBranchName)
        commitViewModel.commits.observe(this, Observer {
           rvCommits.adapter = CommitAdapter(it)
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}