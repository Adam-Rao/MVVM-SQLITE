package viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import model.TaskModel

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    fun deleteAllRecords(context: Context) = taskRepository.deleteAllRecords(context)

    fun deleteSpecificRecord(context: Context, id: Int) = taskRepository.deleteSpecificRecord(context, id)

    fun writeRecord(context: Context, task: String, taskCompleted: String) =
        taskRepository.writeRecord(context, task, taskCompleted)

    fun updateTask(context: Context, id:Int, task: String, taskCompleted: String) =
        taskRepository.updateTask(context, id, task, taskCompleted)

    fun loadTasks(context: Context): ArrayList<TaskModel> =
        taskRepository.loadTasks(context)

    fun  loadSpecificTask(context: Context, id: Int): TaskModel? =
        taskRepository.loadSpecificTask(context, id)

    fun closeDatabase(context: Context) = taskRepository.closeDatabase(context)
}