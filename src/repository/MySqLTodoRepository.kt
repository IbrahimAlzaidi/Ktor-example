package repository

import database.DatabaseManager
import entities.ToDo
import entities.ToDoDraft

class MySqLTodoRepository : ToDoRepository {

    private val database = DatabaseManager()

    override fun getAllToDos(): List<ToDo> {
        return database.getAllTodos()
            .map { ToDo(it.id, it.title, it.done) }
    }

    override fun getToDo(id: Int): ToDo? {
        TODO("Not yet implemented")
    }

    override fun addToDo(draft: ToDoDraft): ToDo {
        TODO("Not yet implemented")
    }

    override fun removeToDo(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateToDo(id: Int, draft: ToDoDraft): Boolean {
        TODO("Not yet implemented")
    }
}