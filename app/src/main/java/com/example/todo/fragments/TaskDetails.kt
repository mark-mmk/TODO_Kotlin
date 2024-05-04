package com.example.todo.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Identity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.CustomAdapter
import com.example.todo.R
import com.example.todo.database.DatabaseClass
import com.example.todo.database.TaskItem
import com.example.todo.databinding.FragmentAllTasksBinding
import com.example.todo.databinding.FragmentTaskDetailsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetails : Fragment() {
    var _binding: FragmentTaskDetailsBinding? = null
    val binding get() = _binding!!
    val args:TaskDetailsArgs by navArgs()

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val db = DatabaseClass.getDatabase(requireContext())
            val users = db.DaoTasks().selectTaskByID(args.ID)
                binding.taskEdite.setText(users.title)
                binding.detailsEdite.setText(users.details)
                binding.dateEdit.setText(users.getFullDateString())

        binding.fabEdit.setOnClickListener {
            val action = TaskDetailsDirections.actionTaskDetailsToEditTask(users.id)
            Navigation.findNavController(it).navigate(action)
        }
        binding.bt.setOnClickListener {
            findNavController().navigate(R.id.action_taskDetails_to_allTasksFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}