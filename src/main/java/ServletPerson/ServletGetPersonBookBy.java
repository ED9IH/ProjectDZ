package ServletPerson;

import ServicePerson.PersonServiceGetPersonBookBy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ServletGetPersonBookBy extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            PersonServiceGetPersonBookBy.getInstance().doGet(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e + "ServletGetPersonBookBy");
        }

    }

}
