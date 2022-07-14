package com.manshal_khatri.githubbrowser.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.manshal_khatri.githubbrowser.model.Commit
import com.manshal_khatri.githubbrowser.util.Constants
import com.manshal_khatri.githubbrowser.viewmodel.CommitViewModel


class CommitRemoteViewFactory(
    private val context: Context,
    val intent: Intent
) : RemoteViewsService.RemoteViewsFactory {
    private lateinit var widgetItems : MutableList<Commit>
    lateinit var queue : RequestQueue
    lateinit var vm : CommitViewModel
    var owner = ""
    var repo = ""
    var branchName = "master"

    val item =  Commit("Annonymos", Constants.DEF_AVATAR, "", "12-25-6")
   /* private  var widgetItems = listOf(Commit("manshal-git","no","mdasfnds","12-2-5"),
        Commit("manshal-git","no","mdasfnds","12-2-5"),
        Commit("manshal-git","no","mdasfnds","12-2-5"),
        Commit("manshal-git","no","mdasfnds","12-2-5"),
        Commit("manshal-git","no","mdasfnds","12-2-5"))*/
    private var appWidgetId : Int = AppWidgetManager.INVALID_APPWIDGET_ID
    override fun onCreate() {
        // In onCreate() you setup any connections / cursors to your data
        // source. Heavy lifting, for example downloading or creating content
        // etc, should be deferred to onDataSetChanged() or getViewAt(). Taking
        // more than 20 seconds in this call will result in an ANR.
        queue = Volley.newRequestQueue(context)
        widgetItems = mutableListOf()
//        widgetItems = List(2,{ Commit("Annonymos",Constants.DEF_AVATAR,"","12-25-6") })

            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
            owner = intent.getStringExtra("owner").toString()
            repo = intent.getStringExtra("repo").toString()
//        println("in on create $owner $repo")
    }

    override fun onDataSetChanged() {


//        println("in datasetchanged $owner $repo")
        /*vm.
        fetchCommits(context,"manshal-git","pikadex","master")*/

        val reqCommits = object :
            JsonArrayRequest(
                Method.GET,
                Constants.API_GITHUB+"$owner/$repo/commits?sha=$branchName",
                null,
                Response.Listener {
                    println(it)
                    for(i in 0 until it.length()) {
                        val commitJsonObj = it.getJSONObject(i)
                        with(commitJsonObj){
                            addCommit(
                                Commit(getJSONObject("commit").getJSONObject("committer").getString("name"),
                                avatar_url = try{if(this.getJSONObject("committer") !=null){
                                    getJSONObject("committer").getString("avatar_url")
                                }else{
                                    Constants.DEF_AVATAR
                                }}catch (e: Exception){
                                    Constants.DEF_AVATAR
                                },getJSONObject("commit").getString("message"),
                                getJSONObject("commit").getJSONObject("committer").getString("date")
                            )
                            )
                        }
//                        refresh()
                    }
                }, Response.ErrorListener {
                    println("Volley Error : $it")
                }){}
        queue.add(reqCommits)

    }
    fun addCommit(commit : Commit) {
        widgetItems.add(commit)
    }
    override fun onDestroy() {
        widgetItems.clear()
    }

    override fun getCount(): Int {
        return widgetItems.size
    }


    override fun getViewAt(position: Int): RemoteViews {
        // Construct a remote views item based on the widget item XML file,
        // and set the text based on the position.

        return RemoteViews(context.packageName, com.manshal_khatri.githubbrowser.R.layout.item_widget_commit).apply {
            setTextViewText(com.manshal_khatri.githubbrowser.R.id.tvCommitter, widgetItems[position].owner)
            setTextViewText(com.manshal_khatri.githubbrowser.R.id.tvDate, widgetItems[position].date)
            setTextViewText(com.manshal_khatri.githubbrowser.R.id.tvMessage, widgetItems[position].message)
            try {
                val bitmap: Bitmap = Glide.with(context)
                    .asBitmap()
                    .load(widgetItems[position].avatar_url)
                    .submit(512, 512)
                    .get()
                setImageViewBitmap(com.manshal_khatri.githubbrowser.R.id.ivAvatar, bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, com.manshal_khatri.githubbrowser.R.layout.item_widget_commit).apply {
//            setTextViewText(R.id.empty_view,"Loading file")
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