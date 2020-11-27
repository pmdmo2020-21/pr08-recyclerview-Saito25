package es.iessaladillo.pedrojoya.pr08.ui.users

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr08.R
import es.iessaladillo.pedrojoya.pr08.data.DataBase
import es.iessaladillo.pedrojoya.pr08.data.model.User
import es.iessaladillo.pedrojoya.pr08.databinding.UsersActivityBinding
import es.iessaladillo.pedrojoya.pr08.ui.add_user.AddUserActivity
import es.iessaladillo.pedrojoya.pr08.ui.edit_user.EditUserActivity
import es.iessaladillo.pedrojoya.pr08.utils.setOnSwipeListener

class UsersActivity : AppCompatActivity() {

    private val viewModel: UsersActivityViewModel by viewModels {
        UserActivityViewModelFactory(DataBase, application)
    }

    private val listAdapter: UserActivityAdapter = UserActivityAdapter().apply {
        onEditClickListener = { editUser(it) }
        onDeleteClickListener = { deleteUser(it) }
    }

    private val binding: UsersActivityBinding by lazy {
        UsersActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        observeUsers()
    }

    private fun setupViews() {
        setupRecyclerView()
        binding.ivUsersAddIcon.setOnClickListener { navigateToAddUser() }
    }

    private fun setupRecyclerView() {
        binding.rvUsersUsersList.apply {
            setHasFixedSize(true)
            adapter = listAdapter
            layoutManager = GridLayoutManager(this@UsersActivity, resources.getInteger(R.integer.users_grid_columns))
            addItemDecoration(DividerItemDecoration(this@UsersActivity, RecyclerView.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            setOnSwipeListener(swipeDirs = ItemTouchHelper.RIGHT) { viewHolder, direction ->
                if (direction == ItemTouchHelper.RIGHT) {
                    deleteUser(viewHolder.bindingAdapterPosition)
                }
            }
        }
    }

    private fun observeUsers() {
        viewModel.students.observe(this) { updateList(it) }
    }

    private fun navigateToAddUser() {
        val intentToAddUserActivity = AddUserActivity.newIntent(this)
        startActivity(intentToAddUserActivity)
    }

    private fun updateList(newList: List<User>) {
        listAdapter.submitList(newList)
        binding.ivUsersAddIcon.visibility = if (newList.isEmpty()) View.VISIBLE else View.GONE
        binding.txtUsersAddUser.visibility = if (newList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun editUser(position: Int) {
        val user: User = listAdapter.currentList[position]
        navigateToEditUserActivity(user)
    }

    private fun navigateToEditUserActivity(user: User) {
        val intentToEditUserActivity = EditUserActivity.newIntent(this, user)
        startActivity(intentToEditUserActivity)
    }

    private fun deleteUser(position: Int) {
        val user: User = listAdapter.currentList[position]
        viewModel.delete(user)
        showUndo(user)
    }

    private fun showUndo(user: User) {
        Snackbar.make(
                binding.root,
                getString(R.string.users_deleted, user.name),
                Snackbar.LENGTH_LONG)
                .setAction(R.string.users_undo) { viewModel.addStudent(user) }
                .show()
    }


    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.users, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuAdd) {
            onAddUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // FIN NO TOCAR

    private fun onAddUser() {
        navigateToAddUser()
    }

}