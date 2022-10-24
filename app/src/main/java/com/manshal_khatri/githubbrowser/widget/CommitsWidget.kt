package com.manshal_khatri.githubbrowser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.activity.HomeActivity

/**
 * Implementation of App Widget functionality.
 */
class CommitsWidget : AppWidgetProvider() {
    lateinit var mContext: Context
    lateinit var mAppWidgetManager: AppWidgetManager
    var mAppWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Just Started
        mContext = context
        mAppWidgetManager = appWidgetManager

        // setup which data you want to show
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            mAppWidgetId = appWidgetId
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.list_view)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    override fun onReceive(context: Context,intent: Intent){

        if (intent.action=="SyncWidget") {
            Toast.makeText(context, "${intent.action}", Toast.LENGTH_SHORT).show()
            val awm = AppWidgetManager.getInstance(context)//mAppWidgetManager
            val id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
//            awm.notifyAppWidgetViewDataChanged(id,R.id.list_view)
//            updateAppWidget(context,awm,id)
            refreshWidgetData(awm,context,id)
        } else {
            Toast.makeText(context, "${intent.action}", Toast.LENGTH_SHORT).show()
            val awm = AppWidgetManager.getInstance(context)
            val id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
//            awm.notifyAppWidgetViewDataChanged(id,R.id.list_view)
//            updateAppWidget(context,awm,id)
            refreshWidgetData(awm,context,id)
        }
    }

    fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        // Construct the RemoteViews object
//        val i = Intent(context, HomeActivity::class.java)
//        val pi = PendingIntent.getActivity(context,0,i,0)

//        var  branch : Int? = null
//        branch = selectedBranchId

        val views = RemoteViews(context.packageName,R.layout.commits_widget)
//        views.setOnClickPendingIntent(R.id.empty_view,pi)
        views.setOnClickPendingIntent(R.id.ivRefresh,getSelfPendingIntent(context,"SyncWidget",appWidgetId))
        // setup which data you want to show -------------------------------------

        val intent = Intent(context, CommitWidgetService::class.java).apply {
            // Add the widget ID to the intent extras.
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
        }

        // Set up the RemoteViews object to use a RemoteViews adapter.
        // This adapter connects to a RemoteViewsService through the
        // specified intent.
        // This is how you populate the data.
        views.setRemoteAdapter(R.id.list_view, intent)

        // The empty view is displayed when the collection has no items.
        // It should be in the same layout used to instantiate the
        // RemoteViews object.
        views.setEmptyView(R.id.list_view, R.id.empty_view)

        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.list_view)
//        awm.notifyAppWidgetViewDataChanged(id,R.id.list_view)
        appWidgetManager.updateAppWidget(appWidgetId, views)

    }
    fun getSelfPendingIntent(context: Context, action: String,appWidgetId: Int): PendingIntent? {
//        val intent = Intent(context,CommitsWidget::class.java)

        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.component = ComponentName(context,CommitsWidget::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
        return PendingIntent.getBroadcast(context,0,intent,0)
    }
    fun refreshByBroadcast(context: Context) {
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.component = ComponentName(context,CommitsWidget::class.java)
        context.sendBroadcast(intent)
    }
    fun refreshWidgetData(appWidgetManager: AppWidgetManager,context: Context,appWidgetId: Int){
        val views = RemoteViews(context.packageName,R.layout.commits_widget)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.list_view)
//        awm.notifyAppWidgetViewDataChanged(id,R.id.list_view)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}


