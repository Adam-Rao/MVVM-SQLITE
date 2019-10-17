package adam.rao.sqlite.ui

import adam.rao.sqlite.R
import adapter.TaskAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import db.DatabaseOpenHelper
import viewmodel.TaskViewModel

class TaskListActivity : AppCompatActivity() {

    private lateinit var addTaskBtn: FloatingActionButton
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var database: DatabaseOpenHelper
    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        addTaskBtn = findViewById(R.id.btnAddTask)
        taskRecyclerView = findViewById(R.id.rvTaskList)

        adapter = TaskAdapter(this, null)
        database = DatabaseOpenHelper(this)
        val factory = utils.utils.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)

        addTaskBtn.setOnClickListener {
            startActivity(Intent(this@TaskListActivity, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val cursor = viewModel.loadTasks(database)
        adapter.changeCursor(cursor)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.changeCursor(null)
        database.close()
    }
}
