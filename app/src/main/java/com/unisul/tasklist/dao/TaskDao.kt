package com.unisul.tasklist.dao

import com.unisul.tasklist.enums.PriorityTypes
import com.unisul.tasklist.helpers.stringToDate
import com.unisul.tasklist.models.Task
import android.util.Log
import android.widget.ArrayAdapter

object TaskDao {
    private var list: ArrayList<Task> = ArrayList();

    init {
        this.initData()
    }

    private fun initData() {
        this.list.add(Task("AD3 - Linguagens Formais", "Fazer um analisador lexico que retorna tokens e tabela de simbolos", stringToDate("25/06/2021"), PriorityTypes.HIGH))
        this.list.add(Task("AD2 - Dispositivos Moveis", "Terminar esse app", stringToDate("01/07/2021"), PriorityTypes.HIGH))
        this.list.add(Task("AD2 - Processos de Software", "Fazer as questoes 2 e 3 do livro didatico", stringToDate("26/07/2021"), PriorityTypes.HIGH))
        this.list.add(Task("Sliders de Volume", "Adicionar components sliders aos controllers the volume no Wayports", stringToDate("25/05/2022"), PriorityTypes.MEDIUM))
    }

    fun getTasks(): ArrayList<Task> {
        return list
    }

    fun getTask(id: Int): Task {
        return this.list[id];
    }

    fun addTask(task: Task) {
        list.add(task)
    }

    fun removeTask(index: Int): Task {
        return list.removeAt(index)
    }

    fun updateTask(index: Int, task: Task) {
        list[index] = task
    }

    fun getTaskTitles(): List<String> {
        return list.map { it.name }
    }
}