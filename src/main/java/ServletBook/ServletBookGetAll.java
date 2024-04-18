package ServletBook;


import ServiseBook.ServiceBookGetAll;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ServletBookGetAll extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp){

        try {
            ServiceBookGetAll.getInstance().doGet(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
