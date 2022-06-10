package com.manshal_khatri.githubbrowser.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.room.GitRepositoryDB
import kotlinx.coroutines.coroutineScope

open class HomeViewModel : ViewModel() {
    private lateinit var gitRepoDB : GitRepositoryDB
    private val _repositories = MutableLiveData(mutableListOf<GitRepository>())
    val repositories : LiveData<MutableList<GitRepository>>
    get() = _repositories

    private fun refresh(){
        _repositories.value =_repositories.value
    }
    suspend fun addRepo(repo : GitRepository){
        gitRepoDB.gitRepoDao().insert(repo)
        _repositories.value?.add(repo)
        refresh()
    }
    private fun addAll(list : List<GitRepository>){
        removeAll()
        _repositories.value?.addAll(list)
        refresh()
    }
    suspend fun removeRepo(repo: GitRepository){
         gitRepoDB.gitRepoDao().delete(repo)
        _repositories.value?.remove(repo)
        refresh()
    }

     private fun removeAll() = _repositories.value?.clear()

    fun setUpDataBase(context : Context){
        gitRepoDB = GitRepositoryDB.getDatabase(context)
    }

    suspend fun getAllPrograms(viewLifecycleOwner: LifecycleOwner){
        gitRepoDB.gitRepoDao().getAllRepositories().observe(viewLifecycleOwner, Observer{
          addAll(it)
        })
    }
}