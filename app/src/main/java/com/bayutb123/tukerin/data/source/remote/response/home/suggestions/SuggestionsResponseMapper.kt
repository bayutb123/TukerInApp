package com.bayutb123.tukerin.data.source.remote.response.home.suggestions

fun SuggestionsResponse.toSuggestionsList() : List<String> {
    return this.suggestions.map { it.title }
}