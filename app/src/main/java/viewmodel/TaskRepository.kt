package viewmodel

import db.DatabaseOpenHelper

class TaskRepository(private val taskDao: TaskDao) {
    fun deleteAllRecords(db: DatabaseOpenHelper) = taskDao.deleteAllRecords(db)

    fun deleteSpecificRecord(db: DatabaseOpenHelper, id: Int) = taskDao.deleteSpecificRecord(db, id)

    fun writeRecord(db: DatabaseOpenHelper, task: String, completed: String) =
        taskDao.writeRecord(db, task, completed)

    fun updateTask(db: DatabaseOpenHelper, id: Int, task: String, completed: String) =
        taskDao.updateTask(db, id, task, completed)

    companion object {
        @Volatile private var instance: TaskRepository? = null

        fun getInstance(taskDao: TaskDao) = instance?: synchronized(this) {
            instance?: TaskRepository(taskDao).also { instance=it }
        }
    }
}