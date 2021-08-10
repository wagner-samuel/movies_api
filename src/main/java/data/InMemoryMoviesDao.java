package data;

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
        return moviesMap != null ? new ArrayList<>(moviesMap.values()) : null;
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
        if (moviesMap != null) {
            moviesMap.replace(movie.getId(), movie);
        }
    }

    @Override
    public void destroy(int id) throws SQLException {
        if (moviesMap != null) {
            moviesMap.remove(id);
        }
    }

    private HashMap<Integer, Movie> getMoviesMap() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("/Users/bigdaddy/IdeaProjects/movies_api/src/main/resources/movies.json"));
            Type type = TypeToken.getParameterized(ArrayList.class, Movie.class).getType();
            return getMovieMap(new Gson().fromJson(reader, type));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private HashMap<Integer, Movie> getMovieMap(List<Movie> movies) {
        HashMap<Integer, Movie> movieHashMap = new HashMap<>();
       int counter = 1;
        for (Movie movie : movies) {
            movieHashMap.put(counter, movie);
            movie.setId(counter);
            counter++;
        }
        return movieHashMap;
    }
}
