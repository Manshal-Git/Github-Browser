package com.manshal_khatri.githubbrowser.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.manshal_khatri.githubbrowser.FirebaseDataSource
import com.manshal_khatri.githubbrowser.model.GitRepository

@Dao
interface GitRepositoryDao {

    @Insert
     fun insert(repo : GitRepository)

    @Delete
     fun delete(repo : GitRepository)

    @Query("select * from GitRepository")
    fun getAllRepositories(): LiveData<List<GitRepository>>

    // New methods for Firestore
    fun insertFirestore(repo: List<GitRepository> , onComplete : (Boolean) -> Unit) {
        FirebaseDataSource("manshal-git").insert(repo,onComplete)
    }

    fun deleteFirestore(repo: GitRepository) {
        FirebaseDataSource("manshal-git").delete(repo)
    }

    fun getAllRepositoriesFirestore(callback: (List<GitRepository>) -> Unit) {
        FirebaseDataSource("manshal-git").getAllRepositories(callback)
    }

}