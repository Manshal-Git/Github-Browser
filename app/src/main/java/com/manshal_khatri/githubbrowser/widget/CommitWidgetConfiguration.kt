package com.manshal_khatri.githubbrowser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.activity.HomeActivity
import com.manshal_khatri.githubbrowser.databinding.ActivityCommitWidgetConfigurationBinding
import com.manshal_khatri.githubbrowser.model.GitRepository
import com.manshal_khatri.githubbrowser.room.GitRepositoryDB
import com.manshal_khatri.githubbrowser.util.Constants
import com.manshal_khatri.githubbrowser.viewmodel.CommitViewModel
import com.manshal_khatri.githubbrowser.viewmodel.HomeViewModel
import java.util.Collections.addAll

class CommitWidgetConfiguration : AppCompatActivity() {

     val sp = "sp"
    var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var gitRepoDB : GitRepositoryDB
    lateinit var binding: ActivityCommitWidgetConfigurationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommitWidgetConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val configIntent = intent
        if(intent.extras!=null){
            widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID)
        }
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId)
        setResult(RESULT_CANCELED,resultValue)
        if(widgetId==AppWidgetManager.INVALID_APPWIDGET_ID){
            finish()
        }
        // set up to show options for selecting a branch for widget
        val list = mutableListOf<GitRepository>()
        gitRepoDB = GitRepositoryDB.getDatabase(this)
        gitRepoDB.gitRepoDao().getAllRepositories().observe(this, Observer{
           if(it.isNotEmpty()){
               list.addAll(it)
           }
        })
        val options = binding.rvGitReposWidget
        options.layoutManager = LinearLayoutManager(this)
        options.adapter = WidgetRepoChooserAdapter(list,this)

    }
    fun chooseBranch(owner : String,repo : String){
        val awm = AppWidgetManager.getInstance(this)
        val i = Intent(this, HomeActivity::class.java)
        val pi = PendingIntent.getActivity(this,0,i,0)

      /*  val vm = ViewModelProvider(this)[CommitViewModel::class.java]
        vm.
        fetchCommits(this,owner,repo,"master")*/

        val views = RemoteViews(this.packageName,R.layout.commits_widget)
        views.setOnClickPendingIntent(R.id.empty_view,pi)
        // setup which data you want to show

//        awm.updateAppWidget(widgetId,views)   //def place

        val prefs = getSharedPreferences(sp, MODE_PRIVATE)
        prefs.edit().putString("spData",repo).apply()

        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId)
        setResult(RESULT_OK,resultValue)
        val dataIntent = Intent(this,CommitWidgetService::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId)
            data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
        }
        views.setRemoteAdapter(R.id.list_view,dataIntent)
        views.setEmptyView(R.id.list_view, R.id.empty_view)

        awm.updateAppWidget(widgetId,views)  // new place
        awm.notifyAppWidgetViewDataChanged(widgetId,R.id.list_view)
        onBackPressed()
    }
}