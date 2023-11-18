package com.bayutb123.tukerin.data.source.remote.response.home

import com.google.gson.annotations.SerializedName

data class SuggestionsResponse(

	@field:SerializedName("suggestions")
	val suggestions: List<SuggestionsItem>
)

data class SuggestionsItem(

	@field:SerializedName("title")
	val title: String
)
