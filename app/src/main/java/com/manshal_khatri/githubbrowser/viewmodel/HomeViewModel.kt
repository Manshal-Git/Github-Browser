package com.manshal_khatri.githubbrowser.viewmodel

import android.content.Context
import android.widget.Toast
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
     fun addRepo(repo : GitRepository){
        gitRepoDB.gitRepoDao().insert(repo)
    }
    private fun addAll(list : List<GitRepository>){
        removeAll()
        _repositories.value?.addAll(list)
        refresh()
    }
    fun removeRepo(repo: GitRepository){
         gitRepoDB.gitRepoDao().delete(repo)
    }

     private fun removeAll() = _repositories.value?.clear()

    fun setUpDataBase(context : Context){
        gitRepoDB = GitRepositoryDB.getDatabase(context)
    }

     fun getAllRepositories(viewLifecycleOwner: LifecycleOwner){
        gitRepoDB.gitRepoDao().getAllRepositories().observe(viewLifecycleOwner, Observer{
          addAll(it)
        })
    }

    fun restoreRepositories() {
        gitRepoDB.gitRepoDao().getAllRepositoriesFirestore {
            addAll(it)
        }
    }

    fun backupRepositories(onComplete : (Boolean) -> Unit) {
        _repositories.value?.let {
            gitRepoDB.gitRepoDao().insertFirestore(it,onComplete)
        }
    }
}