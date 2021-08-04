
# The Entity

The entity is a Plain Old Java Object (POJO) which represents a single row in a database table

It has 
- `private fields` 
- `constructors`
- `getters` and `setters`

Typically, the entity will not have much more than the above. However, there are many ways to achieve building an entity!

Your entity may look like this:

```JAVA

public class Movie implements Serializable {

    private int id;
    private String title;
    private String year;
    private String director;
    private String actors;
    private String imdbId;
    private String poster;
    private String genre;
    private String plot;

    //at least an empty constructor
    public Movie(){}
    
    //NOTE: It may be a good idea to make other constructors with more parameters to suit your needs
    
    //Among those, a full constructor with all fields being assigned is a good idea!

    public Movie(){}

    //getter and setter methods
}

```


Here is how that entity may be reflected in a Movie table:

![Movie table in DB](movie_table.png)



Notice how the table column names are the same as the private field names?
As well, we have equivalency between the data *types*. ```String``` becomes ```char```, ```varchar```, or ```text``` and so on.

We would want to make an entity for each table in the database schema *if* we plan to use that table somehow.

## Nested Entities

What if our database table has a foreign key to another table?

Can we have that foreign-keyed table as an entity property on our main entity?

**Absolutely!**

***Just an example:***

```JAVA
public class Movie implements Serializable {

    private int id;
    private String title;
    private String year;
    private Profile director; // <-- LOOK HERE
    private Profile[] actors; // <-- AND HERE!
    private String imdbId;
    private String poster;
    private String genre;
    private String plot;
    
    // rest of class...
}
```

Notice the `director` and `actors` fields are now of type `Profile`

We would make the assumption that our database contains a table titled `profiles` and we have the following entity class:

```JAVA
public class Profile implements Serializable {

    private int id;
    private String name;
    private String bio;
    private String yearsActive;
    private Datetime birthYear;
    private Optional<Datetime> deathYear;
    private String isActive;
    
    // rest of class...
}
```

Of course, the database table `profiles` would look similar in composition.

---

##Next up: [Data Persistence](6-data-persistence.md)














