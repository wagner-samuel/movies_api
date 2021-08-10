package data;

public class Config {

    public String getUrl() {
//        return "jdbc:mysql://localhost:3306/movies_db?serverTimezone=UTC&useSSL=false";
        return "https://my-glitch.server.me/movies";

    }

    public String getUser() {
        return "root";
    }

    public String getPassword() {
        return "codeup";
    }
}