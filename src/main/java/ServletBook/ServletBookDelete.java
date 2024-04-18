package ServletBook;

import ServiseBook.ServiceBookDelete;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ServletBookDelete extends HttpServlet {


    public void doDelete(HttpServletRequest req, HttpServletResponse resp){


        try {
            ServiceBookDelete.getInstance().doDelete(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }




}
