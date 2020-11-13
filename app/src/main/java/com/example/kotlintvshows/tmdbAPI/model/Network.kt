package com.example.kotlintvshows.tmdbAPI.model

import java.io.Serializable

class Network(val name: String,
              val id: Int,
              val logo_path: String,
              val origin_country: String): Serializable

//EXAMPLE RESPONSE
/*

"networks": [
    {
      "name": "HBO",
      "id": 49,
      "logo_path": "/tuomPhY2UtuPTqqFnKMVHvSb724.png",
      "origin_country": "US"
    }
  ],

*/