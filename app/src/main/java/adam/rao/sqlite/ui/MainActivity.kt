package adam.rao.sqlite.ui

import adam.rao.sqlite.R
import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import db.DatabaseOpenHelper
import viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var task: TextInputEditText
    private lateinit var taskCompleted: TextInputEditText
    private lateinit var database: DatabaseOpenHelper
    private lateinit var viewModel: TaskViewModel
    private val TASK_ID = "task_id"
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent = intent
        id = intent.getIntExtra(TASK_ID, 0)

        task = findViewById(R.id.et_task_input)
        taskCompleted = findViewById(R.id.et_completed_input)

        val factory = utils.utils.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(TaskViewModel::class.java)

        database = DatabaseOpenHelper(this)
    }

    override fun onResume() {
        super.onResume()
        if(id != 0) {
            val taskItem = viewModel.loadSpecificTask(database, id)
            task.setText(taskItem[0])
            taskCompleted.setText(taskItem[1])

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId) {
            R.id.save_task -> {
                viewModel.writeRecord(database, task.text.toString(), taskCompleted.text.toString())
                Toast.makeText(this, "Record Saved Successfully", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.view_all_tasks -> {
                startActivity(Intent(this@MainActivity, ListActivity::class.java))
                return true
            }
            R.id.delete_task -> {
                viewModel.deleteSpecificRecord(database, id)
                return true
            }
            R.id.update_task -> {
                viewModel.updateTask(database, id, task.text.toString(), taskCompleted.text.toString())
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
