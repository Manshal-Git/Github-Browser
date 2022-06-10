package com.manshal_khatri.githubbrowser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.githubbrowser.databinding.ItemCommitBinding
import com.manshal_khatri.githubbrowser.model.Commit
import com.manshal_khatri.githubbrowser.util.GlideUtil

class CommitAdapter (private val list: List<Commit>) : RecyclerView.Adapter<CommitAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemCommitBinding) : RecyclerView.ViewHolder(binding.root) {
        val date = binding.tvDate
        val msg = binding.tvMessage
        val user = binding.tvCommitter
        val avatar = binding.ivAvatar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommitBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder){
            msg.text =  item.message
            user.text = item.owner
            date.text = item.date.substringBefore("T")
            GlideUtil(avatar.context).setRoundImage(item.avatar_url,avatar)
        }
    }

    override fun getItemCount(): Int = list.size

}