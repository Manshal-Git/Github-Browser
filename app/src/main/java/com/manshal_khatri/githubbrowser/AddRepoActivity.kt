package com.manshal_khatri.githubbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.manshal_khatri.githubbrowser.databinding.ActivityAddRepoBinding
import com.manshal_khatri.githubbrowser.databinding.ActivityHomeBinding
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.util.BaseActivity
import com.manshal_khatri.githubbrowser.viewmodel.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Math.random

class AddRepoActivity : BaseActivity() {
    lateinit var toolbar : Toolbar
    lateinit var etOwner : EditText
    lateinit var etRepo : EditText
    lateinit var btnAdd : AppCompatButton
    lateinit var binding: ActivityAddRepoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repo)
        // Initialization
        toolbar = findViewById(R.id.toolbarAddRepo)
        etOwner = findViewById(R.id.ETOwner)
        etRepo = findViewById(R.id.ETGitRepo)
        btnAdd = findViewById(R.id.btnAdd)

        setupActionBar(toolbar,"Add Repository")
        getHomeViewModel(this)


        btnAdd.setOnClickListener {
            lifecycleScope.launch{
//                viewModel.addRepo()
            }
        }
    }
}