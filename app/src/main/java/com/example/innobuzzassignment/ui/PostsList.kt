package com.example.innobuzzassignment.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.innobuzzassignment.MyApp
import com.example.innobuzzassignment.R
import com.example.innobuzzassignment.databinding.FragmentPostsListBinding
import com.example.innobuzzassignment.ui.adapters.PostsRecViewAdapter

class PostsList : Fragment() {

    private var _binding:FragmentPostsListBinding? = null
    val binding get() = _binding!!

    val viewModel : PostViewModel by activityViewModels {
        PostsViewModelFactory(
            (activity?.application as MyApp).postsRepositary
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (checkAccessibilityPermission()){
            binding.reqPerBtn.apply {
                background = resources.getDrawable(R.drawable.service_permission_btn_bg_red)
                text = "DISABLE ACCCESSIBILITY SERVICE"
            }
        }
        else{
            binding.reqPerBtn.apply {
                background = resources.getDrawable(R.drawable.service_permission_btn_bg_green)
                text = "ENABLE ACCESSIBILITY SERVICE"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        viewModel.getPosts()

        val adapter = PostsRecViewAdapter(){ post, titleTv ->
            val extras = FragmentNavigatorExtras(
                titleTv to "Transition${post.id}"
            )
            findNavController().navigate(PostsListDirections.actionPostsListToPostDetails(post),extras)
        }
        binding.postsRecView.adapter = adapter
        binding.postsRecView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.postList.observe(viewLifecycleOwner,{
            Log.v("MyActivity","received...${it}")
            adapter.submitList(it.toList())
        })

        binding.reqPerBtn.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    fun checkAccessibilityPermission(): Boolean {
        var accessEnabled = 0
        try {
            accessEnabled =
                Settings.Secure.getInt(requireActivity().contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        return accessEnabled != 0
    }

}