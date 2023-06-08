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
    var id : String = "",
    var owner : String = "",
    var name : String = "",
    var description : String = "",
    var url : String = ""
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
