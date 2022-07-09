package com.manshal_khatri.githubbrowser.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.manshal_khatri.githubbrowser.model.GitRepository

@Dao
interface GitRepositoryDao {

    @Insert
     fun insert(repo : GitRepository)

    @Delete
     fun delete(repo : GitRepository)

    @Query("select * from GitRepository")
    fun getAllRepositories(): LiveData<List<GitRepository>>
}