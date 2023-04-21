package com.danil.ogranizertusur.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.workspace_data.AppDatabase
import com.danil.ogranizertusur.workspace.workspace_data.WorkspaceDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class NoteDaoAndroidTest {

    private lateinit var sut: WorkspaceDao
    private lateinit var mdb: AppDatabase

    @Before
    fun createDb(){
        mdb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        sut= mdb.workspaceDao()
    }
    @After
    fun cleanDb(){

    }

    @Test
    fun testInsertNoteAndReadInList() = runBlocking {

        val fakeText = "some text"
        val fakeDate = "13.04.2023"
        val fakeTime = "4:00"
        val fakeStatus = false
        val fakeNote = WorkSpaceEntity(date = fakeDate,
            time = fakeTime,
            text = fakeText,
            status = fakeStatus)

        sut.insert(fakeNote)
        val noteListFlow = sut.getAllFlow()

    //    assertThat(noteListFlow.toString()).isEqualTo(fakeDate,fakeTime,fakeText,fakeStatus)

    }
}
