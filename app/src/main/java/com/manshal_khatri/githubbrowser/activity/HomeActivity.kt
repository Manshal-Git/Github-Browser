package com.manshal_khatri.githubbrowser.activity

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.adapter.GitRepositoryAdapter
import com.manshal_khatri.githubbrowser.databinding.ActivityHomeBinding
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.util.BaseActivity
import com.manshal_khatri.githubbrowser.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : BaseActivity() {
    lateinit var toolbar : Toolbar
//    lateinit var viewModel : HomeViewModel
    lateinit var rvGitRepositories : RecyclerView
    lateinit var btnAddRepo : AppCompatButton
    lateinit var binding: ActivityHomeBinding
    var hide = false
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
            if(it.isEmpty() && hide){
                rvGitRepositories.visibility = GONE
                btnAddRepo.setOnClickListener{
                    startActivity(Intent(this, AddRepoActivity::class.java))
                }
            } else {
                hide = true
                rvGitRepositories.visibility = VISIBLE
                rvGitRepositories.adapter = GitRepositoryAdapter(it)
            }
        })
        val data : Uri? = intent?.data
        if (data!=null) {
            addExternalRepo(data)
        }
    }


    fun addExternalRepo(data : Uri){

            val params : List<String> = data.pathSegments
            if(params.size==2){
                val repo =params[params.size-1]
                val owner = params[params.size-2]

                val queue = Volley.newRequestQueue(this)
                val reqGitRepo = object :
                    JsonObjectRequest(
                        Method.GET,
                        Constants.API_GITHUB+"$owner/$repo",
                        null,
                        Response.Listener {
                            viewModel.setUpDataBase(this)
                            val gitRepo = GitRepository(
                                it.getInt("id").toString(),
                                owner,
                                repo,
                                it.getString("description"),
                                it.getString("html_url"))
                            CoroutineScope(Dispatchers.IO).launch{
                              try {
                                  viewModel.addRepo(gitRepo)
                                  withContext(Main){
                                      Toast.makeText(this@HomeActivity, "Added", Toast.LENGTH_SHORT).show()
                                  }
                              }catch (e : Exception){
                                  println(e)
                                  when(e){
                                      is SQLiteConstraintException -> withContext(Main){
                                          Toast.makeText(
                                              this@HomeActivity,
                                              "You Already have this Repository",
                                              Toast.LENGTH_SHORT
                                          ).show()
                                      }
                                  }
                              }
                            }
                        }, Response.ErrorListener {
                            Toast.makeText(this, "Repository Not Found", Toast.LENGTH_SHORT).show()
                        }){}
                queue.add(reqGitRepo)
            }else{
                Toast.makeText(this, "Invalid Repository", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_activity, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addRepo -> {
                startActivity(Intent(this, AddRepoActivity::class.java))
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onResume() {
        viewModel.getAllPrograms(this@HomeActivity)
        super.onResume()
    }

}