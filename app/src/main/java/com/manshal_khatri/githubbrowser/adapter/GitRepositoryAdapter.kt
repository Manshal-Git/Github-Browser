package com.manshal_khatri.githubbrowser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.githubbrowser.activity.DetailActivity
import com.manshal_khatri.githubbrowser.databinding.ItemGitRepoBinding
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.util.Constants

class GitRepositoryAdapter(private val list: List<GitRepository>) : RecyclerView.Adapter<GitRepositoryAdapter.ViewHolder>() {
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
            share.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT,"${gitRepo.url}")
                it.context.startActivity(intent)
            }
            item.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(Constants.INTENT_ARGS_GIT_REPO,gitRepo)
                it.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = list.size

}