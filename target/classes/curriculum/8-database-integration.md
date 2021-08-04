#Database Integration


##Connecting to the database

Much like using a MySQL client, we must take a few steps in order to allow our application to make queries/commands on the database.

---
###Be sure to reference [this](documentation.md) frequently for help creating sample JSON

### 1. Gather our dependencies

Make sure the following is nested in your `pom.xml` `dependencies` tag:

```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.14</version>
</dependency>
```

---
### 2. Configure our connection settings

Now we need to create the class which will store information used to connect to the database.

Inside the `data` package, create a `Config` java class:

```JAVA

public class Config {

    public String getUrl(){
        return "jdbc:mysql://localhost:3306/movies_db?serverTimezone=UTC&useSSL=false";
    }

    public String getUser(){
        return "root";
    }

    public String getPassword(){
        return "codeup";
    }
}

```

This class stores the `URL` (location), `username`, and `password` relating to our database.

We will change these values to be related to our remote server when the time comes.


---
##New DAO Implementation - MySqlMoviesDao

Now, we are ready to add a database implementation of the `MoviesDao`.
This  `MySqlMoviesDao` class will contain the actual code needed to query/command the database:

```JAVA

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao {

    private Connection connection = null;

    public MySqlMoviesDao(Config config) {
        //We will configure our connections here
    }

    @Override
    public List<Movie> all() throws SQLException {
        // TODO: Get ALL the movies
    }

    @Override
    public Movie findOne(int id) {
        // TODO: Get one movie by id
    }

    @Override
    public void insert(Movie movie) {
        // TODO: Insert one movie
    }

    public void insertAll(Movie[] movies) throws SQLException {
        // TODO: Insert all the movies!
    }

    @Override
    public void update(Movie movie) throws SQLException {
        //TODO: Update a movie here!
    }

    @Override
    public void destroy(int id) throws SQLException {
        //TODO: Annihilate a movie
    }
}


```

####Notice a few things here:

- `MySqlMoviesDao implements MoviesDao`
    - Forces our class to implement interface methods and
      let calling code be agnostic of which class is being used for data access.


- We have a constructor which takes in a `Config` argument.
  This will be used to set database connection strings on the MySqlMoviesDao instance.


- All other methods are marked as `@Override`
    - The methods are overridden from the `MoviesDao`
    - These will be where you actually create code for databse CRUD operations

---
## MySqlMoviesDao Constructor

```JAVA

    private Connection connection = null;

    public MySqlMoviesDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());

            this.connection = DriverManager.getConnection(
                    config.getUrl(), // <-- WHERE IS THE DB?
                    config.getUser(), // <-- WHO IS ACCESSING?
                    config.getPassword() // <-- WHAT IS THEIR PASSWORD?
            );

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }

```

There are 3 essential parts to this constructor setup:

- SQL Driver
    - How to connect to and interact with a particular version of MySQL).


- Connection
    - The active, live connection to our database. Unique to each instance of `MySqlMoviesDriver`. Initialized as null.


- Error Handling
    - If a `SQLException` is thrown, we can gracefully handle it with a `try/catch` block



---
## MySqlMoviesDao Method Implementations

Now that your constructor has set the `connection` field, we are ready to open the connection and query/command!

---
###insertAll(Movie[] movies)

```JAVA

    public void insertAll(Movie[] movies) throws SQLException {
        
        // Build sql template
        StringBuilder sql = new StringBuilder("INSERT INTO movies (" +
                "id, title, year, director, actors, imdbId, poster, genre, plot) " +
                "VALUES ");


        // Add a interpolation template for each element in movies list
        sql.append("(?, ?, ?, ?, ?, ?, ?, ?, ?), ".repeat(movies.length));

        // Create a new String and take off the last comma and whitespace
        sql = new StringBuilder(sql.substring(0, sql.length() - 2));
        
        // Use the sql string to create a prepared statement
        PreparedStatement statement = connection.prepareStatement(sql.toString());

        // Add each movie to the prepared statement using the index of each sql param: '?'
        // This is done by using a counter
        // You could use a for loop to do this as well and use its incrementor
        int counter = 0;
        for (Movie movie : movies) {
            statement.setInt((counter * 9) + 1, movie.getId());
            statement.setString((counter * 9) + 2, movie.getTitle());
            statement.setString((counter * 9) + 3, movie.getYear());
            statement.setString((counter * 9) + 4, movie.getDirector());
            statement.setString((counter * 9) + 5, movie.getActors());
            statement.setString((counter * 9) + 6, movie.getImdbID());
            statement.setString((counter * 9) + 7, movie.getPoster());
            statement.setString((counter * 9) + 8, movie.getGenre());
            statement.setString((counter * 9) + 9, movie.getPlot());
            counter++;
        }
        statement.executeUpdate();
    }

```

