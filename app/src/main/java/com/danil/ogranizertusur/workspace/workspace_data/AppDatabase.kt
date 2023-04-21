package com.danil.ogranizertusur.workspace.workspace_data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity

@Database(entities = [WorkSpaceEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase(){

    abstract fun workspaceDao(): WorkspaceDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "workspace.db")
                    //add migration
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}