package com.manshal_khatri.githubbrowser.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddRepoViewModel : HomeViewModel() {


    fun fetchGitRepository(context: Context, owner: String,repo: String) {
        val queue = Volley.newRequestQueue(context)
        val reqGitRepo = object :
            JsonObjectRequest(
                Method.GET,
                Constants.API_GITHUB+"$owner/$repo",
                null,
                Response.Listener {
                    setUpDataBase(context)
                    val gitRepo = GitRepository(
                        it.getInt("id").toString(),
                        owner,
                        repo,
                        it.getString("description"),
                        it.getString("html_url"))
                    CoroutineScope(Dispatchers.IO).launch{
                        addRepo(gitRepo)
                    }
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                }, Response.ErrorListener {
                    Toast.makeText(context, "Repository Not Found", Toast.LENGTH_SHORT).show()
                }){}
        queue.add(reqGitRepo)
    }
}