package es.iessaladillo.pedrojoya.pr08.ui.add_user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr08.R
import es.iessaladillo.pedrojoya.pr08.data.DataSource
import es.iessaladillo.pedrojoya.pr08.data.model.User
import es.iessaladillo.pedrojoya.pr08.utils.Event
import java.util.*

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.
// Para obtener un URL de foto de forma aleatoria (tendrás que definir
// e inicializar el random a nivel de clase.

const val STATE_IMG = "STATE_IMG"

class AddUserViewModel(
        savedStateHandle: SavedStateHandle,
        private val repository: DataSource,
        private val application: Application) : ViewModel() {

    private val random: Random = Random()
    private var counterId: Long = 1

    private val _randomImage: MutableLiveData<String> =
            savedStateHandle.getLiveData(STATE_IMG, getRandomPhotoUrl())
    val randomImage: LiveData<String>
        get() = _randomImage

    private val _onShowMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val onShowMessage: LiveData<Event<String>>
        get() = _onShowMessage

    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${random.nextInt(100)}/400/300"

    fun changeRandomImage() {
        _randomImage.value = getRandomPhotoUrl()
    }

    fun saveUser(user: User) {
        val userToAdd: User = user.copy(id = counterId++)
        repository.insertUser(userToAdd)
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
}
