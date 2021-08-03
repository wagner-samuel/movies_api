# Data Persistence

---
# Data Access Object (DAO)

Most applications will use some form of DAO in order to allow the application to run
queries/commands on a database table(s).

---

###Be sure to reference [this](documentation.md) frequently for help creating sample JSON

###1. Create a DAO Factory

A factory is a type of class which handles instantiation and configuration of objects,
typically returning a ready-made object(s) to the calling code.

Think of a `DaoFactory` as an actual car factory. 
- You place an order to the factory
- The factory handles putting together the car
- You get a car and don't have to worry about how a brushless electric motor is constructed!

In the `data` package, we want to create the `DaoFactory` class which has one method  `getMoviesDao()`
As you can guess, it returns a `MoviesDao` - already configured and ready to use!

```JAVA

import data.movies.InMemoryMoviesDao;
import data.movies.MoviesDao;
import data.movies.MySqlMoviesDao;

public class DaoFactory {

  private static MoviesDao moviesDao;
  private static Config config = new Config();
  public enum ImplType {MYSQL, IN_MEMORY}; //Notice we have two values here

  public static MoviesDao getMoviesDao(ImplType implementationType){

    switch(implementationType){
      case IN_MEMORY:{ //yet we have one switch case. We'll get to that!
        moviesDao = new InMemoryMoviesDao();
      }
    }
    return moviesDao;
  }
}

```
---
###2. DAO Interface


To allow our code to not be tied down to one type of database,
we will make a DAO interface which is agnostic of the final implementation of the DAO.

This is particularly useful because it tells others that if they want to use our `MoviesDao`
to create a new implementation (let's say a, `InMemoryMoviesDao`, `PostgresMoviesDao` or `SqlServerMoviesDao`),
that class *must* implement a set of methods defined in `MoviesDao`.

Define the following `MoviesDao` interface in your `data` package:

```JAVA

import java.sql.SQLException;
import java.util.List;

public interface MoviesDao {
    List<Movie> all() throws SQLException;
    Movie findOne(int id);
    void insert(Movie movie);
    void insertAll(Movie[] movies) throws SQLException;
    void update(Movie movie) throws SQLException;
    void destroy(int id) throws SQLException;
}

```

The `MoviesDao` interface draws a contract with its implementations which say they must create:

- all()
    - Gets all the records


- findOne(int id)
    - Gets a record by id


- insert(Movie movie)
    - Inserts 1 new `movies` row


- insertAll(Movie[] movies)
    - inserts a gaggle of movies


- update(Movie movie)
    - updates a column on 1 `movies` row


- destroy(int id)
    - annihilates one Movie. The obliterated Movie is chosen by id. The strong will survive.


---
###3. DAO Implementation - InMemoryMoviesDao

Before getting fully integrated with the database, 
let's build an implementation of the `MoviesDao`, the `InMemoryMoviesDao`.
We use this setup for testing our application without worrying about the database itself.

```JAVA

package data.movies;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InMemoryMoviesDao implements MoviesDao {

  private HashMap<Integer, Movie> moviesMap = getMoviesMap();

  @Override
  public List<Movie> all() throws SQLException {
    return moviesMap != null ? Arrays.asList(moviesMap.values()) : null;
  }

  @Override
  public Movie findOne(int id) {
    return moviesMap != null ? moviesMap.get(id) : null;
  }

  @Override
  public void insert(Movie movie) {
    int newId = moviesMap.keySet().size() + 1;
    movie.setId(newId);
    moviesMap.put(newId, movie);
  }

  @Override
  public void insertAll(Movie[] movies) throws SQLException {
    moviesMap = getMovieMap(Arrays.asList(movies));
  }

  @Override
  public void update(Movie movie) throws SQLException {
    if (moviesMap != null){
      moviesMap.replace(movie.getId(), movie);
    }
  }

  @Override
  public void destroy(int id) throws SQLException {
    if (moviesMap != null){
      moviesMap.remove(id);
    }
  }

  private HashMap<Integer, Movie> getMoviesMap() {
    try {
      Reader reader = Files.newBufferedReader(Paths.get("movies.json"));
      Type type = TypeToken.getParameterized(ArrayList.class, Movie.class).getType();
      return getMovieMap(new Gson().fromJson(reader, type));
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  private HashMap<Integer, Movie> getMovieMap(List<Movie> movies) {
    HashMap<Integer, Movie> movieHashMap = new HashMap<>();
    for (Movie movie : movies) {
      movieHashMap.put(movie.getId(), movie);
    }
    return movieHashMap;
  }
}

```


###Now, we are ready to use the `MoviesDao` in our servlets!

---
##Next up: [Servlet Integration](7-servlet-integration.md)