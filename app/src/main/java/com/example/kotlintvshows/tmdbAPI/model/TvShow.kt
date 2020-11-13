package com.example.kotlintvshows.tmdbAPI.model

import java.io.Serializable

class TvShow(
    val poster_path: String?,
    val id: Int,
    val backdrop_path: String?,
    val vote_average: Double,
    val overview: String,
    val networks: List<Network>,
    val first_air_date: String,
    val genre_ids: Array<Int>,
    val name: String): Serializable

//EXAMPLE RESPONSE
/*
    "poster_path": "/tfdiVvJkYMbUOXDWibPjzu5dY6S.jpg",
    "popularity": 1.722162,
    "id": 604,
    "backdrop_path": "/hHwEptckXUwZM7XO2lxZ8w8upuU.jpg",
    "vote_average": 8.17,
    "overview": "Teen Titans is an American animated television series based on the DC Comics characters of the same name, primarily the run of stories by Marv Wolfman and George Pérez in the early-1980s The New Teen Titans comic book series. The show was created by Glen Murakami, developed by David Slack, and produced by Warner Bros. Animation. It premiered on Cartoon Network on July 19, 2003 with the episode \"Divide and Conquer\" and the final episode \"Things Change\" aired on January 16, 2006, with the film Teen Titans: Trouble in Tokyo serving as the series finale. A comic book series, Teen Titans Go!, was based on the TV series. On June 8, 2012, it was announced that the series would be revived as Teen Titans Go! in April 23, 2013 and air on the DC Nation block.IT now airs on the Boomerang channel. ",
    "first_air_date": "2003-07-19",
    "origin_country": [
    "US"
    ],
    "genre_ids": [
    16,
    10759
    ],
    "original_language": "en",
    "vote_count": 12,
    "name": "Teen Titans",
    "original_name": "Teen Titans"
 */