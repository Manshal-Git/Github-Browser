package com.manshal_khatri.githubbrowser.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.githubbrowser.model.Commit
import com.manshal_khatri.githubbrowser.util.Constants
import java.lang.Exception

open class CommitViewModel : ViewModel() {
    protected val _commits = MutableLiveData(mutableListOf<Commit>())
    val commits : LiveData<MutableList<Commit>>
        get() = _commits

    private fun refresh(){
        _commits.value =_commits.value
    }
    private fun addCommit(commit : Commit){
        _commits.value?.add(commit)
    }
    fun fetchCommits(context: Context, owner: String, repo: String,branchName : String){
        println(owner+repo)
        val queue = Volley.newRequestQueue(context)
        val reqCommits = object :
            JsonArrayRequest(
                Method.GET,
                Constants.API_GITHUB+"$owner/$repo/commits?sha=$branchName",
                null,
                Response.Listener {
                    println(it)
                    for(i in 0 until it.length()) {
                        val commitJsonObj = it.getJSONObject(i)
                        with(commitJsonObj){
                            addCommit(Commit(getJSONObject("commit").getJSONObject("committer").getString("name"),
                            avatar_url = try{if(this.getJSONObject("committer") !=null){
                                getJSONObject("committer").getString("avatar_url")
                            }else{
                                Constants.DEF_AVATAR
                            }}catch (e:Exception){
                                Constants.DEF_AVATAR
                                                 },getJSONObject("commit").getString("message"),
                                getJSONObject("commit").getJSONObject("committer").getString("date")
                            ))
                        }
                        refresh()
                    }
                }, Response.ErrorListener {
                    println("Volley Error : $it")
                }){}
        queue.add(reqCommits)
    }
}