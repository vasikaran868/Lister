package com.example.innobuzzassignment.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.innobuzzassignment.data.PostsRepositary
import com.example.innobuzzassignment.data.Room.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostViewModel(
    val postsRepositary: PostsRepositary
): ViewModel() {

    val postStream = postsRepositary.getPostsStream()

    private val _postList : MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val postList : LiveData<List<Post>> get() = _postList

    init {
        observeStream()
    }

    fun observeStream(){
        viewModelScope.launch {
            postStream.collect{
                Log.v("MyActivity","flow collector...${it}")
                _postList.value = it
            }
        }
    }

    fun getPosts(){
        viewModelScope.launch{
            postsRepositary.getPosts()
        }
    }

}

class PostsViewModelFactory(private val postsRepositary: PostsRepositary) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(postsRepositary) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}