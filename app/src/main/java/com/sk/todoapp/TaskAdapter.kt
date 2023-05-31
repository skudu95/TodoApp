package com.sk.todoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sk.todoapp.databinding.ItemTaskListBinding

class TaskAdapter(
    private val context: Context,
    var tasks: MutableList<Tasks>,
    private val taskCheckedListener: TaskCheckedListener,
    private val taskLongClickListener: TaskLongClickListener
) : RecyclerView.Adapter<TaskAdapter.MyHolder>() {

    class MyHolder(binding: ItemTaskListBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvTaskName
        val checked = binding.cbCompleted
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ItemTaskListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = tasks[position]

        holder.name.text = model.title
        holder.checked.isChecked = model.isCompleted

        holder.checked.setOnCheckedChangeListener{_, isChecked ->
            taskCheckedListener.onTaskChecked(model, isChecked)
        }

        holder.root.setOnLongClickListener {
            taskLongClickListener.onTaskLongClicked(model, position)
            return@setOnLongClickListener true
        }
    }

    fun submitNewList(newList: List<Tasks>) {
        tasks.clear()
        tasks.addAll(newList)
        notifyDataSetChanged()
    }

    fun deleteTask(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    interface TaskCheckedListener {
        fun onTaskChecked(task: Tasks, isChecked: Boolean)
    }

    interface TaskLongClickListener {
        fun onTaskLongClicked(task: Tasks, position: Int)
    }
}