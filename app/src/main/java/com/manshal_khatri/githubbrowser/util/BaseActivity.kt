package com.manshal_khatri.githubbrowser.util

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manshal_khatri.githubbrowser.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var dialog : Dialog

    fun showProgressDialog(){
        dialog = Dialog(this)
        dialog.setContentView(R.layout.loading_screen)
        dialog.show()
    }
    fun hideProgressDialog(){
            dialog.hide()
    }

}