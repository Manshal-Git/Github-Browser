package com.manshal_khatri.githubbrowser.room

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.manshal_khatri.githubbrowser.model.Repository

@Database(entities = [Repository::class], version = 1)
abstract class RepositoryDB : RoomDatabase() {

}