package com.unisul.tasklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.unisul.tasklist.dao.TaskDao

class TaskList : AppCompatActivity() {
    private var taskDao = TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Minhas Tarefas"

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            Log.d("TASK LIST", "Changing");
            setContentView(R.layout.activity_main)
        }

        this.loadTaskList();
    }

    private fun loadTaskList() {
        var adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.task_item, this.taskDao.getTaskTitles())

        var listView = findViewById<ListView>(R.id.taskList)

        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val itemValue = listView.getItemAtPosition(position) as String

                Log.d("Item clicked", position.toString());

                val intent = Intent(this@TaskList, CreateTask::class.java);

                intent.putExtra("taskId", position.toString());

                startActivity(intent);
            }
    }
}