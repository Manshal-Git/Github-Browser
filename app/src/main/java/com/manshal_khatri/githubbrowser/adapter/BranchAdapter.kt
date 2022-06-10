package com.manshal_khatri.githubbrowser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.githubbrowser.databinding.ItemBranchBinding
import com.manshal_khatri.githubbrowser.databinding.ItemGitRepoBinding
import com.manshal_khatri.githubbrowser.model.Branch
import com.manshal_khatri.githubbrowser.model.GitRepository

class BranchAdapter(private val list: List<Branch>) : RecyclerView.Adapter<BranchAdapter.ViewHolder>() {
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
                //TODO open commit frag
            }
        }
    }

    override fun getItemCount(): Int = list.size


}