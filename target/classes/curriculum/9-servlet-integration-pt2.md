#Servlet Integration Part II

####For this portion, simply go to your `MoviesServlet` and replace references to `IN_MEMORY` with `MYSQL`

####Starting with `POST`, test inserting all of your movie records into the database.

####Once that is complete, inside `MoviesServlet.doPost()`replace the call to `MovieDao.insertAll()` with a call to `MovieDao.insert()`

####This is done to replace our seeder method with inserting one movie at a time.

---

###Now, test each servlet method with Fetch API to ensure it they are working properly

###Remember, you will not receive `Movie` objects back in `POST`, `PUT`, or `DELETE` methods.

###To test the data integrity, you will need to run queries on the database (via Intellij's db console) and check the altered/created rows.

##Next Up: [Client Integration](10-client-integration.md)
