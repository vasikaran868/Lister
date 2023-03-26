package com.example.innobuzzassignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.innobuzzassignment.R
import com.example.innobuzzassignment.data.Room.Post
import com.example.innobuzzassignment.databinding.FragmentPostDetailsBinding
import kotlinx.coroutines.flow.combine


class PostDetails : Fragment() {

    private var _binding : FragmentPostDetailsBinding? = null
    val binding get() = _binding!!

    lateinit var post: Post


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        post = arguments?.let { it.getParcelable<Post>("post")!!}!!

//        postponeEnterTransition()
//        requireView().doOnPreDraw {
//            startPostponedEnterTransition()
//        }

        binding.apply {
            postIdTv.text = "Post Id: ${post.id}"
            userIdTv.text = "User Id: ${post.userId}"
            titleTv.text = post.title
            titleTv.transitionName = "Transition${post.id}"
            bodyTv.text = post.body
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

    }

}