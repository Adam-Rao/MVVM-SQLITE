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

class TaskAdapter(private val context: Context, private var mCursor: Cursor?):
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private var taskPos: Int = 0
    private var completed: Int = 0
    private val contractClass = ContractClass.Task
    private var id: Int= 0
    private val TASK_ID = "task_id"

    fun changeCursor(cursor: Cursor?) {
        if(mCursor != null) {
            mCursor!!.close()
        }
        mCursor = cursor
        getIndices()
        notifyDataSetChanged()
    }

    private fun getIndices() {
        if(mCursor == null) {
            return
        }
        taskPos = mCursor?.getColumnIndex(contractClass.TASK_COLUMN) as Int
        completed = mCursor?.getColumnIndex(contractClass.COMPLETED_COLUMN) as Int
        id = mCursor?.getColumnIndex(contractClass._ID) as Int
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
        return if(mCursor == null) {
            0
        } else {
            mCursor!!.count
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mCursor?.moveToPosition(position)
        holder.task.text = mCursor?.getString(taskPos)
        holder.taskCompleted.text = mCursor?.getString(completed)
        holder.taskId = mCursor?.getInt(id) as Int
    }
}