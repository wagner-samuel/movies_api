import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="HelloWorldServlet",urlPatterns = "/hello-world")
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        response.setContentType("text/html");

        try {
            PrintWriter out = response.getWriter();
            out.println("I swea I will not make another Hello World app");
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
