package es.iessaladillo.pedrojoya.pr08.ui.users

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.pr08.data.DataSource

class UserActivityViewModelFactory(private val repository: DataSource, private val application: Application) : ViewModelProvider.Factory  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        UsersActivityViewModel(repository, application) as T
}