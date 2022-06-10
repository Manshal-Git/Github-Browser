package com.manshal_khatri.githubbrowser.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.githubbrowser.model.Branch
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BranchViewModel : ViewModel() {
    private val _branches = MutableLiveData(mutableListOf<Branch>())
    val branches : LiveData<MutableList<Branch>>
        get() = _branches

    private fun refresh(){
        _branches.value =_branches.value
    }
    private fun addBranch(branch : Branch){
        _branches.value?.add(branch)
    }
    fun fetchBranches(context: Context, owner: String, repo: String){
        println(owner+repo)
        val queue = Volley.newRequestQueue(context)
        val reqBranches = object :
            JsonArrayRequest(
                Method.GET,
                Constants.API_GITHUB+"$owner/$repo/branches",
                null,
                Response.Listener {
                    println(it)
                    for(i in 0 until it.length()) {
                        val branchJsonObj = it.getJSONObject(i)
                        addBranch(Branch(
                            i.toString(),branchJsonObj.getString("name")
                        ))
                        refresh()
                    }

                }, Response.ErrorListener {
                    println("Volley Error : $it")
                }){}
        queue.add(reqBranches)
    }
}
