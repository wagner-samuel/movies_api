#Servlet Integration

Implementing your DAO methods is possibly the simplest part of this process!

We are now going to finish writing the methods in `MovieServlet`!

---
## Be sure to test each endpoint as you complete them using Fetch API in the browser console
###Of course, reformat the request and method properties to whether your are doing GET, POST, PUT, DELETE.
###Be sure to reference [this](documentation.md) frequently for help creating sample JSON

```JAVA

fetch("http://localhost:8080/movies", { 
method: 'GET', 
headers: {
    'Content-Type': 'application/json'
},
redirect: 'follow'
}).then(function(response) {
    return response.json();
  }).then(function(data) {
    console.log(data);
  });

```
---
##Method Integration

---
###doGet()

```JAVA
 
    //put below your existing doGet code

try{
    out.println(new Gson().toJson(
            DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY)
                .all()));
}catch(SQLException e){
    e.printStackTrace();
}

```

Now, we are passing to `out.println()` the return value of `DaoFactory.getMoviesDao().all()`

***Notice we are passing `DaoFactory.ImplType.IN_MEMORY` to `getMoviesDao`***.
This is telling our Factory which implementation of the `MoviesDao` we need.

Later, we will change this to be `DaoFactory.ImplType.MYSQL`when 
we are ready for database integration!

Because `all()` can throw an `SqlException`, we should catch it here, log it, and send an error response to the client

---
###doPost()

```JAVA
    
    //put below your existing doPost code

try {
      DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY).insertAll(movies);
  } catch (SQLException e) {
      out.println(new Gson().toJson(e.getLocalizedMessage()));
      response.setStatus(500);
      e.printStackTrace();
      return;
  }
  out.println(new Gson().toJson("{message: \"Movies POST was successful\"}"));
  response.setStatus(200);
  
```

Here, we see that we are following the same idea as `doGet()`, except we are adding a `catch` condition to say if we get the exception throw, set the `HttpStatus`
code to `500` and return the error in the response so that our client understand what happened.

If the message was successful, send a JSON response with a `200` code (OK) and a nice message.

---
###doPut()

```JAVA
    
    //put below your existing doPut code

try {
      Movie movie = new Gson().fromJson(request.getReader(), Movie.class);
      DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY).update(movie);
  } catch (SQLException e) {
      out.println(new Gson().toJson(e.getLocalizedMessage()));
      response.setStatus(500);
      e.printStackTrace();
      return;
  } catch (Exception e) {
      out.println(new Gson().toJson(e.getLocalizedMessage()));
      response.setStatus(400);
      e.printStackTrace();
      return;
  }

  out.println(new Gson().toJson("{message: \"Movie UPDATE was successful\"}"));
  response.setStatus(200);

```

---

###doDelete()

```JAVA
   
    //put below your existing doDelete code

try {
      var id = new Gson().fromJson(request.getReader(), int.class);
      DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY).destroy(id);
  } catch (SQLException e) {
      out.println(new Gson().toJson(e.getLocalizedMessage()));
      response.setStatus(500);
      e.printStackTrace();
      return;
  }

  out.println(new Gson().toJson("{message: \"Movie DELETE was successful\"}"));

```

---


##Once your endpoints are well-tested, go ahead and remove the `souts` you added way back in the Servlets lesson!

###Read more about the [*DAO Pattern*](https://www.baeldung.com/java-dao-pattern)

##Next Up: [Database Integration](8-database-integration.md)


