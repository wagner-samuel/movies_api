import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")

public class MovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Hello, world!");
    }

}
