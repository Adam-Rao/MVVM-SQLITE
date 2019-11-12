package viewmodel

import android.content.Context
import model.TaskModel

class TaskRepository(private val taskDao: TaskDao) {
    fun deleteAllRecords(context: Context) = taskDao.deleteAllRecords(context)

    fun deleteSpecificRecord(context: Context, id: Int) = taskDao.deleteSpecificRecord(context, id)

    fun writeRecord(context: Context, task: String, completed: String) =
        taskDao.writeRecord(context, task, completed)

    fun updateTask(context: Context, id: Int, task: String, completed: String) =
        taskDao.updateTask(context, id, task, completed)

    fun loadTasks(context: Context): ArrayList<TaskModel> =
        taskDao.loadTasks(context)

    fun loadSpecificTask(context: Context, id: Int): TaskModel? =
        taskDao.loadSpecificTask(context, id)

    fun closeDatabase(context: Context) = taskDao.closeDatabase(context)

    companion object {
        @Volatile private var instance: TaskRepository? = null

        fun getInstance(taskDao: TaskDao) = instance?: synchronized(this) {
            instance?: TaskRepository(taskDao).also { instance=it }
        }
    }
}