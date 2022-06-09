package com.manshal_khatri.githubbrowser.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.manshal_khatri.githubbrowser.model.GitRepository

@Database(entities = [GitRepository::class], version = 1)
abstract class GitRepositoryDB : RoomDatabase() {

    abstract fun gitRepoDao() : GitRepositoryDao

    companion object{

        @Volatile
        private var INSTANCE : GitRepositoryDB? = null
        fun getDatabase(context: Context) : GitRepositoryDB{

            if(INSTANCE==null)
            {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GitRepositoryDB::class.java,
                        "git_repository_db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}