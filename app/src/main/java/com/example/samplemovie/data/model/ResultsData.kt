package com.example.samplemovie.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class ResponseData (
    val results: List<ResultsData>? = emptyList()
)
@Parcelize
data class ResultsData(
    var id: Int? = 0,
    var title: String? = "",
    var name: String? = "",
    var vote_average: String? = "",
    var popularity: String? = "",
    var poster_path: String? = "",
    var backdrop_path: String? = "",
    var overview: String? = "",
    var release_date: String? = ""
):Parcelable{
    override fun toString(): String {
        return "ResultsData(id=$id, title=$title, name=$name, vote_average=$vote_average, popularity=$popularity, poster_path=$poster_path, backdrop_path=$backdrop_path, overview=$overview, release_date=$release_date)"
    }
}

