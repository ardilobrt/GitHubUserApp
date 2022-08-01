package id.co.ardilobrt.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.co.ardilobrt.githubuser.data.model.UsersItem
import id.co.ardilobrt.githubuser.databinding.ItemRowUserBinding
import id.co.ardilobrt.githubuser.utils.MyUtils.loadImage

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    var isClickable = true

    private val listUser = ArrayList<UsersItem>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(user: ArrayList<UsersItem>) {
        listUser.apply {
            clear()
            addAll(user)
        }
    }

    inner class ViewHolder(private var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersItem) {
            binding.apply {
                imgItemPhoto.loadImage(user.avatarUrl)
                tvItemUsername.text = user.login
            }
            if (isClickable) {
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersItem)
    }
}