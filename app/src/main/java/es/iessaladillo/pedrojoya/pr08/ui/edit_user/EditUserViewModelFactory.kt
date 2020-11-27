package es.iessaladillo.pedrojoya.pr08.ui.edit_user

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import es.iessaladillo.pedrojoya.pr08.data.DataSource
import es.iessaladillo.pedrojoya.pr08.data.model.User
import es.iessaladillo.pedrojoya.pr08.ui.add_user.AddUserViewModel

class EditUserViewModelFactory(
        private val user: User,
        private val repository: DataSource,
        private val application: Application,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null,
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T =
            EditUserViewModel(user, handle, repository, application) as T

}