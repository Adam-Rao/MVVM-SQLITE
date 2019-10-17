package adapter

import adam.rao.sqlite.R
import adam.rao.sqlite.ui.MainActivity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import db.ContractClass

class TaskAdapter(private val context: Context, private val cursor: Cursor?):
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var taskPos: Int = 0
    private var completed: Int = 0
    private val contractClass = ContractClass.Task
    private var id: Int = 0
    private val TASK_ID = "task_id"

    init {
        this.taskPos = cursor!!.getColumnIndex(contractClass.TASK_COLUMN)
        this.completed = cursor.getColumnIndex(contractClass.COMPLETED_COLUMN)
        this.id = cursor.getColumnIndex(contractClass._ID)
    }

    fun changeCursor(cursor: Cursor?) {
        changeCursor(cursor)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var taskId: Int = 0
        var task = itemView.findViewById<TextView>(R.id.tv_task)!!
        var taskCompleted = itemView.findViewById<TextView>(R.id.tv_task_completed)!!

        override fun onClick(v: View?) {
            val intent = Intent(v?.context, MainActivity::class.java)
            intent.putExtra(TASK_ID, taskId)
            v?.context?.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor!!.count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor!!.moveToPosition(position)
        holder.task.text = cursor.getString(taskPos)
        holder.taskCompleted.text = cursor.getString(completed)
        holder.taskId = cursor.getInt(id)
    }
}