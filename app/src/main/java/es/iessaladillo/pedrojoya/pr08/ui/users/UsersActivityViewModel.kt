package es.iessaladillo.pedrojoya.pr08.ui.users

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr08.R
import es.iessaladillo.pedrojoya.pr08.data.DataSource
import es.iessaladillo.pedrojoya.pr08.data.model.User
import es.iessaladillo.pedrojoya.pr08.utils.Event

class  UsersActivityViewModel(private val repository: DataSource, private val application: Application) : ViewModel() {

    val students: LiveData<List<User>> = repository.getAllUsersOrderedByName()

    private val _onShowMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val onShowMessage: LiveData<Event<String>>
        get() = _onShowMessage

    private lateinit var deletedUser: User

    fun addStudent() {
        repository.recoveryUser(deletedUser)
    }

    fun delete(user: User) {
        deletedUser = user
        repository.deleteUser(user)
        _onShowMessage.value = Event(application.getString(R.string.users_deleted, user.name))
    }
}
