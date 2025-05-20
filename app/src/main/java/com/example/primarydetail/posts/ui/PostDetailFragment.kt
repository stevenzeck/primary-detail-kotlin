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
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.primarydetail.R
import com.example.primarydetail.databinding.PostDetailItemBinding
import com.example.primarydetail.posts.domain.model.Post
import com.example.primarydetail.posts.ui.PostListAdapter.Companion.POST_ID
import com.example.primarydetail.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels()
    private var currentPost: Post? = null

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the post from the args
        arguments?.let {
            if (it.containsKey(POST_ID)) {
                val postId = it.getLong(POST_ID)
                viewModel.fetchPostById(postId)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedPost.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.titleTextView.text = getString(R.string.loading_post_details)
                            binding.bodyTextView.text = ""
                            currentPost = null
                        }

                        is Resource.Success -> {
                            currentPost = resource.data
                            if (currentPost != null) {
                                binding.titleTextView.text = currentPost?.title
                                binding.bodyTextView.text = currentPost?.body
                            } else {
                                binding.titleTextView.text = getString(R.string.post_not_found)
                                binding.bodyTextView.text = ""
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.post_not_found),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }

                        is Resource.Error -> {
                            currentPost = null
                            binding.titleTextView.text = getString(R.string.error_loading_post)
                            binding.bodyTextView.text =
                                resource.message ?: getString(R.string.unknown_error_occurred)
                            resource.message?.let {
                                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.delete -> {
                        currentPost?.let { viewModel.deletePost(it.id) }
                        if (isAdded) {
                            findNavController().navigateUp()
                        }
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