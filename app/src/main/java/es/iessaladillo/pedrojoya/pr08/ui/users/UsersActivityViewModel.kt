package es.iessaladillo.pedrojoya.pr08.ui.users

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr08.data.DataSource
import es.iessaladillo.pedrojoya.pr08.data.model.User

class  UsersActivityViewModel(private val repository: DataSource, private val application: Application) : ViewModel() {

    val students: LiveData<List<User>> = repository.getAllUsersOrderedByName()

    fun addStudent(student: User) {
        repository.insertUser(student)
    }

    fun edit(student: User) {
        // TODO
        repository.updateUser(student)
    }

    fun delete(student: User) {
        repository.deleteUser(student)
    }
}
