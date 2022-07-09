package com.manshal_khatri.githubbrowser.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.Toast
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.model.Commit
import com.manshal_khatri.githubbrowser.util.Constants

class CommitRemoteViewFactory(
    private val context: Context,
    intent: Intent
) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var widgetItems: List<Commit>
    override fun onCreate() {
        // In onCreate() you setup any connections / cursors to your data
        // source. Heavy lifting, for example downloading or creating content
        // etc, should be deferred to onDataSetChanged() or getViewAt(). Taking
        // more than 20 seconds in this call will result in an ANR.
        widgetItems = List(5) { index -> Commit("Mabsgak",Constants.DEF_AVATAR,"Hy","121236") }
    }

    override fun onDataSetChanged() {
        widgetItems = List(6) { index -> Commit("Mabsgak",Constants.DEF_AVATAR,"Hy","121236") }
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
            setTextViewText(R.id.tvWidgetCommitter, widgetItems[position].owner)
        }
    }

    override fun getLoadingView(): RemoteViews {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

}