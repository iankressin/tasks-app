package com.unisul.tasklist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.unisul.tasklist.dao.TaskDao
import com.unisul.tasklist.enums.PriorityTypes
import com.unisul.tasklist.helpers.dateToString
import com.unisul.tasklist.helpers.stringToDate
import com.unisul.tasklist.helpers.isDateValid
import com.unisul.tasklist.helpers.stringToPriorityType
import com.unisul.tasklist.models.Task
import kotlinx.android.synthetic.main.fragment_first.view.*

class FormFragment : Fragment() {
    lateinit var name: String;
    lateinit var description: String;
    lateinit var dueDate: String;
    lateinit var priority: String;

    lateinit var radioGroup: RadioGroup;
    lateinit var errorTextView: TextView;

    lateinit var parentView: View;

    var taskId: Int? = null;

    var taskDao = TaskDao

    companion object {
        const val ARG_NAME = "taskId"

        fun newInstance(taskId: String): FormFragment {
            val fragment = FormFragment()

            val bundle = Bundle().apply {
                putString(ARG_NAME, taskId)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        this.taskId = arguments?.getString(ARG_NAME)?.toInt() ?: null

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.parentView = view

        this.taskId?.let { populateFields(it) }

        view.findViewById<Button>(R.id.voltar).setOnClickListener{
            this.navigateToList();
        }

        view.findViewById<Button>(R.id.cadastrar).setOnClickListener {
            this.handleNewTask()
        }
    }

    fun handleNewTask() {
        errorTextView = this.parentView.findViewById(R.id.formError)

        try {
            var task = newTask(this.parentView)

            taskDao.addTask(task);

            this.navigateToList();

            errorTextView.text = ""
        } catch (err: java.lang.Error) {
            errorTextView.text = err.message
        }
    }

    private fun populateFields(taskId: Int) {
        val task = taskDao.getTask(taskId)

//        Log.d("HANDLE UPDATE TASK", "$task")

        this.parentView.findViewById<EditText>(R.id.nameField).setText(task.name)
        this.parentView.findViewById<EditText>(R.id.descriptionField).setText(task.description)
        this.parentView.findViewById<EditText>(R.id.dueDateField).setText(dateToString(task.dueDate))

        var radioGroup = this.parentView.findViewById<RadioGroup>(R.id.priorityField)

        when(task.priority) {
            PriorityTypes.MEDIUM -> radioGroup.check(R.id.media)
            PriorityTypes.LOW -> radioGroup.check(R.id.baixa)
            PriorityTypes.HIGH -> radioGroup.check(R.id.alta)
        }
    }

    fun handleUpdateTask(taskId: Int) {
        errorTextView = this.parentView.findViewById(R.id.formError)
        Log.d("HANDLING TASK: ", taskId.toString())

        try {
            var task = newTask(this.parentView)

            taskDao.updateTask(taskId, task);

            this.navigateToList();

            errorTextView.text = ""
        } catch (err: java.lang.Error) {
            errorTextView.text = err.message
        }
    }

    private fun navigateToList() {
        activity?.let {
            val intent = Intent(it, TaskList::class.java);
            it.startActivity(intent);
        }
    }

    private fun newTask(view: View): Task {
        name = view.findViewById<EditText>(R.id.nameField).text.toString();
        description = view.findViewById<EditText>(R.id.descriptionField).text.toString();
        dueDate = view.findViewById<EditText>(R.id.dueDateField).text.toString();
        radioGroup = view.findViewById<RadioGroup>(R.id.priorityField);

        val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId

        if (selectedRadioButtonId != -1) {
            priority = view.findViewById<RadioButton>(selectedRadioButtonId).text.toString()
        } else {
            priority = ""
        }

        try {
            validateForm(name, description, dueDate, priority)

            return Task(name, description, stringToDate(dueDate), stringToPriorityType(priority))
        } catch (err: Error) {
            throw err
        }
    }

    private fun validateForm(name: String, description: String, dueDate: String, priority: String): Boolean {
        if (name == null || name == "") throw Error("O campo NOME e invalido")
        if (description == null || description == "") throw Error("O campo DESCRICAO e invalido")
        if (dueDate == null || dueDate == "" || !isDateValid(dueDate)) throw Error("O campo PRAZO e invalido")
        if (priority == "") throw Error("O campo PRIORIDADE e invalido")

        return true
    }
}