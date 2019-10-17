package viewmodel

import android.database.Cursor
import androidx.lifecycle.ViewModel
import db.DatabaseOpenHelper

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    fun deleteAllRecords(db: DatabaseOpenHelper) = taskRepository.deleteAllRecords(db)

    fun deleteSpecificRecord(db: DatabaseOpenHelper, id: Int) = taskRepository.deleteSpecificRecord(db, id)

    fun writeRecord(db: DatabaseOpenHelper, task: String, taskCompleted: String) =
        taskRepository.writeRecord(db, task, taskCompleted)

    fun updateTask(db: DatabaseOpenHelper, id:Int, task: String, taskCompleted: String) =
        taskRepository.updateTask(db, id, task, taskCompleted)

    fun loadTasks(db: DatabaseOpenHelper): Cursor? =
        taskRepository.loadTasks(db)

    fun  loadSpecificTask(db: DatabaseOpenHelper, id: Int): List<String> =
        taskRepository.loadSpecificTask(db, id)
}