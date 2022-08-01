package id.co.ardilobrt.githubuser.ui.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.ardilobrt.githubuser.adapter.UserAdapter
import id.co.ardilobrt.githubuser.utils.MyUtils
import id.co.ardilobrt.githubuser.utils.MyUtils.showLoading
import id.co.ardilobrt.githubuser.data.model.UsersItem
import id.co.ardilobrt.githubuser.databinding.FragmentFollowBinding

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FollowViewModel>()
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)

        val argument = arguments
        username = argument?.getString(MyUtils.EXTRA_USER)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (username != null) {
            viewModel.getFollower(username!!)
        }

        viewModel.listFollow.observe(viewLifecycleOwner) {
            if (it != null) showUser(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.includeProgressBar.progressBar.showLoading(it)
        }

    }
    private fun showUser(listUser: ArrayList<UsersItem>) {
        val linearLayoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        val userAdapter = UserAdapter()

        userAdapter.setList(listUser)
        userAdapter.isClickable = false

        binding.rvUsers.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(itemDecoration)
            adapter = userAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}