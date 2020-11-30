package es.iessaladillo.pedrojoya.pr08.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr08.data.model.User
import es.iessaladillo.pedrojoya.pr08.databinding.UsersActivityItemBinding
import es.iessaladillo.pedrojoya.pr08.utils.loadUrl

typealias OnEditClickListener = (position: Int) -> Unit
typealias OnDeleteClickListener = (position: Int) -> Unit

class UserActivityAdapter:  ListAdapter<User, UserActivityAdapter.ViewHolder>(UserDiffCallback) {

    var onEditClickListener: OnEditClickListener? = null
    var onDeleteClickListener: OnDeleteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UsersActivityItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = currentList[position]
        holder.bind(student)
    }

    inner class ViewHolder(private val binding: UsersActivityItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnUsersItemEdit.setOnClickListener {
                val position = bindingAdapterPosition

                if(position != RecyclerView.NO_POSITION) {
                    onEditClickListener?.invoke(position)
                }
            }

            binding.btnUsersItemDelete.setOnClickListener {
                val position = bindingAdapterPosition

                if(position != RecyclerView.NO_POSITION) {
                    onDeleteClickListener?.invoke(position)
                }
            }
        }

        fun bind(student: User) {
            student.run {
                binding.txtUsersItemEmail.text = email
                binding.txtUsersItemName.text = name
                binding.txtUsersItemPhone.text = phoneNumber
                binding.ivUsersItemAvatar.loadUrl(photoUrl)
            }
        }
    }

    object UserDiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem

    }
}