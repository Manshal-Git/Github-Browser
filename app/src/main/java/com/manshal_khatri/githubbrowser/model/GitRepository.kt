package com.manshal_khatri.githubbrowser.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class GitRepository(
    @PrimaryKey
    var id : String = "",
    var owner : String = "",
    var name : String = "",
    var description : String = "",
    var url : String = ""
):Serializable
