package viewmodel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.os.AsyncTask
import android.widget.Toast
import db.ContractClass
import db.DatabaseOpenHelper
import model.TaskModel

class TaskDao {

    val contractClass = ContractClass.Task
    val tasks = ArrayList<TaskModel>()

    fun deleteAllRecords(context: Context) {
        val db = DatabaseOpenHelper(context)
        val database = db.writableDatabase

        class DeleteAll : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {
                database.delete(contractClass.TABLE_NAME, null, null)
            }

            override fun onProgressUpdate(vararg values: Void?) {
                Toast.makeText(context, "Deleting All Tasks", Toast.LENGTH_LONG).show()
            }

            override fun onPostExecute(result: Unit?) {
                Toast.makeText(context, "All Tasks Deleted", Toast.LENGTH_LONG).show()
            }
        }

        val task = DeleteAll()
        task.execute()
    }

    fun deleteSpecificRecord(context: Context, id: Int) {
        val db = DatabaseOpenHelper(context)
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

            override fun onProgressUpdate(vararg values: Void?) {
                Toast.makeText(context, "Deleting Task", Toast.LENGTH_LONG).show()
            }
        }

        val task = DeleteSpecificRecord()
        task.execute()
    }

    fun writeRecord(context: Context, taskDone: String, completed: String) {
        val db = DatabaseOpenHelper(context)
        val database = db.writableDatabase
        val values = ContentValues()

        class WriteRecord : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void?) {
                values.put(contractClass.TASK_COLUMN, taskDone)
                values.put(contractClass.COMPLETED_COLUMN, completed)
                database.insert(contractClass.TABLE_NAME, null, values)
            }

            override fun onPostExecute(result: Unit?) {
                Toast.makeText(context, "Task Inserted Successfully", Toast.LENGTH_LONG).show()
            }
        }

        val task = WriteRecord()
        task.execute()
    }

    fun updateTask(
        context: Context,
        id: Int,
        updatedTask: String,
        updatedTaskCompleted: String
    ) {
        val db = DatabaseOpenHelper(context)
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

    fun loadTasks(context: Context): ArrayList<TaskModel> {

        val db = DatabaseOpenHelper(context)
        val database = db.readableDatabase


        val columns = arrayOf(
            contractClass._ID,
            contractClass.TASK_COLUMN,
            contractClass.COMPLETED_COLUMN
        )

        class LoadStuff : AsyncTask<Void, Void, Cursor>() {
            override fun doInBackground(vararg params: Void): Cursor {
                return database.query(
                    contractClass.TABLE_NAME,
                    columns,
                    null, null, null, null, null
                )
            }

            override fun onProgressUpdate(vararg values: Void?) {
                Toast.makeText(context, "Tasks Loading", Toast.LENGTH_LONG).show()
            }

            override fun onPostExecute(result: Cursor) {
                val taskIdIndex = result.getColumnIndex(contractClass._ID)
                val taskColumnIndex = result.getColumnIndex(contractClass.TASK_COLUMN)
                val taskCompletedIndex = result.getColumnIndex(contractClass.COMPLETED_COLUMN)

                if(tasks.size != 0) {
                    tasks.clear()
                }

                while (result.moveToNext()) {
                    val id = result.getInt(taskIdIndex)
                    val taskDone = result.getString(taskColumnIndex)
                    val taskCompleted = result.getString(taskCompletedIndex)

                    val task = TaskModel(id, taskDone, taskCompleted)
                    tasks.add(task)
                }
                result.close()
            }
        }

        val task = LoadStuff()
        task.execute()

        return tasks
    }

    fun loadSpecificTask(context: Context, id: Int): TaskModel {

        val db = DatabaseOpenHelper(context)
        val database = db.readableDatabase

        lateinit var specificTask: TaskModel

        val columns = arrayOf(
            contractClass.TASK_COLUMN,
            contractClass.COMPLETED_COLUMN
        )

        val selection = "${contractClass._ID} = ?"
        val selectionArg = arrayOf(id.toString())

        class LoadStuff : AsyncTask<Void, Void, Cursor>() {
            override fun doInBackground(vararg params: Void?): Cursor {
                return database.query(
                    contractClass.TABLE_NAME,
                    columns,
                    selection, selectionArg, null, null, null
                )
            }

            override fun onPostExecute(result: Cursor) {
                val taskIndex = result.getColumnIndex(contractClass.TASK_COLUMN)
                val completedIndex = result.getColumnIndex(contractClass.COMPLETED_COLUMN)

                val task = result.getString(taskIndex)
                val taskCompleted = result.getString(completedIndex)

                specificTask = TaskModel(task, taskCompleted)
                result.close()
            }
        }

        val task = LoadStuff()
        task.execute()

        return specificTask
    }

    fun closeDatabase(context: Context) {
        val database = DatabaseOpenHelper(context)
        database.close()
    }
}
