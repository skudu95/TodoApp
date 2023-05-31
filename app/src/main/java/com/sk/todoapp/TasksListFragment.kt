package com.sk.todoapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.todoapp.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment(), TaskAdapter.TaskCheckedListener,
    TaskAdapter.TaskLongClickListener {

    companion object {
        fun newInstance() = TasksListFragment()
    }

    private lateinit var binding: FragmentTasksListBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // recycler view setup
        taskAdapter = TaskAdapter(requireContext(), mutableListOf(), this, this)
        binding.rvTasks.adapter = taskAdapter
        binding.rvTasks.setHasFixedSize(true)
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = taskAdapter

        // view model setup
        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks?.let {
                taskAdapter.submitNewList(it)

                // no task available, show textview
                if (it.isEmpty()) {
                    binding.tvNoTasksAvailable.visibility = View.VISIBLE
                    binding.rvTasks.visibility = View.GONE
                } else {
                    binding.tvNoTasksAvailable.visibility = View.GONE
                    binding.rvTasks.visibility = View.VISIBLE
                }
            }
        }

        // add button
        binding.btnAddTask.setOnClickListener {
            // navigate to AddTaskFragment
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigate(R.id.action_tasksListFragment_to_addTaskFragment)
        }
    }

    override fun onTaskChecked(task: Tasks, isChecked: Boolean) {
        task.isCompleted = isChecked
        viewModel.update(task)
    }

    override fun onTaskLongClicked(task: Tasks, position: Int) {
        val options = arrayOf("Edit", "Delete")
        AlertDialog.Builder(requireContext())
            .setTitle("Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        showEditDialog(task)
                    }
                    1 -> {
                        showDeleteDialog(task, position)
                    }
                }
            }
            .show()
    }

    private fun showEditDialog(task: Tasks) {
        val dialog = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_task, null)
        dialog.setView(dialogView)
        dialog.setTitle("Edit Task")

        val taskTitle = dialogView.findViewById<EditText>(R.id.et_task_title)
        val taskDescription = dialogView.findViewById<EditText>(R.id.et_task_description)

        taskTitle.isEnabled = false // disabled title update
        taskTitle.setText(task.title)
        taskDescription.setText(task.description)

        dialog.setPositiveButton("Update") { dialog, _ ->
            task.description = taskDescription.text.toString()
            viewModel.update(task)
            dialog.dismiss()
        }
        dialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDeleteDialog(task: Tasks, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.delete(task)
                taskAdapter.deleteTask(position)
                Toast.makeText(requireContext(), "Task deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}