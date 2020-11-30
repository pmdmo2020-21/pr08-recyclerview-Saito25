package es.iessaladillo.pedrojoya.pr08.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr08.data.model.User

object DataBase : DataSource {
    private var idCounter: Long = 1
    private val users: MutableList<User> = mutableListOf()
    private val usersLiveDate: MutableLiveData<List<User>> = MutableLiveData()

    init {
        updateStudentsLiveDate()
    }

    override fun getAllUsersOrderedByName(): LiveData<List<User>> =
            usersLiveDate

    override fun insertUser(user: User) {
        val userToAdd = user.copy(id = idCounter++)
        users.add(userToAdd)
        updateStudentsLiveDate()
    }

    override fun recoveryUser(user: User) {
        users.add(user)
        updateStudentsLiveDate()
    }

    override fun updateUser(user: User) {
        val position: Int = users.indexOfFirst { it.id == user.id }
        if(position >= 0) {
            users[position] = user
            updateStudentsLiveDate()
        }
    }

    override fun deleteUser(user: User) {
        val position: Int = users.indexOfFirst { it.id == user.id }
        if(position >= 0) {
            users.removeAt(position)
            updateStudentsLiveDate()
        }
    }

    private fun updateStudentsLiveDate() {
        usersLiveDate.value = users.sortedBy { it.name }
    }
}
// TODO:
//  Crear una singleton Database que implemente la interfaz DataSource.
//  Al insertar un usuario, se le asignará un id autonumérico
//  (primer valor válido será el 1) que deberá ser gestionado por la Database.
//  La base de datos debe ser reactiva, de manera que cuando se agrege,
//  actualice o borre un usuario, los observadores de la lista deberán
//  recibir la nueva lista.
//  Al consultar los usuario se deberá retornar un LiveData con la lista
//  de usuarios ordenada por nombre

