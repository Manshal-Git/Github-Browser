package com.manshal_khatri.githubbrowser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.githubbrowser.databinding.ItemIssueBinding
import com.manshal_khatri.githubbrowser.model.Issue

class IssueAdapter(private val list: List<Issue>) : RecyclerView.Adapter<IssueAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemIssueBinding) : RecyclerView.ViewHolder(binding.root) {
        val issue = binding.tvIssue
        val issueCreator = binding.tvIssueCreator
        val avatar = binding.ivAvatar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemIssueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder){
            issue.text = item.title
            issueCreator.text = item.owner
        }
    }

    override fun getItemCount(): Int = list.size

}