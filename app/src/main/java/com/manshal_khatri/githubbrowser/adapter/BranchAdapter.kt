package com.manshal_khatri.githubbrowser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.githubbrowser.CommitsActivity
import com.manshal_khatri.githubbrowser.databinding.ItemBranchBinding
import com.manshal_khatri.githubbrowser.databinding.ItemGitRepoBinding
import com.manshal_khatri.githubbrowser.model.Branch
import com.manshal_khatri.githubbrowser.model.GitRepository

class BranchAdapter(private val list: List<Branch>,val owner:String,val repoName:String) : RecyclerView.Adapter<BranchAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemBranchBinding) : RecyclerView.ViewHolder(binding.root) {
        val branchName = binding.tvBranch
        val item = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBranchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val branch = list[position]
        with(holder){
            branchName.text = branch.name
            item.setOnClickListener {
                val intent = Intent(it.context,CommitsActivity::class.java)
                intent.putExtra("branchName",branch.name)
                intent.putExtra("owner",owner)
                intent.putExtra("repoName",repoName)
                it.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = list.size


}