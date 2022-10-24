package com.manshal_khatri.githubbrowser

import android.content.Context
import com.manshal_khatri.githubbrowser.widget.CommitsWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object BoradCaster {
    val cw = CommitsWidget()
    fun sendBC(context : Context){
        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            cw.refreshByBroadcast(context )
        }
    }
}