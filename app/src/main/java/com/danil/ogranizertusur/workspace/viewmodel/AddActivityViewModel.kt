package com.danil.ogranizertusur.workspace.viewmodel

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.danil.ogranizertusur.R
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.workspace_data.repository.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface AddActivityViewModelAbstract {

    val selectedNoteState: State<WorkSpaceEntity?>
    val workspaceListFlow: Flow<List<WorkSpaceEntity>>
    //val workspaceList: List<WorkSpaceEntity>

    fun addOrUpdateWorkspace(workspace: WorkSpaceEntity)
    fun updateWorkspace(workspace: WorkSpaceEntity)
    fun deleteWorkspace(workspace: WorkSpaceEntity)
    fun selectedNote(workspace: WorkSpaceEntity)
    fun resetSelectedNote()

}

@HiltViewModel
class AddActivityViewModel
@Inject constructor(
    private val workspaceRepository: WorkspaceRepository,


    ) : ViewModel(), AddActivityViewModelAbstract {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _selectedNoteState: MutableState<WorkSpaceEntity?> = mutableStateOf(null)
    override val selectedNoteState: State<WorkSpaceEntity?>
        get() = _selectedNoteState

    override val workspaceListFlow: Flow<List<WorkSpaceEntity>> =
        workspaceRepository.getAllFlow().map { notes ->
            notes.sortedWith(
                compareBy({
                    LocalDate.parse(
                        it.date,
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    )
                },
                    {
                        LocalTime.parse(
                            it.time,
                            when (it.time.length) {
                                4 -> DateTimeFormatter.ofPattern("H:mm")
                                else -> DateTimeFormatter.ofPattern("HH:mm")
                            }
                        )
                    }
                )
            )
        }

    override fun addOrUpdateWorkspace(workspace: WorkSpaceEntity) {
        ioScope.launch {
            if (workspace.roomId == null) {

                workspaceRepository.insert(workspaceEntity = workspace)
            } else {
                workspaceRepository.update(workspaceEntity = workspace)

            }
        }
    }


    override fun updateWorkspace(workspace: WorkSpaceEntity) {
        ioScope.launch {
            workspaceRepository.update(workspaceEntity = workspace)
        }
    }

    override fun deleteWorkspace(workspace: WorkSpaceEntity) {
        ioScope.launch {
            workspaceRepository.delete(workspaceEntity = workspace)
        }
    }

    override fun selectedNote(workspace: WorkSpaceEntity) {
        _selectedNoteState.value = workspace
    }

    override fun resetSelectedNote() {
        _selectedNoteState.value = null
    }


    /*override fun notifyUser(workspace: WorkSpaceEntity) {
        //TODO("Not yet implemented")
    }*/


    init {
        var t = ""
    workspaceListFlow.map { notes->

        notes.forEachIndexed{index,note->
            t=note.time
        }
    }

       /* createNotificationChannel(context = Graph.appContext)
        setOneTimeNotification(addViewModel = this@AddActivityViewModel,
            WorkSpaceEntity(1,"",t,"",true)
        )*/

        //setOneTimeNotification(this@AddActivityViewModel)


       /* CoroutineScope(Dispatchers.IO).launch {
            createNotificationChannel(context = Graph.appContext)

        }*/


    }

}

private fun setOneTimeNotification(addViewModel: AddActivityViewModelAbstract,note:WorkSpaceEntity){
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .build()
    val notificationWorker = OneTimeWorkRequestBuilder<WorkSpaceNotifyWorker>()
        .setInitialDelay(10, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .build()

    workManager.enqueue(notificationWorker)

    //Monitoring for state of work
    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo->
            if(workInfo.state == WorkInfo.State.SUCCEEDED){
                CoroutineScope(Dispatchers.IO).launch {
                    createSuccessNotification(note)
                }

            }
           /* else {
                createErrorNotification()
            }*/

        }
}

private fun createNotificationChannel(context: Context){
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
        val name = "NotificationChannelName"
        val descriptionText = "NotificationDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name,importance).apply {
            description = descriptionText
        }
        // register the channel with the system
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}
private fun createSuccessNotification(note: WorkSpaceEntity){
    val notification = 1
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.baseline_access_time_24)
        .setContentTitle("Success! Download complete ${note.time}")
        .setContentText("Your countdown completed successfully")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(Graph.appContext)){
        //notificationId is unique for each notification that you define
        if (ActivityCompat.checkSelfPermission(
                Graph.appContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(notification, builder.build())
    }
}

private fun createTaskStartNotification(addViewModel: AddActivityViewModelAbstract,note: WorkSpaceEntity){
    var itemList = listOf<WorkSpaceEntity>()
    //val item1 = mutableStateOf(listOf<WorkSpaceEntity>())


    val scope = CoroutineScope(Dispatchers.IO)
    //item1.value = addViewModel.workspaceListFlow.flattenToList()




    //val itemListState1= itemListState[0]
    val notificationId = 2
    val builder = NotificationCompat.Builder(
        Graph.appContext,
        "CHANNEL_ID"
    )   .setSmallIcon(R.drawable.baseline_access_time_24)
        .setContentTitle("Your task starts now")
        .setContentText("Your task ${note.text}, starts at ${note.time} ")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    with(NotificationManagerCompat.from(Graph.appContext)){
        if (ActivityCompat.checkSelfPermission(
                Graph.appContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notify(notificationId, builder.build())
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(notificationId, builder.build())
    }

}
/*private fun workSpaceNotification()
{
    val intent = Intent(Graph.appContext, Notification::class.java)
    val title =
}*/