package com.manshal_khatri.githubbrowser.viewmodel

import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.manshal_khatri.githubbrowser.util.Constants

class AddRepoViewModel : ViewModel() {

    fun fetchGitRepository(owner: String, repo: String) {
        val reqGitRepo = object :
            JsonObjectRequest(
                Method.GET,
                Constants.API_GET_REPO,
                null,
                Response.Listener {

                }, Response.ErrorListener {

                }){}
    }
}