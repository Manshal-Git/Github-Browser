package com.manshal_khatri.githubbrowser.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.githubbrowser.model.Branch
import com.manshal_khatri.githubbrowser.model.Issue
import com.manshal_khatri.githubbrowser.util.Constants

class IssueViewModel : ViewModel() {
    private val _issues = MutableLiveData(mutableListOf<Issue>())
    val issues : LiveData<MutableList<Issue>>
        get() = _issues

    private fun refresh(){
        _issues.value =_issues.value
    }
    private fun addIssue(issue : Issue){
        _issues.value?.add(issue)
    }
    fun fetchIssues(context: Context, owner: String, repo: String){
        val queue = Volley.newRequestQueue(context)
        val reqIssues = object :
            JsonArrayRequest(
                Method.GET,
                Constants.API_GET_ISSUES+"$owner/$repo/issues?state=open",
                null,
                Response.Listener {
                    println(it)
                    for(i in 0 until it.length()) {
                        val issueJsonObj = it.getJSONObject(i)
                        addIssue(Issue(
                            i.toString(),
                            issueJsonObj.getJSONObject("user").getString("login"),
                            issueJsonObj.getString("title"),
                            issueJsonObj.getJSONObject("user").getString("avatar_url")
                        ))
                        refresh()
                    }

                }, Response.ErrorListener {
                    println("Volley Error : $it")
                }){}
        queue.add(reqIssues)
    }
}