package com.manshal_khatri.githubbrowser.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.Toast
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.manshal_khatri.githubbrowser.NotificationService
import com.manshal_khatri.githubbrowser.SyncWorker
import com.manshal_khatri.githubbrowser.model.Commit
import com.manshal_khatri.githubbrowser.util.Constants
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


class CommitRemoteViewFactory(
    private val context: Context,
    val intent: Intent
) : RemoteViewsService.RemoteViewsFactory {
    private lateinit var widgetItems: MutableList<Commit>
    lateinit var queue: RequestQueue

    //    lateinit var reqCommits : JsonArrayRequest
    var owner = ""
    var repo = ""
    var branchName = "master"

    //    val item =  Commit("Annonymos", Constants.DEF_AVATAR, "", "12-25-6")
    lateinit var sp: SharedPreferences
    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    val notificationService = NotificationService(context)

    override fun onCreate() {
        // In onCreate() you setup any connections / cursors to your data
        // source. Heavy lifting, for example downloading or creating content
        // etc, should be deferred to onDataSetChanged() or getViewAt(). Taking
        // more than 20 seconds in this call will result in an ANR.
        queue = Volley.newRequestQueue(context)
        widgetItems = mutableListOf()

        sp = context.getSharedPreferences(Constants.SP_WIDGET, MODE_PRIVATE)
        owner = sp.getString(Constants.SP_WIDGET_DATA_OWNER, "manshal_git").toString()
        repo = sp.getString(Constants.SP_WIDGET_DATA_REPO, "codemin").toString()

//           appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        if (intent.hasExtra("owner") && intent.hasExtra("repo")) {
            owner = intent.getStringExtra("owner").toString()
            repo = intent.getStringExtra("repo").toString()
        }
        Toast.makeText(context, "onCreate", Toast.LENGTH_SHORT).show()
//        println("in on create $owner $repo")

        val periodicWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            SyncWorker::class.java,
            15, TimeUnit.MINUTES,
            10, TimeUnit.MINUTES
        )
//            .setConstraints(constraints)
            .addTag(Constants.NOTIFICATION_TAG)
            .build()

        // Remember this
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            Constants.NOTIFICATION_TAG, ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    override fun onDataSetChanged() {
//        println("in datasetchanged $owner $repo")
        val reqCommits = object :
            JsonArrayRequest(
                Method.GET,
                Constants.API_GITHUB + "$owner/$repo/commits?sha=$branchName",
                null,
                Response.Listener {
                    if (it.length() > widgetItems.size) {
                        val lastCommitIndex = widgetItems.size
                        widgetItems.clear()
                        println(it)
                        for (i in 0 until it.length()) {

                            val commitJsonObj = it.getJSONObject(i)
                            with(commitJsonObj) {
                                val commit = Commit(
                                    getJSONObject("commit").getJSONObject("committer")
                                        .getString("name"),
                                    avatar_url = try {
                                        if (this.getJSONObject("committer") != null) {
                                            getJSONObject("committer").getString("avatar_url")
                                        } else {
                                            Constants.DEF_AVATAR
                                        }
                                    } catch (e: Exception) {
                                        Constants.DEF_AVATAR
                                    }, getJSONObject("commit").getString("message"),
                                    getJSONObject("commit").getJSONObject("committer")
                                        .getString("date")
                                )
                                if (i <= it.length() - lastCommitIndex) {
                                    notificationService.showNotification(
                                        i,
                                        commit.owner,
                                        commit.message
                                    )
                                }
                                addCommit(
                                    commit
                                )
                            }
                        }

                    }
                }, Response.ErrorListener {
                    println("Volley Error : $it")
                }) {}
        queue.add(reqCommits)

    }

    fun addCommit(commit: Commit) {
        widgetItems.add(commit)
    }

    override fun onDestroy() {
        queue.cancelAll("Widget Destroyed")
    }

    override fun getCount(): Int {
        return widgetItems.size
    }


    override fun getViewAt(position: Int): RemoteViews {
        // Construct a remote views item based on the widget item XML file,
        // and set the text based on the position.
        val wi = widgetItems[position]

        val dateTime = LocalDateTime.ofInstant(Instant.parse(wi.date), ZoneId.systemDefault())
        val localDateTime = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yy")) +
                " at " + dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
        return RemoteViews(
            context.packageName,
            com.manshal_khatri.githubbrowser.R.layout.item_widget_commit
        ).apply {
            setTextViewText(com.manshal_khatri.githubbrowser.R.id.tvCommitter, wi.owner)
            setTextViewText(com.manshal_khatri.githubbrowser.R.id.tvDate, localDateTime.toString())
            setTextViewText(com.manshal_khatri.githubbrowser.R.id.tvMessage, wi.message)
            try {
                val bitmap: Bitmap = Glide.with(context)
                    .asBitmap()
                    .load(wi.avatar_url)
                    .submit(512, 512)
                    .get()
                setImageViewBitmap(com.manshal_khatri.githubbrowser.R.id.ivAvatar, bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(
            context.packageName,
            com.manshal_khatri.githubbrowser.R.layout.item_widget_commit
        )
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