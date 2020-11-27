package es.iessaladillo.pedrojoya.pr08.ui.add_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr08.R
import es.iessaladillo.pedrojoya.pr08.data.DataBase
import es.iessaladillo.pedrojoya.pr08.data.model.User
import es.iessaladillo.pedrojoya.pr08.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr08.utils.SoftInputUtils.hideSoftKeyboard
import es.iessaladillo.pedrojoya.pr08.utils.loadUrl
import es.iessaladillo.pedrojoya.pr08.utils.observeEvent

class AddUserActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddUserActivity::class.java)
        }
    }

    private val binding: UserActivityBinding by lazy {
        UserActivityBinding.inflate(layoutInflater)
    }

    private val viewModel: AddUserViewModel by viewModels {
        AddUserViewModelFactory(DataBase, application, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        observe()
    }

    private fun setupViews() {
        binding.ivUserAvatar.setOnClickListener { viewModel.changeRandomImage() }
        binding.etxtUserWeb.setOnEditorActionListener { v, actionId, event ->
            onSave()
            true
        }
    }

    private fun observe() {
        observeImage()
        observeMessage()
    }

    private fun observeImage() {
        viewModel.randomImage.observe(this) { changeImage(it) }
    }

    private fun observeMessage() {
        viewModel.onShowMessage.observeEvent(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun changeImage(imageUrl: String) {
        binding.ivUserAvatar.loadUrl(imageUrl)
    }

    // NO TOCAR: Estos métodos gestionan el menú y su gestión
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            onSave()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // FIN NO TOCAR

    private fun onSave() {
        val name = binding.etxtUserName.text.toString()
        val email = binding.etxtUserEmail.text.toString()
        val phoneNumber = binding.etxtUserPhone.text.toString()
        hideSoftKeyboard(binding.root)

        if (viewModel.checkFormFields(name, email, phoneNumber)) {
            val address = binding.etxtUserAddress.text.toString()
            val web = binding.etxtUserWeb.text.toString()
            val photoURL = viewModel.randomImage.value ?: ""
            val user = User(0, name, email, address, phoneNumber, web, photoURL)
            viewModel.saveUser(user)
            finish()
        }
    }


}