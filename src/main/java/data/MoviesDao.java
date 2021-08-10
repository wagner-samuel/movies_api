package data;

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


