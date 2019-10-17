package utils

import android.content.Context
import db.Database
import viewmodel.TaskRepository
import viewmodel.ViewModelFactory

object utils {
    fun provideViewModelFactory(context: Context) : ViewModelFactory {
        val repository = TaskRepository.getInstance(Database.getInstance().taskDao)
        return ViewModelFactory(repository)
    }
}