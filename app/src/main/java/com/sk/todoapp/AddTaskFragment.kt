package com.sk.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sk.todoapp.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {

    companion object {
        fun newInstance() = AddTaskFragment()
    }

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        binding.btnSave.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val description = binding.etTaskDescription.text.toString()

            if (title.isNotEmpty()) {
                viewModel.insert(Tasks(title = title, description = description, isCompleted = false))
                requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                    .navigate(R.id.action_addTaskFragment_to_tasksListFragment)
            }

        }
    }

}
