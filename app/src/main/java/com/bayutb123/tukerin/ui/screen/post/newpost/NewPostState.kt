package com.bayutb123.tukerin.ui.screen.post.newpost

sealed class NewPostState(val statusCode : Int? = null) {
    data class Success(val code: Int) :
        NewPostState(statusCode = code)
    data class Failed(val code: Int) : NewPostState(statusCode = code)
    data object Loading : NewPostState()
    data object Idle : NewPostState()
}