Some boilerplate steps we take here:

***It would be a good idea to write the equivalent query/command in a Console before writing any of these methods. Then transfer them over to your code when you feel good about it!***


- Build the statement `StringBuilder sql =....`


- Format the string for your dynamic fields `append(?, ?, ?, ?.....)`


- Create a `PreparedStatement` variable
    - We draw this from the `Connection` object (this ties the query/command to a specific connection)
    - Pass the `sql` string to `prepareStatement`


- Then, a little logic to set each `?` param in the `sql`:
    - A loop to go through the collection of movies and set each `Movie` field to a `?` param
    - Use `statement.setString("yourParam", "anotherParam"...)` to set all
      of the Movie object fields to their SQL command parameters (`?`).
    - Some math is needed to line up the number of `?` to:
        - (number of total properties per movie * by the number of movies)
            - ***We'll help with that part!***


- Execute the statement with `statement.executeUpdate()`


- This is an insert (CREATE) on the database, so there is no return value!


---
####Good news: UPDATE and DELETE follow the same boilerplates as INSERT!


***TODO: see if you can follow the same process to implement update()***


***It would be a good idea to write the equivalent query/command in a Console before writing any of these methods. Then transfer them over to your code when you feel good about it!***

---
###update(Movie movie)

*We do not need to do a loop or complicated logic* - We are updating one `Movie` at a time!

- Make a `String` variable for your sql string
    - This time, pass in a `?` where ever you would have a value inserted from a movie object
    - ie: `...title = ?, year = ?,...`


- Create a `PreparedStatement` variable exactly as you did with `insertAll()`
    - Again, use `statement.setString("yourParam", "anotherParam"...)` to set all
      Movie object fields to their SQL command parameters.


- Execute the statement with `statement.executeUpdate()`


---
###destroy(int id)

This one really is the best:

```JAVA
@Override
    public void destroy(int id) throws SQLException {

        String sql = 
                "DELETE FROM movies " +
                "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, id);

        statement.execute();
    }

```

The key here is we are deleting a record by *id*. This means we need nothing else in order to remove a movie from the database table!

---
###findAll()

What good is creating a record if we can't retrieve it?

```JAVA

@Override
public List<Movie> all() throws SQLException {

    Statement statement = connection.createStatement();

    ResultSet rs = statement.executeQuery("SELECT * FROM movies");

    List<Movie> movies = new ArrayList<>();
    
    while (rs.next()) {
      movies.add(new Movie(
        rs.getInt("id"),
        rs.getString("title"),
        rs.getString("year"),
        rs.getString("director"),
        rs.getString("actors"),
        rs.getString("imdbId"),
        rs.getString("poster"),
        rs.getString("genre"),
        rs.getString("plot")
      ));
    }
    
    return movies;
}

```
**Boilerplate**:


***It would be a good idea to write an insert query/command in a Console before writing any of these methods. Then transfer them over to your code when you feel good about it!***


- Make a `Statement` variable


- Make a `ResultSet` variable
    - Unlike the `insert` process, you will execute the query toward the beginning of the method
    - Call `statement.executeQuery("Your query string");`


- Once you get the response from the database server, we need to iterate through the `ResultSet` variable
    - From here we grab each row from the `Result` set and transmutate into a `Movie` object


- Finally, just return the collection of movies!

---

### While you can start with the above methods, you may find you need additional DAO methods (findByWhateveColumn, insertOne, etc)
###Reach out and let us help you discover how to write them!

---

##Next Up: [Servlet Integration Part II](9-servlet-integration-pt2.md)

