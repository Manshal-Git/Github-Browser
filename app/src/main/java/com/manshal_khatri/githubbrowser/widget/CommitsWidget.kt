package com.manshal_khatri.githubbrowser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.FrameLayout
import android.widget.RemoteViews
import android.widget.StackView
import android.widget.TextView
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.activity.CommitsActivity

/**
 * Implementation of App Widget functionality.
 */
class CommitsWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Just Started
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
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object

//    views.setTextViewText(R.id.appwidget_text, widgetText)
//    val intent = Intent(context,CommitsActivity::class.java)
//    val pendingIntent = PendingIntent.getActivity(context,0,intent,0)
//    views.setOnClickPendingIntent(R.id.widgetLayout,pendingIntent)

    /* pendingIntent.putExtra("branchName",branch.name)
     pendingIntent.putExtra("owner",owner)
     pendingIntent.putExtra("repoName",repoName)
     pendingIntent.describeContents()*/

    val intent = Intent(context, CommitWidgetService::class.java).apply {
        // Add the widget ID to the intent extras.
        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
    }
    val views = RemoteViews(context.packageName, R.layout.commits_widget)
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
    appWidgetManager.updateAppWidget(appWidgetId, views)
}