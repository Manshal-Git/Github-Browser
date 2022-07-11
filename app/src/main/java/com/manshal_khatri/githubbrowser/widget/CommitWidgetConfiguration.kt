package com.manshal_khatri.githubbrowser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.activity.HomeActivity
import com.manshal_khatri.githubbrowser.viewmodel.HomeViewModel

class CommitWidgetConfiguration : AppCompatActivity() {

     val sp = "sp"
    var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commit_widget_configuration)

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
    }
    fun chooseBranch(view : View){
        val awm = AppWidgetManager.getInstance(this)
        val i = Intent(this,HomeActivity::class.java)
        val pi = PendingIntent.getActivity(this,0,i,0)

        var  branch : Int? = null
//        branch = selectedBranchId

        val views = RemoteViews(this.packageName,R.layout.commits_widget)
        views.setOnClickPendingIntent(R.id.empty_view,pi)
        // setup which data you want to show

        awm.updateAppWidget(widgetId,views)

        val prefs = getSharedPreferences(sp, MODE_PRIVATE)
        if (branch != null) {
            prefs.edit().putInt("spData",branch).apply()
        }
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId)
        setResult(RESULT_OK,resultValue)
    }
}