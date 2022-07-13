package com.manshal_khatri.githubbrowser.widget

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.githubbrowser.activity.DetailActivity
import com.manshal_khatri.githubbrowser.adapter.GitRepositoryAdapter
import com.manshal_khatri.githubbrowser.databinding.ItemGitRepoBinding
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.util.Constants

class WidgetRepoChooserAdapter(private val list: List<GitRepository>, private val configActivity : CommitWidgetConfiguration) : RecyclerView.Adapter<WidgetRepoChooserAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemGitRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val description = binding.description
        val share = binding.ivShare
        val item = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGitRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gitRepo = list[position]
        with(holder){
            title.text = gitRepo.name
            description.text = if(gitRepo.description.length < 72) gitRepo.description
            else gitRepo.description.substring(0,72)+"..."
           share.isVisible = false
            item.setOnClickListener {
               configActivity.chooseBranch(gitRepo.owner,gitRepo.name)
            }
        }
    }
    override fun getItemCount(): Int = list.size
}