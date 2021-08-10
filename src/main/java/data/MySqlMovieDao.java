package data;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMovieDao implements MoviesDao {

    private Connection connection = null;

    public MySqlMovieDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());

            this.connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch (SQLException se) {
            throw new RuntimeException("Error connecting to the database!", se);
        }
    }

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
                    rs.getString("rating"),
                    rs.getString("poster"),
                    rs.getString("genre"),
                    rs.getString("plot")
            ));
        }
        return movies;
    }

    @Override
    public Movie findOne(int id) {
        return null;
//        try {
//            Statement statement = connection.createStatement();
//
//            ResultSet rs = statement.executeQuery("DELETE FROM movies +" +
//                    "WHERE id = ?");
//
//            String sql = "DELETE FROM movies " +
//                    "WHERE id = ?";
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();

    }

    @Override
    public void insert(Movie movie) {


        String sql = "INSERT FROM movies " +
                "WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setObject(1, movie);

            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insertAll(Movie[] movies) throws SQLException {

        // Build sql template (this is 9 question marks, 9 in loop statement
        StringBuilder sql = new StringBuilder("INSERT INTO movies (" +
                "id, title, year, director, actors, rating, poster, genre, plot) " +
                "VALUES ");

        // Add an interpolation template for each element(field) in movies list
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
            //Correlate the question masks that correlates to movie fields and order matters
            statement.setInt((counter * 9) + 1, movie.getId());
            statement.setString((counter * 9) + 2, movie.getTitle());
            statement.setString((counter * 9) + 3, movie.getYear());
            statement.setString((counter * 9) + 4, movie.getDirector());
            statement.setString((counter * 9) + 5, movie.getActors());
            statement.setString((counter * 9) + 6, movie.getRating());
            statement.setString((counter * 9) + 7, movie.getPoster());
            statement.setString((counter * 9) + 8, movie.getGenre());
            statement.setString((counter * 9) + 9, movie.getPlot());
            counter++;
        }
        statement.executeUpdate();
    }

    @Override
    public void update(Movie movie) throws SQLException {
// Build sql template (this is 9 question marks, 9 in loop statement
        StringBuilder sql = new StringBuilder("UPDATE movies " +
                "SET title = ?, year = ?, director = ?, actors = ?, rating = ?, poster = ?, genre = ?, plot = ? WHERE id = ? ");

        // Create a new String and take off the last comma and whitespace
        sql = new StringBuilder(sql.substring(0, sql.length() - 2));

        // Use the sql string to create a prepared statement
        PreparedStatement statement = connection.prepareStatement(sql.toString());

        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getYear());
        statement.setString(3, movie.getDirector());
        statement.setString(4, movie.getActors());
        statement.setString(5, movie.getRating());
        statement.setString(6, movie.getPoster());
        statement.setString(7, movie.getGenre());
        statement.setString(8, movie.getPlot());
        statement.setInt(9, movie.getId());
        statement.executeUpdate();

    }

    @Override
    public void destroy(int id) throws SQLException {

        String sql = "DELETE FROM movies " +
                "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        statement.execute();
    }
}
