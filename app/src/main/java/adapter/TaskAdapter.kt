package adapter

import adam.rao.sqlite.R
import adam.rao.sqlite.ui.MainActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model.TaskModel

class TaskAdapter(private val context: Context, private val tasks: ArrayList<TaskModel>?):
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private val TASK_ID = "task_id"

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var taskId: Int = 0
        var task = itemView.findViewById<TextView>(R.id.tv_task)!!
        var taskCompleted = itemView.findViewById<TextView>(R.id.tv_task_completed)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks?.size as Int
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks?.get(position)
        holder.task.text = task?.task
        holder.taskCompleted.text = task?.completed
        holder.taskId = task?.id as Int

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(TASK_ID, holder.taskId)
            context.startActivity(intent)
        }
    }
}