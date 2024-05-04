package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.database.DatabaseClass
import com.example.todo.database.TaskItem
import com.example.todo.databinding.FragmentEditTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditTask : Fragment() {
    var _binding: FragmentEditTaskBinding? = null
    val binding get() = _binding!!
    val args:EditTaskArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var datePicker = MaterialDatePicker.Builder.datePicker().build()
        var getDate: Long = 0
        val db = DatabaseClass.getDatabase(requireContext())
        val users = db.DaoTasks().selectTaskByID(args.IDEdite)
        binding.EditTask.setText(users.title)
        binding.addInputDetails.setText(users.details)
        binding.editInputRemind.setText(users.getFullDateString())
        binding.bt2.setOnClickListener {
            val action = EditTaskDirections.actionEditTaskToTaskDetails(users.id)
            Navigation.findNavController(it).navigate(action)
        }
        binding.editLayoutRemind.setEndIconOnClickListener {
            datePicker.apply {
                show(this@EditTask.requireActivity().supportFragmentManager, "datePicker")
                addOnPositiveButtonClickListener {
                    getDate = it
                    binding.editInputRemind.setText(this.headerText)
                }
            }
        }
        binding.Update.setOnClickListener {
            if (binding.editInputRemind.text.toString() == users.getFullDateString()){
                val updateUser = TaskItem(
                    id = users.id,
                    binding.EditTask.text.toString(),
                    binding.addInputDetails.text.toString(),
                    taskDate = users.taskDate,
                )
                CoroutineScope(Dispatchers.IO).launch {
                    val db = DatabaseClass.getDatabase(requireContext())
                    db.DaoTasks().updateUsers(updateUser)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Task Update", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_editTask_to_allTasksFragment)
                    }
                }
            }
            else{
                val updateUser = TaskItem(
                    id = users.id,
                    binding.EditTask.text.toString(),
                    binding.addInputDetails.text.toString(),
                    taskDate = getDate,
                )
                CoroutineScope(Dispatchers.IO).launch {
                    val db = DatabaseClass.getDatabase(requireContext())
                    db.DaoTasks().updateUsers(updateUser)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Task Update", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_editTask_to_allTasksFragment)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }
}