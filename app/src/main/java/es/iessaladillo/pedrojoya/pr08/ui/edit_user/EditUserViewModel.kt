package es.iessaladillo.pedrojoya.pr08.ui.edit_user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr08.R
import es.iessaladillo.pedrojoya.pr08.data.DataSource
import es.iessaladillo.pedrojoya.pr08.data.model.User
import es.iessaladillo.pedrojoya.pr08.ui.add_user.STATE_IMG
import es.iessaladillo.pedrojoya.pr08.utils.Event
import java.util.*

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

class EditUserViewModel(
        receiveUser: User,
        savedStateHandle: SavedStateHandle,
        private val repository: DataSource,
        private val application: Application) : ViewModel() {

    private val random: Random = Random()

    private val _randomImage: MutableLiveData<String> =
            savedStateHandle.getLiveData(STATE_IMG, receiveUser.photoUrl)
    val randomImage: LiveData<String>
        get() = _randomImage

    private val _user: MutableLiveData<User> = MutableLiveData(receiveUser)
    val user: LiveData<User>
        get() = _user

    private val _onShowMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val onShowMessage: LiveData<Event<String>>
        get() = _onShowMessage

    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${random.nextInt(100)}/400/300"

    fun changeRandomImage() {
        _randomImage.value = getRandomPhotoUrl()
    }

    fun checkFormFields(vararg inputField: String): Boolean {
        for (fild in inputField) {
            if(fild.isBlank()) {
                _onShowMessage.value = Event(application.getString(R.string.user_invalid_data))
                return false
            }
        }
        return true
    }

    fun editUser(user: User) {
        repository.updateUser(user)
    }
}
