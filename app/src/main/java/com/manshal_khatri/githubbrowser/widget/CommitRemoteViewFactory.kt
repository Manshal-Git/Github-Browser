package com.manshal_khatri.githubbrowser.widget

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.model.Commit
import com.manshal_khatri.githubbrowser.util.Constants
import com.manshal_khatri.githubbrowser.viewmodel.CommitViewModel

class CommitRemoteViewFactory(
    private val context: Context,
    val intent: Intent
) : RemoteViewsService.RemoteViewsFactory, CommitViewModel() {

    private lateinit var widgetItems: List<Commit>
    private var appWidgetId : Int = 999
    override fun onCreate() {
        // In onCreate() you setup any connections / cursors to your data
        // source. Heavy lifting, for example downloading or creating content
        // etc, should be deferred to onDataSetChanged() or getViewAt(). Taking
        // more than 20 seconds in this call will result in an ANR.
        fetchCommits(context,"manshal-git","pikadex","master")
        widgetItems = commits.value!!
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
    }

    override fun onDataSetChanged() {
        fetchCommits(context,"manshal-git","pikadex","master")
        widgetItems = commits.value!!

//        widgetItems = List(100) { index -> Commit("$index Manshal",Constants.DEF_AVATAR,"Hy","121236") }

    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        return widgetItems.size
    }


    override fun getViewAt(position: Int): RemoteViews {
        // Construct a remote views item based on the widget item XML file,
        // and set the text based on the position.

        return RemoteViews(context.packageName, R.layout.item_widget_commit).apply {
            setTextViewText(R.id.tvCommitter, widgetItems[position].owner)
            setTextViewText(R.id.tvDate, widgetItems[position].date)
            setTextViewText(R.id.tvMessage, widgetItems[position].message)
            setImageViewResource(R.id.ivAvatar,R.drawable.ic_add)

        }
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.item_widget_commit).apply {
            setTextViewText(R.id.empty_view,"Loading file")
        }
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true

    }

}