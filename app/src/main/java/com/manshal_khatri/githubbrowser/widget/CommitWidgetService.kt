package com.manshal_khatri.githubbrowser.widget

import android.content.Intent
import android.widget.RemoteViewsService

class CommitWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return CommitRemoteViewFactory(this.applicationContext, intent)
    }
}
