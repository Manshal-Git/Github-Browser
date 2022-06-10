package com.manshal_khatri.githubbrowser.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideUtil(val context: Context) {

    fun setRoundImage(img : String,imgView : ImageView){
        Glide.with(context).load(img).error(Constants.DEF_AVATAR).circleCrop().into(imgView)
    }

}