package com.example.primarydetail.posts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.primarydetail.R
import com.example.primarydetail.databinding.PostDetailItemBinding
import com.example.primarydetail.posts.domain.model.Post
import com.example.primarydetail.posts.ui.PostListAdapter.Companion.POST_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels()
    private var post: Post? = null

    private var _binding: PostDetailItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        /**
         * Declare our binding and inflate the post_detail_item layout
         * [PostDetailItemBinding] is automatically created with the <layout> tag in the layout
         */
        _binding = PostDetailItemBinding.inflate(
            LayoutInflater.from(context)
        )

        // Get the post from the args
        arguments?.let {
            if (it.containsKey(POST_ID)) {
                lifecycleScope.launch {
                    post = viewModel.postById(it.getLong(POST_ID))

                    binding.titleTextView.text = post?.title
                    binding.bodyTextView.text = post?.body
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.delete -> {
                        post?.let { viewModel.deletePost(it.id) }
                        findNavController().navigateUp()
                    }

                    else -> return false
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
