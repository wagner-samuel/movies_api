#Movies Backend 
##Endpoint Documentation

The Movies API allows a client to perform CRUD operations on exposed endpoints using JSON.

---

###Movie Property Types (Java)

```
{
    id: int,
    title: "String",
    year: "String",
    director: "String",
    actors: "String",
    imdbID: "String",
    poster: "String",
    genre: "String",
    plot: "String"
}
```

---

###Exposed Endpoints

####/movies

####Headers: 
```
{
    'Content-Type' : 'application/json' 
}
``` 
```
redirect: 'follow'
```

---


####GET

--- Sample Request Body:

    -> This endpoint gets all movies, no body needed

--- 200 Response Body:

```[
    {
    "id": 11,
    "title": "Dostana",
    "year": "2008",
    "director": "Tarun Mansukhani",
    "actors": "Abhishek Bachchan, John Abraham, Priyanka Chopra",
    "imdbID": "tt1185420",
    "poster": "https://m.media-amazon.com/images/M/MV5BOTE0NDU1ZTctYjRjYS00OTEyLTkzOWQtNmRiNDg5ZDU1ODBiXkEyXkFqcGdeQXVyNjQ2MjQ5NzM@._V1_SX300.jpg",
    "genre": "Comedy, Drama, Romance",
    "plot": "Two straight guys pretend to be gay in order to secure a Miami apartment. When both of them fall for their roommate Neha, hilarity ensues as they strive to convince one and all that they're gay whilst secretly trying to win her heart"
    },
    {
    "id": 12,
    "title": "Friday",
    "year": "1995",
    "director": "F. Gary Gray",
    "actors": "Ice Cube, Chris Tucker, Nia Long",
    "imdbID": "tt0113118",
    "poster": "https://m.media-amazon.com/images/M/MV5BYmEwNjNlZTUtNzkwMS00ZTlhLTkyY2MtMjM2MzlmODQyZGVhXkEyXkFqcGdeQXVyNTI4MjkwNjA@._V1_SX300.jpg",
    "genre": "Comedy, Drama",
    "plot": "It's Friday, and Craig and Smokey must come up with $200 they owe a local bully or there won't be a Saturday."
    },
    {
    "id": 14,
    "title": "Harry Potter and the Deathly Hallows: Part 2",
    "year": "2011",
    "director": "David Yates",
    "actors": "Ralph Fiennes, Michael Gambon, Alan Rickman, Daniel Radcliffe",
    "imdbID": "tt1201607",
    "poster": "https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_SX300.jpg",
    "genre": "Adventure, Drama, Fantasy, Mystery",
    "plot": "Harry, Ron, and Hermione search for Voldemort's remaining Horcruxes in their effort to destroy the Dark Lord as the final battle rages on at Hogwarts."
    }
]
```

---

####POST

--- Sample Request Body: 

```
[
  {
    title: "Dostana",
    year: "2008",
    director: "Tarun Mansukhani",
    actors: "Abhishek Bachchan, John Abraham, Priyanka Chopra",
    imdbID: "tt1185420",
    poster: "https://m.media-amazon.com/images/M/MV5BOTE0NDU1ZTctYjRjYS00OTEyLTkzOWQtNmRiNDg5ZDU1ODBiXkEyXkFqcGdeQXVyNjQ2MjQ5NzM@._V1_SX300.jpg",
    genre: "Comedy, Drama, Romance",
    plot: "Two straight guys pretend to be gay in order to secure a Miami apartment. When both of them fall for their roommate Neha, hilarity ensues as they strive to convince one and all that they're gay whilst secretly trying to win her heart",
    id: 11
  }
]
```

--- 200 Response Body:

```
{message: "Movies POST was successful"}
```

---

####PUT

--- Sample Request Body:

```
{
    title: "UPDATED MOVIE",
    year: "2008",
    director: "Tarun Mansukhani",
    actors: "Abhishek Bachchan, John Abraham, Priyanka Chopra",
    imdbID: "tt1185420",
    poster: "https://m.media-amazon.com/images/M/MV5BOTE0NDU1ZTctYjRjYS00OTEyLTkzOWQtNmRiNDg5ZDU1ODBiXkEyXkFqcGdeQXVyNjQ2MjQ5NzM@._V1_SX300.jpg",
    genre: "Comedy, Drama, Romance",
    plot: "Two straight guys pretend to be gay in order to secure a Miami apartment. When both of them fall for their roommate Neha, hilarity ensues as they strive to convince one and all that they're gay whilst secretly trying to win her heart",
    id: 11
}
```

--- 200 Response Body:

```
{message: "Movie UPDATE was successful"}
```

---

####DELETE (by ID)

--- Sample Request Body:

```
{"11"}
```

--- 200 Response Body:

```
{message: "Movie DELETE was successful"}
```



