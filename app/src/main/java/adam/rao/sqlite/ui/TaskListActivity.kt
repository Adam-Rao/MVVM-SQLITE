package adam.rao.sqlite.ui

import adam.rao.sqlite.R
import adapter.TaskAdapter
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        addTaskBtn = findViewById(R.id.btnAddTask)
        taskRecyclerView = findViewById(R.id.rvTaskList)

        adapter = TaskAdapter(this, null)
        taskRecyclerView.adapter = adapter
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        database = DatabaseOpenHelper(this)
        val factory = utils.utils.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)

        addTaskBtn.setOnClickListener {
            startActivity(Intent(this@TaskListActivity, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(cursor != null) {
            cursor?.close()
        }
        adapter.changeCursor(null)
        database.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.beta_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.delete_all -> {
                viewModel.deleteAllRecords(database)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadTasks() {
        cursor = viewModel.loadTasks(database)
        adapter.changeCursor(cursor)
    }
}
