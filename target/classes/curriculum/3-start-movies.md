#Starting the Backend for the Movies Project

###Create a new Maven project
1. Configure IntelliJ to use Tomcat

   [See this markdown](tomcat.md) for more details.

2. As in our sample servlet project, add this dependency into your `pom.xml`:

```
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
</dependency>
```
Now, reload your maven project.

---
###Making the Servlet and doGet()


####1. In ```src/main/java```, create a ```MovieServlet``` class.
   
- Have MovieServlet extend HttpServlet
    
      
- Annotate the class with @WebServlet(name="MovieServlet", urlPatterns="/movies/*")
      


- Create an override (use annotation) protected void `doGet` method.
    - This method will accept ```HttpServletRequest request``` and ```HttpServletResponse response``` as arguments
    - set the response content type as ```"application/json"```
      - `response.setContentType()`
    - Create a ```PrintWriter``` variable named ```out```
        - Set its value to ```response.getWriter();```
        - `getWriter()` can throw an `IOException`. So handle it with a `try/catch`
    - Let's leave ```doGet()``` alone for now
    
---
####2. In ```src/main/java```, create a new package named ```data```
   

- This is where we will make classes for defining a movie object and persisting data to a database.
    

- For now, we are wanting to make the object so that we can test our endpoints with the same movie object as you
      were using in the frontend side of the project
    

- In the ```data``` package, make a class named ```Movie```. Give it the same fields as were present in your Glitch
      server's db.json file. Make an empty constructor, a full constructor, and getters/setters for all fields.
      
***You will eventually make the Movie class your 'Entity'. More on that later!***
      

---
####3. Now, we are ready to serve up a Movie object as JSON!
- In MovieServlet.doGet(), make a new Movie object set to a variable. Populate its fields either in the constructor
  call or with setter methods. -> Make the data realistic. This way it looks similar to what you should see later on
  in production.
  

- Using your Gson dependency, pass the new movie object into a call to
```Java
    String movieString = new Gson().toJson(yourMovieObject);
```

Now that your movie is converted to a string, you can pass it to your ```PrintWriter``` variable's ```printLn()``` method.

---
#Magic Time!

####1. Let's run the server and ensure it is listening on ```http://localhost:8080```

####2. If so, now go to your browser and visit ```http://localhost:8080/movies```

####3. If successful, you should see your movie object represented as JSON on the screen!

##What is this?

When you hit the `/movies` route without any other parameters, you are accessing the `GET` method. For us, it's called `doGet()`. It requires no input and returns all available `Movie` objects, represented as JSON.

---
##Testing your endpoints


###In the browser console, you can also use the Fetch API to make HTTP requests!

###Be sure to reference [this](documentation.md) frequently for help creating sample JSON

```Javascript
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

*For other methods (POST, PUT, DELETE), you will need to change the Fetch to be formatted properly for the requests. Ask for help!*

---
## doPost, doPut, doDelete

For these methods (to complete CRUD on your servlet), you will set them up much the same as doGet()
-Create an override (use annotation) protected void [the name of the method].
- This method will accept ```HttpServletRequest request``` and ```HttpServletResponse response``` as arguments
- Set the response content type as ```"application/json"```
- Create a ```PrintWriter``` variable named ```out```
- Set that variable's value to ```response.getWriter();```

**The difference is now you are not sending OUT movie data, but receiving it in order to
Create/Update/Delete a record in the database.**

For now, we just want to take in the incoming movie by converting it from
JSON into a Movie object (or array of Movie objects).

To do this, you must:
- First get the ```HttpServletRequest``` parameter (```request```).

- Access ```request``` property ```getReader()```, which will return a ```BufferedReader``` object.
    - This means you should assign `request.getReader()` to a variable of type `BufferedReader`


- We will use this `BufferedReader` variable to "read" the body of the request.  


- The returned ```BufferedReader``` can be cast into your desired ```Movie``` object or Array of ```Movie``` objects.
    - As seen below, pass ```request.getReader()``` in to Gson().fromJson() as the first argument
    - The second argument describes what you are trying to convert the ```BufferedReader``` into (in this case, ```Movie[]```)
    

- The overall return from this operation is assigned to a variable of the same type as your second argument in ```fromJson()```

```Java
    Movie[] movies = new Gson().fromJson(request.getReader(), Movie[].class);
```

###On doDelete():

***Simply pass an `id` property of type `int` in the delete request. We will be deleting movies by `id`.*** 


###*It is VERY important to test each endpoint as you develop BEFORE moving on to developing another servlet method*


Once you have the array of Movie objects, you will want to verify they came in correctly by printing their
details to the console.

You will eventually get rid of that ```System.out.println()```, but it helps to see our data hitting the endpoints correctly and being converted.

Simply write a loop which prints each field by calling each object's getter methods inside a separate ```sout```. Be sure to do this AFTER you convert to a Movie java object.

###For doPut and doDelete, simply repeat the steps of doPost for now. We'll do more with these methods once our database is hooked up!

##Next up: [Data Persistence and Retrieval](4-data-persistence-intro.md)

