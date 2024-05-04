package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.CustomAdapter
import com.example.todo.R
import com.example.todo.database.DatabaseClass
import com.example.todo.database.TaskItem
import com.example.todo.databinding.FragmentAllTasksBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
 class AllTasksFragment : Fragment() {
    private var _binding: FragmentAllTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var recycle: RecyclerView
    private lateinit var itemlist: ArrayList<TaskItem>
    private lateinit var adapter: CustomAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            AllTasksFragmentDirections.actionAllTasksFragmentToAddNewTask()
            findNavController().navigate(R.id.action_allTasksFragment_to_addNewTask)
        }
        recycle = binding.rec
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseClass.getDatabase(requireContext())
                val users = db.DaoTasks().getUsers()
                withContext(Dispatchers.Main) {
                    itemlist = ArrayList(users)
                    adapter = CustomAdapter(itemlist,requireActivity())
                    recycle.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
                    recycle.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}