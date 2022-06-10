package com.manshal_khatri.githubbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.manshal_khatri.githubbrowser.adapter.ViewPagerAdapter
import com.manshal_khatri.githubbrowser.databinding.ActivityDetailBinding
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.util.BaseActivity
import com.manshal_khatri.githubbrowser.util.Constants
import kotlinx.coroutines.launch

class DetailActivity : BaseActivity() {
    lateinit var binding : ActivityDetailBinding
    lateinit var gitRepo : GitRepository
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var tvTitle : TextView
    lateinit var tvDescription : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        // Initialization
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        toolbar = findViewById(R.id.toolbarDetail)
        tvTitle = findViewById(R.id.tvTitle)
        tvDescription = findViewById(R.id.tvDescription)

        setupActionBar(toolbar,"Details",true)
        if(intent!=null){
            gitRepo = intent.getSerializableExtra(Constants.INTENT_ARGS_GIT_REPO) as GitRepository
            tvTitle.text = gitRepo.name
            tvDescription.text = gitRepo.description
            viewPager.adapter = ViewPagerAdapter(this,supportFragmentManager,gitRepo.owner,gitRepo.name)
            tabLayout.setupWithViewPager(viewPager)
        }
        getHomeViewModel(this)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.deleteRepo -> {
                lifecycleScope.launch{
                    viewModel.removeRepo(gitRepo)
                }
                true
            }
            R.id.viewInBrowser -> {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))

                startActivity(intent)
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}