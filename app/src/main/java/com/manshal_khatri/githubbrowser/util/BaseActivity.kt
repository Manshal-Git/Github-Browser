package com.manshal_khatri.githubbrowser.util

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.manshal_khatri.githubbrowser.R
import com.manshal_khatri.githubbrowser.viewmodel.HomeViewModel

open class BaseActivity : AppCompatActivity() {

    private lateinit var dialog : Dialog
    lateinit var viewModel : HomeViewModel
    fun showProgressDialog(){
        dialog = Dialog(this)
        dialog.setContentView(R.layout.loading_screen)
        dialog.show()
    }
    fun hideProgressDialog(){
            dialog.hide()
    }

     fun setupActionBar(toolbar : Toolbar, title : String){
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
    }

    fun getHomeViewModel(owner: LifecycleOwner){
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.setUpDataBase(this)
    }

}