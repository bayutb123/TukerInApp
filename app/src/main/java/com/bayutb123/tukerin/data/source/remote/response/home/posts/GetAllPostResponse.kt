package com.bayutb123.tukerin.data.source.remote.response.home.posts

data class GetAllPostResponse(
	val message: String,
	val pagination: Pagination,
	val posts: List<PostResponse>
)