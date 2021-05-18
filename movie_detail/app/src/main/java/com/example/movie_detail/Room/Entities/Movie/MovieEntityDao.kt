package com.example.movie_detail.Room.Entities.Movie

import androidx.room.*

@Dao
interface MovieEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneMovie(movieEntity: MovieEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyMovies(movies: List<MovieEntity>);
    @Update(entity = MovieEntity::class)
    fun updateMovieEntity(movieEntity: MovieEntity);
}

@Dao
interface SimilarEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneSimilarMovie(movieEntity: SimilarMovieEntity);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManySimilarMovies(movies: List<SimilarMovieEntity>);
    @Update(entity = SimilarMovieEntity::class)
    fun updateSimilarMovieEntity(similarMovie: SimilarMovieEntity);
}