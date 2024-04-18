package ServletBook;

import ServicePerson.PersonServiceUpdate;
import ServiseBook.ServiceUpdateBook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
@WebServlet
public class ServletBookUpdate extends HttpServlet {

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServiceUpdateBook.getInstance().doPut(req, resp);
    }
}
