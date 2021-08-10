import com.google.gson.Gson;
import data.DaoFactory;
import data.Movie;
import data.MoviesDao;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")

public class MovieServlet extends HttpServlet {
    Movie movie = new Movie(2, "Blank Man", "1987", "John Goldman", "Jason Statham", "2341324", "there ant one", "action", "some stuff");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
//    get object which can write to the response
            PrintWriter out = response.getWriter();
//            out.println("Look at my GSON");
//    eventually get movies from the database
//            Movie movie = new Movie(2, "Blank Man", "1987", "John Goldman", "Jason Statham", "2341324", "there ant one", "action", "some stuff");

            //
            MoviesDao moviesDao = DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY);

            //    turn into JSON string
            String movieString = new Gson().toJson(moviesDao.all());
            out.println(movieString);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        //    get object which can write to the response
        PrintWriter out = null;
        try {
            out = response.getWriter();

            //get the stream of characters from the request (eventually becomes our movies
            BufferedReader reader = request.getReader();

            //turn that stream into array
            Movie[] movies = new Gson().fromJson(reader, Movie[].class);

            //Casey extra method (might not need)
            DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY).insert(movies[0]);

            //sout out properties of moive to know our object made it to our client
            for (Movie movie : movies) {
                System.out.println(movie.getId());
                System.out.println(movie.getTitle());
                System.out.println(movie.getDirector());
                System.out.println(movie.getActors());
                System.out.println("******************");

//                DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY).insertAll(movies);

                //replace references to IN_MEMORY with MYSQL and insertALL to insert
                DaoFactory.getMoviesDao(DaoFactory.ImplType.MYSQL).insert(movie);

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            out.println(new Gson().toJson(ex.getLocalizedMessage()));
            response.setStatus(500);
            ex.printStackTrace();
        }

        //write a meaningfully response body and set
        out.println(new Gson().toJson("{message: \"Movies Post was successfully"));
        response.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        //    get object which can write to the response
        PrintWriter out = null;
        try {
            out = response.getWriter();

            //get the stream of characters from the request (eventually becomes our movies
            BufferedReader reader = request.getReader();

            //turn that stream into array
            Movie[] movies = new Gson().fromJson(reader, Movie[].class);
            DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY).update(movie);

            //sout out properties of movie to know our object made it to our client
            for (Movie movie : movies) {
                System.out.println(movie.getId());
                System.out.println(movie.getTitle());
                System.out.println(movie.getDirector());
                System.out.println(movie.getActors());
                System.out.println("******************");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            out.println(new Gson().toJson(ex.getLocalizedMessage()));
            response.setStatus(500);
        }

        //write a meaningfully response body and set
        out.println(new Gson().toJson("{message: \"Movies Put was successfully"));
        response.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        //    get object which can write to the response
        PrintWriter out = null;
        try {
            out = response.getWriter();

            //get the stream of characters from the request (eventually becomes our movies
            BufferedReader reader = request.getReader();

//            int id = new Gson().fromJson(reader, int.class);
//            System.out.println("the movie to delete: " + id);

            //Servlet Integration
            int id = new Gson().fromJson(request.getReader(), int.class);
            DaoFactory.getMoviesDao(DaoFactory.ImplType.IN_MEMORY).destroy(id);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            out.println(new Gson().toJson(ex.getLocalizedMessage()));
            response.setStatus(500);
            ex.printStackTrace();
            return;
        }

        //write a meaningful response body and set
        out.println(new Gson().toJson("{message: \"Movies Delete was successfully"));
        response.setStatus(200);
    }

}
