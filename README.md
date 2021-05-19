# Reberth Kelvin - The movie database - [contact me](https://twitter.com)

This is a project that load the details from a specific movie, designed from a `movieId` propertie defined in [here](https://github.com/reberthkss/Todo-Movies/blob/main/movie_detail/app/src/main/java/com/example/movie_detail/Views/MovieDetails/MovieDetails.kt#L48)

## Specifications
* ***Architecture:*** ModelView-ViewModel
* ***Used API:*** [The Movie DB](https://developers.themoviedb.org/3/getting-started)
    * ***Endpoints:***
        * [genres/get-movie-list](https://developers.themoviedb.org/3/genres/get-movie-list): To get the available registered genres
        * [movies/get-movie-details](https://developers.themoviedb.org/3/movies/get-movie-details): To get the movie details (title, release date, image url, etc...)
        * [movies/get-similar-movies](https://developers.themoviedb.org/3/movies/get-similar-movies): To get the similarly movies with the selected movie
        * [configuration/get-api-configuration](https://developers.themoviedb.org/3/configuration/get-api-configuration): To get the correct url for gathering images

* ***Used tools:***
    * [Retrofit](https://github.com/square/retrofit): For communicate with API
    * [Moshi](https://github.com/square/moshi): For parse response from API
    * [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines): For handle thread operations
    * [Room Database](https://developer.android.com/training/data-storage/room): For persist data
    
    

## Description
I tried to apply everything i've known about Android, OOP and Relational databases since from my backgrounds (current place where i'm working, personal projects, university and [this](https://classroom.udacity.com/courses/ud853) course of Udacity provided by Google).

It uses the recommended architecture for Android Development:
 
 ![Recommended arch for android](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
    
The development of the user interface and communication with API was the easy part. The hardest work were the implementation of the Room Database, in my perception i took at least 20 hours to implement Room Database implementation following this model:

![Data model](https://mobile2youtest.s3.amazonaws.com/relational+data.png)

To get the above data I had to set 3 cross-references tables:
- ***Movie And Similar Movies:*** For relations between a movie, and they similar movies
- ***Similar Movie and Genres:*** For relations between a similar movie and a genre
- ***Movie And Genres:*** For relations between a movie and a genre

![cross-references](https://mobile2youtest.s3.amazonaws.com/Crossreference.png)

With that relations i can get a data structure like the given JSON: 

```
{
movie: {
        movieId: String,
        movieTitle: String,
        releaseDate: String,
        imageUrl: String,
        voteCount: Int,
        popularity: Float,
        isFavorite: Boolean
    },
genres: {
        genreId: String,
        genreName: String
    }[],
similarMovies: {
        similarMovieId: String,
        similarMovieTitle: String,
        releaseDate: String,
        imageUrl: String,
        isWatched: Boolean   
    }[]
}
```



With that data i can handle the layout using [BindingAdapters](https://developer.android.com/topic/libraries/data-binding/binding-adapters) and [view](https://developer.android.com/topic/libraries/view-binding?hl=pt-br) / [data](https://developer.android.com/topic/libraries/data-binding) binding.

## Result

https://mobile2youtest.s3.amazonaws.com/2021-05-18+21-27-05.mp4
