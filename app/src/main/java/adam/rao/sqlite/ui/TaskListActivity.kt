package adam.rao.sqlite.ui

import adam.rao.sqlite.R
import adapter.TaskAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import viewmodel.TaskViewModel

class TaskListActivity : AppCompatActivity() {

    private lateinit var addTaskBtn: FloatingActionButton
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        addTaskBtn = findViewById(R.id.btnAddTask)
        taskRecyclerView = findViewById(R.id.rvTaskList)

        val factory = utils.utils.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)

        addTaskBtn.setOnClickListener {
            startActivity(Intent(this@TaskListActivity, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        viewModel.closeDatabase(this)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.beta_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.delete_all -> {
                viewModel.deleteAllRecords(this)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadTasks() {
        val tasks = viewModel.loadTasks(this)
        adapter = TaskAdapter(this, tasks)
        taskRecyclerView.adapter = adapter
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
