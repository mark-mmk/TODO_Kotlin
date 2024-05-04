package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.database.DatabaseClass
import com.example.todo.database.TaskItem
import com.example.todo.databinding.FragmentAddNewTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNewTask : Fragment() {
    var _binding: FragmentAddNewTaskBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var datePicker = MaterialDatePicker.Builder.datePicker().build()
        var getDate: Long = 0
        binding.addLayoutRemind.setEndIconOnClickListener {
            datePicker.apply {
                show(this@AddNewTask.requireActivity().supportFragmentManager, "datePicker")
                addOnPositiveButtonClickListener {
                    getDate = it
                    binding.addInputRemind.setText(this.headerText)
                }
            }
        }
        binding.bt.setOnClickListener {
            findNavController().navigate(R.id.action_addNewTask_to_allTasksFragment)
        }
        binding.save.setOnClickListener {
            val newUser = TaskItem(
                id = 0,
                binding.addTask.text.toString(),
                binding.addInputDetails.text.toString(),
                taskDate = getDate
            )
            CoroutineScope(Dispatchers.IO).launch {
                val db = DatabaseClass.getDatabase(requireContext())
                db.DaoTasks().insertUser(newUser)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Task saved", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addNewTask_to_allTasksFragment)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}