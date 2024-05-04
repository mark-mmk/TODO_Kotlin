package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.database.DatabaseClass
import com.example.todo.database.TaskItem
import com.example.todo.fragments.AllTasksFragmentDirections
import com.example.todo.fragments.TaskDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CustomAdapter(var list: ArrayList<TaskItem>, var requireActivity: FragmentActivity) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day)
        val dayNumber: TextView = itemView.findViewById(R.id.dayNumber)
        val month: TextView = itemView.findViewById(R.id.month)
        val task: TextView = itemView.findViewById(R.id.task)
        val details: TextView = itemView.findViewById(R.id.details)
        val date: TextView = itemView.findViewById(R.id.date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shape, parent, false)
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.day.text = list[position].getDay()
        holder.dayNumber.text = list[position].getDate()
        holder.month.text = list[position].getMonth()
        holder.task.text = list[position].title
        holder.details.text = list[position].details
        holder.date.text = list[position].getFullDateString()

        var Id: Int = list[position].id

        holder.itemView.setOnClickListener {
            val action = AllTasksFragmentDirections.actionAllTasksFragmentToTaskDetails(Id)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.setOnLongClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val db = DatabaseClass.getDatabase(requireActivity)
                db.DaoTasks().deleteUsers(list[position])
                withContext(Dispatchers.Main) {
                    list.removeAt(position)
                    notifyItemRemoved(position)
                    Toast.makeText(requireActivity, "Task Delete", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
