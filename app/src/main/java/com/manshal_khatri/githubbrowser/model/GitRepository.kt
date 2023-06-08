package com.manshal_khatri.githubbrowser.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable
@IgnoreExtraProperties
@Entity
data class GitRepository(
    @PrimaryKey
    val id : String,
    val owner : String,
    val name : String,
    val description : String,
    val url : String
):Serializable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "owner" to owner,
            "name" to name,
            "description" to description,
            "url" to url
        )
    }
}
