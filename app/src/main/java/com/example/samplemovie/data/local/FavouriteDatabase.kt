package com.example.samplemovie.data.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.samplemovie.data.model.ResultsData

class FavouriteDatabase(context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "CREATE TABLE favourite_movie (id INTEGER,title TEXT,name TEXT,vote_average TEXT,popularity TEXT,poster_path TEXT,backdrop_path TEXT,overview TEXT,release_date TEXT)"
        p0!!.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS favourite_movie")
        onCreate(p0)
    }

    companion object {
        private const val DATABASENAME = "movie"
        private const val DATABASEVERSION = 1
    }

    fun addFavouriteMovie(favMovie: ResultsData?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("id", favMovie?.id)
        values.put("title", favMovie?.title)
        values.put("name", favMovie?.name)
        values.put("vote_average", favMovie?.vote_average)
        values.put("popularity", favMovie?.popularity)
        values.put("poster_path", favMovie?.poster_path)
        values.put("backdrop_path", favMovie?.backdrop_path)
        values.put("overview", favMovie?.overview)
        values.put("release_date", favMovie?.release_date)
        db.insert("favourite_movie", null, values)
        db.close()
    }

    @SuppressLint("Recycle")
    fun getAllFavouriteMovie(): List<ResultsData> {
        val db = this.writableDatabase
        val list = ArrayList<ResultsData>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM favourite_movie", null)
        if (cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val voteAvg = cursor.getString(cursor.getColumnIndex("vote_average"))
                val popularity = cursor.getString(cursor.getColumnIndex("popularity"))
                val poster = cursor.getString(cursor.getColumnIndex("poster_path"))
                val backdrop = cursor.getString(cursor.getColumnIndex("backdrop_path"))
                val overview = cursor.getString(cursor.getColumnIndex("overview"))
                val releaseDate = cursor.getString(cursor.getColumnIndex("release_date"))
                val favMovie = ResultsData(
                    id,
                    title,
                    name,
                    voteAvg,
                    popularity,
                    poster,
                    backdrop,
                    overview,
                    releaseDate
                )
                list.add(favMovie)
            } while (cursor.moveToNext())
        }
        return list
    }

    @SuppressLint("Recycle")
    fun getFavouriteMovieById(id: Int?): ResultsData? {
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM favourite_movie WHERE id = $id", null)
        if (cursor.moveToFirst()) {
            val favMovieTemp = ResultsData()
            favMovieTemp.id = cursor.getInt(cursor.getColumnIndex("id"))
            favMovieTemp.title = cursor.getString(cursor.getColumnIndex("title"))
            favMovieTemp.name = cursor.getString(cursor.getColumnIndex("name"))
            favMovieTemp.vote_average = cursor.getString(cursor.getColumnIndex("vote_average"))
            favMovieTemp.popularity = cursor.getString(cursor.getColumnIndex("popularity"))
            favMovieTemp.poster_path = cursor.getString(cursor.getColumnIndex("poster_path"))
            favMovieTemp.backdrop_path = cursor.getString(cursor.getColumnIndex("backdrop_path"))
            favMovieTemp.overview = cursor.getString(cursor.getColumnIndex("overview"))
            favMovieTemp.release_date = cursor.getString(cursor.getColumnIndex("release_date"))
            return favMovieTemp
        }
        return null
    }

    fun deleteFavouriteMovie(favMovie: ResultsData?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("id", favMovie?.id)
        val retVal = db.delete("favourite_movie", "id = " + favMovie?.id, null)
        if (retVal >= 1) {
            Log.v("delete fav Movie :", " Movie deleted")
        } else {
            Log.v("delete fav Movie :", " Movie Not deleted")
        }
        db.close()

    }
}