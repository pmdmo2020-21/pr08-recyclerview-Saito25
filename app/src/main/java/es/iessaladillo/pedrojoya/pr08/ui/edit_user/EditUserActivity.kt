package es.iessaladillo.pedrojoya.pr08.ui.edit_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr08.R
import es.iessaladillo.pedrojoya.pr08.data.DataBase
import es.iessaladillo.pedrojoya.pr08.data.model.User
import es.iessaladillo.pedrojoya.pr08.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr08.utils.SoftInputUtils
import es.iessaladillo.pedrojoya.pr08.utils.loadUrl
import es.iessaladillo.pedrojoya.pr08.utils.observeEvent

class EditUserActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  Ten en cuenta que la actividad debe recibir a través del intent
    //  con el que es llamado el objeto User corresondiente
    //  ...

    companion object {
        const val EXTRA_USER: String = "EXTRA_USER"
        fun newIntent(context: Context, user: User): Intent {
            return Intent(context, EditUserActivity::class.java)
                    .putExtra(EXTRA_USER, user)
        }
    }

    private lateinit var user: User

    private val binding: UserActivityBinding by lazy {
        UserActivityBinding.inflate(layoutInflater)
    }

    private val viewModel: EditUserViewModel by viewModels {
        EditUserViewModelFactory(user, DataBase, application, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        receiveData()
        setupViews()
        observe()
    }

    private fun receiveData() {
        user = intent.getParcelableExtra(EXTRA_USER) ?: throw java.lang.RuntimeException()
    }

    private fun setupViews() {
        binding.ivUserAvatar.setOnClickListener { viewModel.changeRandomImage() }
        binding.etxtUserWeb.setOnEditorActionListener { v, actionId, event ->
            onSave()
            true
        }
    }

    private fun observe() {
        observeUser()
        observeImage()
        observeMessage()
    }

    private fun observeUser() {
        viewModel.user.observe(this) { printInfoInFields(it) }
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


    private fun printInfoInFields(user: User) {
        user.run {
            binding.etxtUserName.text = stringToEditable(name)
            binding.etxtUserEmail.text = stringToEditable(email)
            binding.etxtUserWeb.text = stringToEditable(web)
            binding.etxtUserPhone.text = stringToEditable(phoneNumber)
            binding.ivUserAvatar.loadUrl(photoUrl)
        }
    }

    private fun stringToEditable(string: String): Editable =
            Editable.Factory.getInstance().newEditable(string)

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
        SoftInputUtils.hideSoftKeyboard(binding.root)

        if (viewModel.checkFormFields(name, email, phoneNumber)) {
            val address = binding.etxtUserAddress.text.toString()
            val web = binding.etxtUserWeb.text.toString()
            val photoURL = viewModel.randomImage.value ?: ""
            val user = User(viewModel.user.value?.id
                    ?: 0, name, email, address, phoneNumber, web, photoURL)
            viewModel.editUser(user)
            finish()
        }
    }

}