package com.example.samplemovie.data.model

class ReviewerData (
    val results: List<ReviewData>? = emptyList()
)

data class ReviewData(
    var author: String? = "",
    var avatar_path: String? = "",
    var content: String? = "",
)