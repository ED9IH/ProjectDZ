package ServletPerson;

import ServicePerson.PersonServiceGetAll;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ServletGetPersonAll extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {

        try {
            PersonServiceGetAll.getInstance().doGet(req, resp);
        } catch (IOException e) {
            throw new RuntimeException(e + "ServletGetPersonAll");
        }
    }

}
