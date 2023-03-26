package com.example.innobuzzassignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.innobuzzassignment.R
import com.example.innobuzzassignment.data.Room.Post
import com.example.innobuzzassignment.databinding.PostVhBinding

class PostsRecViewAdapter(val toDetails:(Post , TextView)-> Unit):
    ListAdapter<Post, PostsRecViewAdapter.PostViewHolder>(
        Diffcallback
    ) {

    class PostViewHolder(private var binding: PostVhBinding): RecyclerView.ViewHolder(binding.root){

        val titleTv = binding.titleTv

        fun bind(post: Post){
            binding.apply {
                binding.titleTv.text = post.title
                binding.titleTv.transitionName = "Transition${post.id}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            PostVhBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
        holder.itemView.setOnClickListener {
            toDetails(post, holder.titleTv)
        }
    }
    companion object{
        private val Diffcallback = object: DiffUtil.ItemCallback<Post>(){
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

}