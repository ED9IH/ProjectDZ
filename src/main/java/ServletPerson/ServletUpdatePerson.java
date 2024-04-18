package ServletPerson;


import ServicePerson.PersonServiceUpdate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ServletUpdatePerson extends HttpServlet {


    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PersonServiceUpdate.getInstance().doPut(req, resp);
    }
}
