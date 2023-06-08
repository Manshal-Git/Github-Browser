package com.manshal_khatri.githubbrowser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.manshal_khatri.githubbrowser.model.GitRepository

class FirebaseDataSource (val userName : String){

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun insert(repo: List<GitRepository>, onComplete : (Boolean) -> Unit) {


        db.collection("users")
            .document(userName)
            .collection("repositories")
            .apply {
                var x = 0
                for (gitRepository in repo) {
                    document(gitRepository.name!!).set(gitRepository, SetOptions.merge())
                        .addOnSuccessListener {
                            println(x)
                            x++
                            if(x == repo.size)
                                onComplete(true)
                        }.addOnFailureListener {
                            onComplete(false)
                        }
                }
            }

    }

    fun delete(repo: GitRepository) {
        repo.id.let { id ->
            db.collection("repositories")
                .document(id)
                .delete()
        }
    }

    fun getAllRepositories(callback: (List<GitRepository>) -> Unit) {

        db.collection("users")
            .document(userName)
            .collection("repositories")
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
