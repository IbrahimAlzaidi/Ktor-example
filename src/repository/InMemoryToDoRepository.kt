package repository

import entities.ToDo
import entities.ToDoDraft

class InMemoryToDoRepository : ToDoRepository {

    private val todos = mutableListOf<ToDo>(
        ToDo(1, "First Note", true),
        ToDo(2, "Two Note", true),
        ToDo(3, "Three Note", true),
        ToDo(4, "Four Note", true),
        ToDo(5, "Five Note", true),
        ToDo(6, "Six Note", true),
        ToDo(7, "Seven Note", true),
        ToDo(8, "Eight Note", true),
        ToDo(9, "Nine Note", true),
    )

    override fun getAllToDos(): List<ToDo> = todos

    override fun getToDo(id: Int): ToDo? = todos.find { it.id == id }

    override fun addToDo(draft: ToDoDraft): ToDo {

        val newTodo = ToDo(
            id = todos.size + 1,
            title = draft.title,
            done = draft.done)

         todos.add(newTodo)

        return newTodo
    }

    override fun removeToDo(id: Int): Boolean = todos.removeIf { it.id == id }

    override fun updateToDo(id: Int, draft: ToDoDraft): Boolean {
        val todo = todos.firstOrNull{ it.id == id}
            ?: return false

        todo.title = draft.title
        todo.done = draft.done
        return true
    }
}