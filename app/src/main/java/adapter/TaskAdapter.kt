package adapter

import adam.rao.sqlite.R
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import db.ContractClass

class TaskAdapter(private val context: Context, private val cursor: Cursor):
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var taskPos: Int = 0
    private var completed: Int = 0
    private val contractClass = ContractClass.Task

    init {
        this.taskPos = cursor.getColumnIndex(contractClass.TASK_COLUMN)
        this.completed = cursor.getColumnIndex(contractClass.COMPLETED_COLUMN)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var task = itemView.findViewById<TextView>(R.id.tv_task)!!
        var taskCompleted = itemView.findViewById<TextView>(R.id.tv_task_completed)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        holder.task.text = cursor.getString(taskPos)
        holder.taskCompleted.text = cursor.getString(completed)
    }
}