package viewmodel

import android.content.ContentValues
import android.database.Cursor
import android.os.AsyncTask
import db.ContractClass
import db.DatabaseOpenHelper

class TaskDao {

    val contractClass = ContractClass.Task

    companion object fun deleteAllRecords(db: DatabaseOpenHelper) {
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

    fun loadTasks(db: DatabaseOpenHelper): Cursor? {

        val database = db.readableDatabase
        var cursor: Cursor? = null

        val columns = arrayOf(
            contractClass.TASK_COLUMN,
            contractClass.COMPLETED_COLUMN
        )

        class LoadStuff: AsyncTask<Void, Void, Cursor?>() {
            override fun doInBackground(vararg params: Void?): Cursor? {
                return database.query(
                    contractClass.TABLE_NAME,
                    columns,
                    null, null, null, null, null
                )
            }

            override fun onPostExecute(result: Cursor?) {
                cursor = result
            }
        }

        val task = LoadStuff()
        task.execute()

        return cursor
    }

    fun loadSpecificTask(db: DatabaseOpenHelper, id: Int): List<String> {

        val database = db.readableDatabase
        val item = mutableListOf<String>()

        val columns = arrayOf(
            contractClass.TASK_COLUMN,
            contractClass.COMPLETED_COLUMN
        )

        val selection = "${contractClass._ID } = ?"
        val selectionArg = arrayOf(id.toString())

        class LoadStuff: AsyncTask<Void, Void, Cursor?>() {
            override fun doInBackground(vararg params: Void?): Cursor? {
                return database.query(
                    contractClass.TABLE_NAME,
                    columns,
                    selection, selectionArg, null, null, null
                )
            }

            override fun onPostExecute(result: Cursor?) {
                val taskIndex = result!!.getColumnIndex(contractClass.TASK_COLUMN)
                val completedIndex = result.getColumnIndex(contractClass.COMPLETED_COLUMN)

                val task = result.getString(taskIndex)
                val taskCompleted = result.getString(completedIndex)

                item.add(task)
                item.add(taskCompleted)
            }
        }

        val task = LoadStuff()
        task.execute()

        return item
    }
}
