package es.iessaladillo.pedrojoya.pr08.ui.add_user

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import es.iessaladillo.pedrojoya.pr08.data.DataSource

class AddUserViewModelFactory(
        private val repository: DataSource,
        private val application: Application,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null,
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T =
            AddUserViewModel(handle, repository, application) as T
}