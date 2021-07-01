package com.unisul.tasklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.fragment.app.Fragment
import com.unisul.tasklist.dao.TaskDao

class CreateTask : AppCompatActivity() {
    var taskDao = TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        var taskId = intent.getStringExtra("taskId");

        val fragment = FormFragment.newInstance(taskId)

        if (taskId != null) {
            Log.d("TASK ID", "$taskId")

            replaceFragment(fragment)
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            Log.d("DELETE: ", taskId)

            taskDao.removeTask(taskId.toInt())

            val intent = Intent(this, TaskList::class.java);
            startActivity(intent);
        }

        findViewById<Button>(R.id.cadastrar).setOnClickListener {
            if (taskId == null) {
                fragment.handleNewTask();
            } else {
                fragment.handleUpdateTask(taskId.toInt())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.form, fragment)
        transaction.commit()
    }
}