package db

import android.provider.BaseColumns
import android.provider.BaseColumns._ID

class ContractClass private constructor() {
    companion object class Task: BaseColumns {
        companion object {
            const val TABLE_NAME = "tasks"
            const val TASK_COLUMN = "task"
            const val COMPLETED_COLUMN = "task_completed"

            const val CREATE_TASK_TABLE = "CREATE TABLE $TABLE_NAME (" +
                    "$_ID INTEGER PRIMARY KEY, " +
                    "$TASK_COLUMN VARCHAR(256), " +
                    "$COMPLETED_COLUMN VARCHAR(256)" +
                    ")"
        }
    }
}