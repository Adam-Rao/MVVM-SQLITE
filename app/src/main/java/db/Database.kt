package db

import viewmodel.TaskDao

class Database {
    var taskDao = TaskDao()
        private set

    companion object {
        @Volatile private var instance: Database? = null

        fun getInstance() = instance?: synchronized(this) {
            instance ?: Database().also{instance = it}
        }
    }

}