package com.manshal_khatri.githubbrowser

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.manshal_khatri.githubbrowser.model.Commit
import com.manshal_khatri.githubbrowser.util.Constants

class SyncWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    var notificationService: NotificationService? = null
    var oldSize: Int? = null
    override fun doWork(): Result {

        // Send notification
        println("Sending")
        if (notificationService == null)
            notificationService = NotificationService(context)

        notificationService!!.showNotification(
            Constants.REMINDER_JOB_ID,
            Constants.NOTIFICATION_TAG,
            "Sync By workmanger"
        )
        println("Sent")

        val sp = context.getSharedPreferences(Constants.SP_WIDGET, Context.MODE_PRIVATE)
        val owner = sp.getString(Constants.SP_WIDGET_DATA_OWNER, "manshal_git").toString()
        val repo = sp.getString(Constants.SP_WIDGET_DATA_REPO, "codemin").toString()

        val reqCommits = object :
            JsonArrayRequest(
                Method.GET,
                Constants.API_GITHUB + "$owner/$repo/commits?sha=master",
                null,
                Response.Listener {
                    if (oldSize == null) oldSize = it.length()

                    if (it.length() > oldSize!!) {

                        println(it)
                        for (i in 0 until it.length()) {
                            if (i < oldSize!!) continue
                            else println("skipped $i")

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

                                notificationService!!.showNotification(
                                    i,
                                    commit.owner,
                                    commit.message
                                )
                            }
                        }
                    }
                }, Response.ErrorListener {
                    println("Volley Error : $it")
                }) {}

        Volley.newRequestQueue(context).add(reqCommits)

        return Result.success()
    }
}