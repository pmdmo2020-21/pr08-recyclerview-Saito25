package es.iessaladillo.pedrojoya.pr08.data

import androidx.lifecycle.LiveData
import es.iessaladillo.pedrojoya.pr08.data.model.User

interface DataSource {

    fun getAllUsersOrderedByName(): LiveData<List<User>>
    fun insertUser(user: User)
    fun updateUser(user: User)
    fun deleteUser(user: User)
    fun recoveryUser(user: User)

}