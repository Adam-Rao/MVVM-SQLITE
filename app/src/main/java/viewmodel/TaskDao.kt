package viewmodel

import android.content.ContentValues
import android.os.AsyncTask
import db.ContractClass
import db.DatabaseOpenHelper

class TaskDao {

    val contractClass = ContractClass.Task

    fun deleteAllRecords(db: DatabaseOpenHelper) {
        val database = db.writableDatabase

        class DeleteAll : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {
                database.execSQL(contractClass.DELETE_TASK_TABLE)
            }
        }

        val task = DeleteAll()
        task.execute()
    }

    fun deleteSpecificRecord(db: DatabaseOpenHelper, id: Int) {
        val database = db.writableDatabase
        val selection = "${contractClass._ID} = ?"
        val selectionArg = arrayOf(id.toString())

        class DeleteSpecificRecord : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {

                database.delete(
                    contractClass.TABLE_NAME,
                    selection,
                    selectionArg
                )
            }
        }

        val task = DeleteSpecificRecord()
        task.execute()
    }

    fun writeRecord(db: DatabaseOpenHelper, taskDone: String, completed: String) {
        val database = db.writableDatabase
        val values = ContentValues()

        class WriteRecord : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {
                values.put(contractClass.TASK_COLUMN, taskDone)
                values.put(contractClass.COMPLETED_COLUMN, completed)
                database.insert(contractClass.TABLE_NAME, null, values)
            }
        }

        val task = WriteRecord()
        task.execute()
    }

    fun updateTask(
        db: DatabaseOpenHelper,
        id: Int,
        updatedTask: String,
        updatedTaskCompleted: String
    ) {
        val database = db.writableDatabase
        val values = ContentValues()
        val selection = "${contractClass._ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        class TaskCompleted : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {
                values.put(contractClass.TASK_COLUMN, updatedTask)
                values.put(contractClass.COMPLETED_COLUMN, updatedTaskCompleted)
                database.update(
                    contractClass.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )
            }
        }

        val task = TaskCompleted()
        task.execute()
    }
}
