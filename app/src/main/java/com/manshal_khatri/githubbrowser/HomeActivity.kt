package com.manshal_khatri.githubbrowser

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.githubbrowser.adapter.RepositoryAdapter
import com.manshal_khatri.githubbrowser.databinding.ActivityHomeBinding
import com.manshal_khatri.githubbrowser.util.BaseActivity
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {
    lateinit var toolbar : Toolbar
//    lateinit var viewModel : HomeViewModel
    lateinit var rvGitRepositories : RecyclerView
    lateinit var btnAddRepo : AppCompatButton
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        //Initialization
        toolbar = findViewById<Toolbar>(R.id.toolbarHome)
        btnAddRepo = findViewById(R.id.btnAddRepo)
        rvGitRepositories = findViewById(R.id.rvGitRepos)
        getHomeViewModel(this)
        setupActionBar(toolbar,"Github Browser")
        rvGitRepositories.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch{
            viewModel.getAllPrograms(this@HomeActivity)
        }

        viewModel.repositories.observe(this, Observer {
            if(it.isEmpty()){
                rvGitRepositories.visibility = GONE
                btnAddRepo.setOnClickListener{
                    startActivity(Intent(this,AddRepoActivity::class.java))
                }
            } else {
                rvGitRepositories.visibility = VISIBLE
                rvGitRepositories.adapter = RepositoryAdapter(it)
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_activity, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addRepo -> {
                startActivity(Intent(this,AddRepoActivity::class.java))
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}