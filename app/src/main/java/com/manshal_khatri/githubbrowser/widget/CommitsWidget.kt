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

class CommitsWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Just Started
        // setup which data you want to show
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    override fun onReceive(context: Context,intent: Intent){
            Toast.makeText(context, "${intent.action}", Toast.LENGTH_SHORT).show()
            val awm = AppWidgetManager.getInstance(context)
            val id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
            awm.notifyAppWidgetViewDataChanged(id,R.id.list_view)
            updateAppWidget(context,awm,id)
    }

    fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        // Construct the RemoteViews object

        val views = RemoteViews(context.packageName,R.layout.commits_widget)

        val rintent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        rintent.component = ComponentName(context,CommitsWidget::class.java)
        rintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        rintent.data = Uri.parse(rintent.toUri(Intent.URI_INTENT_SCHEME))
        val pi = PendingIntent.getBroadcast(context,0,rintent,0)
        views.setOnClickPendingIntent(R.id.ivRefresh,pi)
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
//    fun getSelfPendingIntent(context: Context, action: String,appWidgetId: Int): PendingIntent? {
////        val intent = Intent(context,CommitsWidget::class.java)
//
//    }
    /*fun refreshByBroadcast(context: Context) {
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.component = ComponentName(context,CommitsWidget::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
        context.sendBroadcast(intent)
    }*/
    fun refreshWidgetData(appWidgetManager: AppWidgetManager,context: Context,appWidgetId: Int){
        val views = RemoteViews(context.packageName,R.layout.commits_widget)

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.list_view)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

}


