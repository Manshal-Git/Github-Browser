package com.manshal_khatri.githubbrowser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repository(
    @PrimaryKey
    val id : String,
    val owner : String,
    val name : String
)
