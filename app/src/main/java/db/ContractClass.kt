package db

class ContractClass private constructor() {
    companion object class Task {
        companion object {
            const val TABLE_NAME = "tasks"
            const val _ID = "_id"
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