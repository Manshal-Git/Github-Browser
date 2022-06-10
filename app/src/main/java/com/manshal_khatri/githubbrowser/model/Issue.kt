package com.manshal_khatri.githubbrowser.model

data class Issue(
    val id : String,
    val issueCreator : String,
    val issue : String,
    val avatar_url: String,
    val state : String = "open"
)
