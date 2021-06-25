package com.unisul.tasklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.util.Log
import android.widget.*
import com.unisul.tasklist.helpers.stringToDate
import com.unisul.tasklist.helpers.stringToPriorityType
import com.unisul.tasklist.models.Task


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    lateinit var name: String;
    lateinit var description: String;
    lateinit var dueDate: String;
    lateinit var priority: String;

    lateinit var radioGroup: RadioGroup;
    lateinit var errorTextView: TextView;

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.cadastrar).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            errorTextView = view.findViewById(R.id.formError)

            try {
                var task = newTask(view)

                errorTextView.text = ""
            } catch (err: java.lang.Error) {
                errorTextView.text = err.message
            }
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
        if (dueDate == null || dueDate == "") throw Error("O campo PRAZO e invalido")
        if (priority == "") throw Error("O campo PRIORIDADE e invalido")

        return true
    }
}