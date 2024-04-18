package ServletBook;

import ServiseBook.ServiceBookGetById;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ServletBookGetById extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse resp){

        try {
            ServiceBookGetById.getInstance().doGet(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
