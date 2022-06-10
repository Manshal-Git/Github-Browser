package com.manshal_khatri.githubbrowser.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class GitRepository(
    @PrimaryKey
    val id : String,
    val owner : String,
    val name : String,
    val description : String,
    val url : String
):Serializable
