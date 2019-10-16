package adam.rao.sqlite.ui

import adam.rao.sqlite.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var task: TextInputEditText
    private lateinit var taskCompleted: TextInputEditText
    private lateinit var addTask: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        task = findViewById(R.id.et_task_input)
        taskCompleted = findViewById(R.id.et_completed_input)
        addTask = findViewById(R.id.btn_add_task)
    }
}
