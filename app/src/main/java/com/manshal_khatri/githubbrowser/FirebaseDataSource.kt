package com.manshal_khatri.githubbrowser
import com.google.firebase.firestore.FirebaseFirestore
import com.manshal_khatri.githubbrowser.model.GitRepository

class FirebaseDataSource {

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun insert(repo: GitRepository) {
        db.collection("repositories")
            .add(repo.toMap())
    }

    fun delete(repo: GitRepository) {
        repo.id.let { id ->
            db.collection("repositories")
                .document(id)
                .delete()
        }
    }

    fun getAllRepositories(callback: (List<GitRepository>) -> Unit) {
        db.collection("repositories")
            .get()
            .addOnSuccessListener { result ->
                val repositories = mutableListOf<GitRepository>()
                for (document in result) {
                    val repository = document.toObject(GitRepository::class.java)
                    repositories.add(repository)
                }
                callback(repositories)
            }
            .addOnFailureListener { exception ->
                // Handle the exception here if needed
            }
    }
}
