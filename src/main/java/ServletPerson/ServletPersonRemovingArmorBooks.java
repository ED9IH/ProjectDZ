package ServletPerson;

import ServicePerson.PersonServiceRemovingArmorBooks;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ServletPersonRemovingArmorBooks extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PersonServiceRemovingArmorBooks.getInstance().doPut(req, resp);

    }

}
