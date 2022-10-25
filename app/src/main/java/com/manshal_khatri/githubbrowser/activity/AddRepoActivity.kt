package com.manshal_khatri.githubbrowser.activity

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.manshal_khatri.githubbrowser.NotificationService
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.databinding.ActivityAddRepoBinding
import com.manshal_khatri.githubbrowser.util.BaseActivity
import com.manshal_khatri.githubbrowser.viewmodel.AddRepoViewModel

class AddRepoActivity : BaseActivity() {
    lateinit var toolbar : Toolbar
    lateinit var etOwner : EditText
    lateinit var etRepo : EditText
    lateinit var btnAdd : AppCompatButton
    lateinit var binding: ActivityAddRepoBinding
    lateinit var addRepoViewModel: AddRepoViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repo)
        // Initialization
        toolbar = findViewById(R.id.toolbarAddRepo)
        etOwner = findViewById(R.id.ETOwner)
        etRepo = findViewById(R.id.ETGitRepo)
        btnAdd = findViewById(R.id.btnAdd)
        addRepoViewModel = ViewModelProvider(this).get(AddRepoViewModel::class.java)
        setupActionBar(toolbar,"Add Repository")
        val notificationService = NotificationService(applicationContext)
        getHomeViewModel(this)


        btnAdd.setOnClickListener {
            val owner = etOwner.text.toString()
            val repo = etRepo.text.toString()
            addRepoViewModel.fetchGitRepository(this@AddRepoActivity,owner,repo)
//            notificationService.showNotification(owner,"Repository Added")
            onBackPressed()
        }
    }
}